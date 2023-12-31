#!/bin/bash
set -e

# Get the absolute path of the script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if the script is being executed from the current directory
if [ "$(pwd)" != "$SCRIPT_DIR" ]; then
  echo "Please run the script from the directory where it is located."
  exit 1
fi

echo "Run FTLR"
. venv/bin/activate
python3 App.py
deactivate

echo "Copy results to results/raw/FTLR"
# Copy CSVs from datasets/*/output/*.csv to results/raw/FTLR
find datasets -type d -name 'output' -exec sh -c 'cp "$0"/*.csv ../../results/raw/ftlr/' {} \;

echo "Calculate results for FTLR"
cd ../../evaluator/
bash evaluateFTLR.sh
