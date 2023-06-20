#!/bin/bash

cd ./evaluator-src
mvn package
cp -u target/evaluator.jar ../evaluator.jar
cd ..