# ReplicationPackageICSE24
This is the replication package for the paper "Recovering Trace Links Between Software Documentation And Code".

## Structure
Each folder contains a README.md file with instructions on how to run the code.
The replication package is structured as follows:
* ardoco: contains the source code the extended ArDoCo approach
* baselines: contains the baselines used in the paper
* data: contains the data used in the paper
* evaluator: contains helper scripts to evaluate the generated results
* results: contains the results of the experiments

## Requirements
In summary, the following tools are required to run the experiments:
* Python 3
* Java 17
* Maven 3

In addition, we suggest a machine with at least 8GB of RAM.

## Replication
The details for the replication of the baseline experiments are described in the README.md files of the respective baseline folder.

### Replication of the ArDoCo experiments
The execution of the ArDoCo experiments is encapsulated in a JUnit test suite: [TraceLinkEvaluationIT](ardoco/tests/tests-tlr/src/test/java/edu/kit/kastel/mcse/ardoco/core/tests/integration/TraceLinkEvaluationIT.java).

In order to run the experiments, please execute the following command within the ardoco folder: `mvn -q -P tlr clean test -Dsurefire.failIfNoSpecifiedTests=false -Dtest=TraceLinkEvaluationIT`

You can also run `mvn -P tlr clean test -Dsurefire.failIfNoSpecifiedTests=false -Dtest=TraceLinkEvaluationIT` to produce more verbose output of maven.

The results are printed in a format like this:
```
TEASTORE (SadSamCodeTraceabilityLinkRecoveryEvaluation):
        Precision:    1.00 (min. expected: 1.00)
        Recall:       0.71 (min. expected: 0.71)
        F1:           0.83 (min. expected: 0.83)
        Accuracy:     0.98 (min. expected: 0.98)
        Specificity:  1.00 (min. expected: 1.00)
        Phi Coef.:    0.83 (min. expected: 0.83)

```
Please note that the `min. expected` values refer to thresholds that are used to determine whether the test shall fail because of some unexpected degradation of the results.

* `SadSamCodeTraceabilityLinkRecoveryEvaluation`: evaluation of the transitive trace links between SAD and code (via SAM).
* `SamCodeTraceabilityLinkRecoveryEvaluation`: evaluation of the trace links between SAM and code.
* `SadSamTraceabilityLinkRecoveryEvaluation`: evaluation of the trace links between SAD and SAM.
