#!/bin/bash
set -e

# Get the absolute path of the script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if the script is being executed from the current directory
if [ "$(pwd)" != "$SCRIPT_DIR" ]; then
  echo "Please run the script from the directory where it is located."
  exit 1
fi

echo "Run CodeBERT"
. venv/bin/activate
cd trace/trace_single
python3 eval_trace_single_SAD.py --data_dir ../../data/MediaStore --model_path ../../../models/CodeBERT-Java --per_gpu_eval_batch_size 10 --exp_name "MediaStore_single"

python3 eval_trace_single_SAD.py --data_dir ../../data/bigbluebutton --model_path ../../../models/CodeBERT-Java --per_gpu_eval_batch_size 10 --exp_name "BigBlueButton_single"

python3 eval_trace_single_SAD.py --data_dir ../../data/jabref --model_path ../../../models/CodeBERT-Java --per_gpu_eval_batch_size 10 --exp_name "JabRef_single"

python3 eval_trace_single_SAD.py --data_dir ../../data/teammates --model_path ../../../models/CodeBERT-Java --per_gpu_eval_batch_size 10 --exp_name "TEAMMATES_single"

python3 eval_trace_single_SAD.py --data_dir ../../data/TeaStore --model_path ../../../models/CodeBERT-Java --per_gpu_eval_batch_size 10 --exp_name "TeaStore_single"

deactivate

cd ../..
