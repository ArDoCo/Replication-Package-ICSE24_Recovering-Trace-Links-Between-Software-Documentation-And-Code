#!/bin/bash
# Run bash evaluateTAROT.sh > TAROT-evaluation.txt 2>&1
set -e

current_dir=$(pwd)

cd ../results/raw/TAROT

# BigBlueButton
echo "Evaluating BigBlueButton"
for file in $(find threshold_filtered -name "bigbluebutton*.csv"); do
    # Get the filename without the extension
    filename=$(basename -- "$file")
    # Run the evaluator
    echo "Running evaluator for $filename"
    java -jar ../../../evaluator/evaluator.jar -t "threshold_filtered/$filename" -g ../../../data/SAD-Code-goldstandards/goldstandard-bigbluebutton.csv -c 47600 -w -m
done

# JabRef
echo "Evaluating JabRef"
for file in $(find threshold_filtered -name "jabref*.csv"); do
    # Get the filename without the extension
    filename=$(basename -- "$file")
    # Run the evaluator
    echo "Running evaluator for $filename"
    java -jar ../../../evaluator/evaluator.jar -t "threshold_filtered/$filename" -g ../../../data/SAD-Code-goldstandards/goldstandard-jabref.csv -c 26000 -w -m
done

# MediaStore
echo "Evaluating MediaStore"
for file in $(find threshold_filtered -name "MediaStore*.csv"); do
    # Get the filename without the extension
    filename=$(basename -- "$file")
    # Run the evaluator
    echo "Running evaluator for $filename"
    java -jar ../../../evaluator/evaluator.jar -t "threshold_filtered/$filename" -g ../../../data/SAD-Code-goldstandards/goldstandard-mediastore.csv -c 3589 -w -m
done

# TEAMMATES
echo "Evaluating TEAMMATES"
for file in $(find threshold_filtered -name "teammates*.csv"); do
    # Get the filename without the extension
    filename=$(basename -- "$file")
    # Run the evaluator
    echo "Running evaluator for $filename"
    java -jar ../../../evaluator/evaluator.jar -t "threshold_filtered/$filename" -g ../../../data/SAD-Code-goldstandards/goldstandard-teammates.csv -c 165330 -w -m
done

# TeaStore
echo "Evaluating TeaStore"
for file in $(find threshold_filtered -name "TeaStore*.csv"); do
    # Get the filename without the extension
    filename=$(basename -- "$file")
    # Run the evaluator
    echo "Running evaluator for $filename"
    java -jar ../../../evaluator/evaluator.jar -t "threshold_filtered/$filename" -g ../../../data/SAD-Code-goldstandards/goldstandard-teastore.csv -c 8815 -w -m
done

cd $current_dir
