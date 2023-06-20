#!/bin/bash
echo "BigBlueButton"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadSamTlr_bigbluebutton.csv -g ../data/SAD-SAM-goldstandards/goldstandard-bigbluebutton.csv -c 1020

echo "JabRef"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadSamTlr_jabref.csv -g ../data/SAD-SAM-goldstandards/goldstandard-jabref.csv -c 78

echo "MediaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadSamTlr_mediastore.csv -g ../data/SAD-SAM-goldstandards/goldstandard-mediastore.csv -c 518

echo "TEAMMATES"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadSamTlr_teammates.csv -g ../data/SAD-SAM-goldstandards/goldstandard-teammates.csv -c 1584

echo "TeaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadSamTlr_teastore.csv -g ../data/SAD-SAM-goldstandards/goldstandard-teastore.csv -c 473

read -p "Press Enter To Close..."