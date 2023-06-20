#!/bin/bash

cd ./evaluator-src
mvn -Dmaven.test.skip package
cp -u target/evaluator.jar ../evaluator.jar
cd ..

read -n 1 -s -p "Press any key to exit..."