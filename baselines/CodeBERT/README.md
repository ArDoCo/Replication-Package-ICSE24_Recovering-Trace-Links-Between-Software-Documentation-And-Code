# CodeBERT Adaptation from TraceBERT repository

To train CodeBERT on the code search task on the Java part of the CodeSearchNet dataset we used the code provided in the replication package of [TraceBERT](https://github.com/jinfenglin/TraceBERT) and adapted it to our purpose. Therefore, we make use of the SINGLE architecture as described in the paper [Traceability Transformed: Generating More Accurate Links with Pre-Trained BERT Models](https://doi.org/10.1109/ICSE43902.2021.00040).

## Installation
- Python >= 3.7 
- pytorch/1.1.0
- 1 GPU with CUDA 10.2 or 11.1

```
pip install -U pip setuptools 
pip install -r requirement.txt
```

## Step 1: Fine-tune CodeBERT
Step 1 uses the Java part of the CodeSearchNet dataset, which can be found on [huggingface.co](https://huggingface.co/datasets/code_search_net/blob/main/data/java.zip) or [GitHub](https://github.com/github/CodeSearchNet). The files are also contained in the `data` [directory](data/java/).

### Train 
```
cd code_search/single
python single_train.py \
    --data_dir ../../data/java
    --output_dir ./output \
    --per_gpu_train_batch_size 8 \
    --per_gpu_eval_batch_size 8 \
    --logging_steps 10 \
    --save_steps 10000 \
    --gradient_accumulation_steps 16 \
    --num_train_epochs 8 \
    --learning_rate 4e-5 \
    --valid_num 200 \
    --valid_step 10000 \
    --neg_sampling online
```

## Step 2: Evaluate on SAD to Code
Step 2 uses the fine-tuned CodeBERT model to predict whether a sentence of the SAD should be linked to a source code class. The `data` [directory](data) includes the files of the five benchmark projects `bigbluebutton`, `jabref`, `MediaStore`, `teammates` and `TeaStore` in the required format.

### Evaluate 
To evaluate on MediaStore one could run:

```
cd trace/trace_single
python eval_trace_single_SAD.py \
  --data_dir ../../data/MediaStore \
  --model_path <model_path> \
  --per_gpu_eval_batch_size 10 \
  --exp_name "MediaStore_single"

```
The path to the fine-tuned model has to be provided after training is finished.


## Attribution of the code used


Lin, J., Liu, Y., Zeng, Q., Jiang, M., & Cleland-Huang, J. (2021, May). Traceability transformed: Generating more accurate links with pre-trained bert models. In 2021 *IEEE/ACM 43rd International Conference on Software Engineering (ICSE)* (pp. 324-335). IEEE.

Feng, Z., Guo, D., Tang, D., Duan, N., Feng, X., Gong, M., ... & Zhou, M. (2020). CodeBERT: A Pre-Trained Model for Programming and Natural Languages. In *Findings of the Association for Computational Linguistics: EMNLP 2020* (pp. 1536–1547), Association for Computational Linguistics (ACL).

