#!/bin/bash
echo "BigBlueButton"
java -jar evaluator.jar -t ../results/raw/ArDoCo/samCodeTlr_bigbluebutton.csv -g ../data/SAM-Code-goldstandards/goldstandard-bigbluebutton.csv -c 13440

echo "JabRef"
java -jar evaluator.jar -t ../results/raw/ArDoCo/samCodeTlr_jabref.csv -g ../data/SAM-Code-goldstandards/goldstandard-jabref.csv -c 12000

echo "MediaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/samCodeTlr_mediastore.csv -g ../data/SAM-Code-goldstandards/goldstandard-mediastore.csv -c 2231

echo "TEAMMATES"
java -jar evaluator.jar -t ../results/raw/ArDoCo/samCodeTlr_teammates.csv -g ../data/SAM-Code-goldstandards/goldstandard-teammates.csv -c 13360

echo "TeaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/samCodeTlr_teastore.csv -g ../data/SAM-Code-goldstandards/goldstandard-teastore.csv -c 3895

read -p "Press Enter To Close..."