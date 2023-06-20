#!/bin/bash

echo "FileLevelAvgMc"
echo "BigBlueButton FileLevelAvgMc"
java -jar evaluator.jar -t ../results/raw/ftlr/bigbluebutton_FileLevelAvgMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-bigbluebutton.csv -c 47600 -w
echo "JabRef FileLevelAvgMc"
java -jar evaluator.jar -t ../results/raw/ftlr/jabref_FileLevelAvgMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-jabref.csv -c 26000 -w
echo "MediaStore FileLevelAvgMc"
java -jar evaluator.jar -t ../results/raw/ftlr/mediastore_FileLevelAvgMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-mediastore.csv -c 3589 -w
echo "TEAMMATES FileLevelAvgMc"
java -jar evaluator.jar -t ../results/raw/ftlr/teammates_FileLevelAvgMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-teammates.csv -c 165330 -w
echo "TeaStore FileLevelAvgMc"
java -jar evaluator.jar -t ../results/raw/ftlr/teastore_FileLevelAvgMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-teastore.csv -c 8815 -w

###
echo ""
echo "FileLevelWMDMc"
echo "BigBlueButton FileLevelWMDMc"
java -jar evaluator.jar -t ../results/raw/ftlr/bigbluebutton_FileLevelWMDMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-bigbluebutton.csv -c 47600 -w
echo "JabRef FileLevelWMDMc"
java -jar evaluator.jar -t ../results/raw/ftlr/jabref_FileLevelWMDMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-jabref.csv -c 26000 -w
echo "MediaStore FileLevelWMDMc"
java -jar evaluator.jar -t ../results/raw/ftlr/mediastore_FileLevelWMDMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-mediastore.csv -c 3589 -w
echo "TEAMMATES FileLevelWMDMc"
java -jar evaluator.jar -t ../results/raw/ftlr/teammates_FileLevelWMDMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-teammates.csv -c 165330 -w
echo "TeaStore FileLevelWMDMc"
java -jar evaluator.jar -t ../results/raw/ftlr/teastore_FileLevelWMDMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-teastore.csv -c 8815 -w

###
echo ""
echo "BigBlueButton FTLRMc"
java -jar evaluator.jar -t ../results/raw/ftlr/bigbluebutton_FTLRMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-bigbluebutton.csv -c 47600 -w
echo "JabRef FTLRMc"
java -jar evaluator.jar -t ../results/raw/ftlr/jabref_FTLRMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-jabref.csv -c 26000 -w
echo "MediaStore FTLRMc"
java -jar evaluator.jar -t ../results/raw/ftlr/mediastore_FTLRMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-mediastore.csv -c 3589 -w
echo "TEAMMATES FTLRMc"
java -jar evaluator.jar -t ../results/raw/ftlr/teammates_FTLRMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-teammates.csv -c 165330 -w
echo "TeaStore FTLRMc"
java -jar evaluator.jar -t ../results/raw/ftlr/teastore_FTLRMc_tracelinks.csv -g ../data/SAD-Code-goldstandards/goldstandard-teastore.csv -c 8815 -w

echo ""
read -p "Press Enter To Close..."