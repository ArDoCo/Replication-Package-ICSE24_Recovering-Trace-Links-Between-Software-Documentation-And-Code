#!/bin/bash

cd ..
git remote add -f evaluator git@github.com:ArDoCo/Evaluator.git
git fetch evaluator main
git subtree pull --prefix evaluator/evaluator-src evaluator main --squash
cd evaluator

read -n 1 -s -p "Press any key to exit..."