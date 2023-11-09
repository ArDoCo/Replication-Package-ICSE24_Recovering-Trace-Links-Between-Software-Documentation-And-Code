import logging
import os
import sys
import time

import torch
from torch.utils.data import DataLoader
from tqdm import tqdm
from transformers import BertConfig

sys.path.append("..")
sys.path.append("../../")
sys.path.append("../../common")

from code_search.twin.twin_eval import get_eval_args
from common.models import TBertS
from common.utils import MODEL_FNAME

from common.metrices import metrics
from common.utils import results_to_df
from data_process import __read_artifacts

F_ID = 'id'
F_TOKEN = 'tokens'
F_ATTEN_MASK = "attention_mask"
F_INPUT_ID = "input_ids"
F_EMBD = "embd"
F_TK_TYPE = "token_type_ids"

issue_index_num = {}
reverse_issue_index = {}
commit_index_num = {}
reverse_commit_index = {}


def _gen_seq_pair_feature(nl_id, pl_id, tokenizer):
    nl_tks = reverse_issue_index[nl_id]
    pl_tks = reverse_commit_index[pl_id]
    feature = tokenizer.encode_plus(
        text=nl_tks,
        text_pair=pl_tks,
        pad_to_max_length=True,
        return_attention_mask=True,
        return_token_type_ids=True,
        max_length=512,
        add_special_tokens=True
    )
    res = {
        F_INPUT_ID: feature[F_INPUT_ID],
        F_ATTEN_MASK: feature[F_ATTEN_MASK],
        F_TK_TYPE: feature[F_TK_TYPE]
    }
    return res


def format_batch_input_for_single_bert(batch, model):
    tokenizer = model.tokenizer
    nl_ids, pl_ids, labels = batch[0].tolist(), batch[1].tolist(), batch[2].tolist()
    input_ids = []
    att_masks = []
    tk_types = []
    for nid, pid, lb in zip(nl_ids, pl_ids, labels):
        encode = _gen_seq_pair_feature(nid, pid, tokenizer)
        input_ids.append(torch.tensor(encode["input_ids"], dtype=torch.long))
        att_masks.append(torch.tensor(encode["attention_mask"], dtype=torch.long))
        tk_types.append(torch.tensor(encode["token_type_ids"], dtype=torch.long))
    input_tensor = torch.stack(input_ids)
    att_tensor = torch.stack(att_masks)
    tk_type_tensor = torch.stack(tk_types)
    features = [input_tensor, att_tensor, tk_type_tensor]
    features = [t.to(model.device) for t in features]
    inputs = {
        'input_ids': features[0],
        'attention_mask': features[1],
        'token_type_ids': features[2],
    }
    return inputs


def test(args, model, eval_examples):
    if not os.path.isdir(args.output_dir):
        os.makedirs(args.output_dir)
    retr_res_path = os.path.join(args.output_dir, "raw_result.csv")
    retrieval_dataloader = DataLoader(eval_examples, batch_size=args.per_gpu_eval_batch_size)

    res = []
    for batch in tqdm(retrieval_dataloader, desc="retrieval evaluation"):
        nl_ids = batch[0]
        pl_ids = batch[1]
        labels = batch[2]
        with torch.no_grad():
            model.eval()
            inputs = format_batch_input_for_single_bert(batch, model)
            sim_score = model.get_sim_score(**inputs)
            for n, p, prd, lb in zip(nl_ids.tolist(), pl_ids.tolist(), sim_score, labels.tolist()):
                res.append((n, p, prd, lb))

    df = results_to_df(res)
    df.to_csv(retr_res_path)
    m = metrics(df, output_dir=args.output_dir)
    return m


def create_examples(data_dir):
    commit_file = os.path.join(data_dir, "commit_file")
    issue_file = os.path.join(data_dir, "issue_file")
    link_file = os.path.join(data_dir, "link_file")
    examples = []
    issues = __read_artifacts(issue_file, "issue")
    commits = __read_artifacts(commit_file, "commit")
    links = __read_artifacts(link_file, "link")
    i = 0
    for issue in issues:
        issue_index_num[issue.issue_id] = i
        reverse_issue_index[i] = issue.desc
        i += 1
    i = 0
    for commit in commits:
        commit_index_num[commit.commit_id] = i
        reverse_commit_index[i] = commit.summary
        i += 1
    link_index = {}
    for lk in links:
        if not lk[0] in link_index:
            link_index[lk[0]] = [lk[1]]
        elif not lk[1] in link_index[lk[0]]:
            link_index[lk[0]].append(lk[1])
    for issue in issues:
        for commit in commits:
            label = 0
            if issue.issue_id in link_index and commit.commit_id in link_index[issue.issue_id]:
                label = 1
            examples.append((issue_index_num[issue.issue_id], commit_index_num[commit.commit_id], label))
    return examples


def load_examples(data_dir):
    cache_dir = os.path.join(data_dir, "cache")
    if not os.path.isdir(cache_dir):
        os.makedirs(cache_dir)
    logger.info("Creating examples from dataset file at {}".format(data_dir))
    examples = create_examples(data_dir)
    return examples


if __name__ == "__main__":
    args = get_eval_args()
    device = torch.device("cuda" if torch.cuda.is_available() and not args.no_cuda else "cpu")
    res_file = os.path.join(args.output_dir, "./raw_res.csv")
    cache_dir = os.path.join(args.data_dir, "cache")
    cached_file = os.path.join(cache_dir, "test_examples_cache.dat".format())
    logging.basicConfig(level='INFO')
    logger = logging.getLogger(__name__)

    if not os.path.isdir(args.output_dir):
        os.makedirs(args.output_dir)

    model = TBertS(BertConfig(), args.code_bert)
    if args.model_path and os.path.exists(args.model_path):
        model_path = os.path.join(args.model_path, MODEL_FNAME)
        model.load_state_dict(torch.load(model_path))
        print(model_path)
    else:
        raise Exception("evaluation model not found")
    logger.info("model loaded")

    start_time = time.time()
    test_dir = os.path.join(args.data_dir, "test")
    test_examples = load_examples(test_dir)
    m = test(args, model, test_examples)
    exe_time = time.time() - start_time
    m.write_summary(exe_time)
