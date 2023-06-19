#!/bin/bash
echo "BigBlueButton"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadCodeTlr_bigbluebutton.csv -g ../data/enrolled-goldstandards/goldstandard-bigbluebutton.csv -c 47600

echo "JabRef"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadCodeTlr_jabref.csv -g ../data/enrolled-goldstandards/goldstandard-jabref.csv -c 26000

echo "MediaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadCodeTlr_mediastore.csv -g ../data/enrolled-goldstandards/goldstandard-mediastore.csv -c 3589

echo "TEAMMATES"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadCodeTlr_teammates.csv -g ../data/enrolled-goldstandards/goldstandard-teammates.csv -c 165330

echo "TeaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/sadCodeTlr_teastore.csv -g ../data/enrolled-goldstandards/goldstandard-teastore.csv -c 8815