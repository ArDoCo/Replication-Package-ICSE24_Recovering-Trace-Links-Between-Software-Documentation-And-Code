#!/bin/bash
echo "#### TRANSITIVE ####"

echo "BigBlueButton"
java -jar evaluator.jar -t ../results/raw/ArDoCo/transitive/sadCodeTlr_bigbluebutton.csv -g ../data/SAD-Code-goldstandards/goldstandard-bigbluebutton.csv -c 47600

echo "JabRef"
java -jar evaluator.jar -t ../results/raw/ArDoCo/transitive/sadCodeTlr_jabref.csv -g ../data/SAD-Code-goldstandards/goldstandard-jabref.csv -c 26000

echo "MediaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/transitive/sadCodeTlr_mediastore.csv -g ../data/SAD-Code-goldstandards/goldstandard-mediastore.csv -c 3589

echo "TEAMMATES"
java -jar evaluator.jar -t ../results/raw/ArDoCo/transitive/sadCodeTlr_teammates.csv -g ../data/SAD-Code-goldstandards/goldstandard-teammates.csv -c 165330

echo "TeaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/transitive/sadCodeTlr_teastore.csv -g ../data/SAD-Code-goldstandards/goldstandard-teastore.csv -c 8815

echo "#### DIRECT ####"

echo "BigBlueButton"
java -jar evaluator.jar -t ../results/raw/ArDoCo/direct/sadCodeTlr_bigbluebutton.csv -g ../data/SAD-Code-goldstandards/goldstandard-bigbluebutton.csv -c 47600

echo "JabRef"
java -jar evaluator.jar -t ../results/raw/ArDoCo/direct/sadCodeTlr_jabref.csv -g ../data/SAD-Code-goldstandards/goldstandard-jabref.csv -c 26000

echo "MediaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/direct/sadCodeTlr_mediastore.csv -g ../data/SAD-Code-goldstandards/goldstandard-mediastore.csv -c 3589

echo "TEAMMATES"
java -jar evaluator.jar -t ../results/raw/ArDoCo/direct/sadCodeTlr_teammates.csv -g ../data/SAD-Code-goldstandards/goldstandard-teammates.csv -c 165330

echo "TeaStore"
java -jar evaluator.jar -t ../results/raw/ArDoCo/direct/sadCodeTlr_teastore.csv -g ../data/SAD-Code-goldstandards/goldstandard-teastore.csv -c 8815

read -p "Press Enter To Close..."