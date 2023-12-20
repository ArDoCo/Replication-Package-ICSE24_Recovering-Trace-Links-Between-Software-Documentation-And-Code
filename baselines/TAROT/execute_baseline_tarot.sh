#!/bin/bash
set -e

# Get the absolute path of the script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if the script is being executed from the current directory
if [ "$(pwd)" != "$SCRIPT_DIR" ]; then
  echo "Please run the script from the directory where it is located."
  exit 1
fi

echo "Cleanup previous results"
# In "dataset" folder remove all files that reside in a folder called output
find ./dataset -type d -name 'output' -exec sh -c 'rm -f "$0"/*.csv' {} \;

echo "Run TAROT"
mvn -B compile exec:java -Dexec.mainClass="Start" 

echo "Copy results to results/raw/TAROT"
# Copy CSVs from dataset/*/output/*.csv to results/raw/TAROT
find dataset -type d -name 'output' -exec sh -c 'cp "$0"/*.csv ../../results/raw/TAROT/' {} \;

echo "Calculate threshold files for optimizing threshold"
cd ../../results/raw/TAROT/
python3 create_threshold_filtered.py

echo "Calculate results for TAROT"
cd ../../../evaluator/
bash evaluateTAROT.sh
