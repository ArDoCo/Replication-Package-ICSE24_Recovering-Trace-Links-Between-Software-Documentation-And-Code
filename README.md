# ReplicationPackageICSE24
This is the replication package for the paper "Recovering Trace Links Between Software Documentation And Code".

We've integrated our new approach into the replication package of the ArDoCo approach, which is openly available at https://doi.org/10.5281/zenodo.7555194.
As we created our implementation as an extension of ArDoCo, we continued to use ArDoCo's package names, which refer to the name of the research group that originally created ArDoCo.
We would like to emphasize that this does not imply that we are ourselves members of that research group.

## Structure
Each folder contains a README.md file with instructions on how to run the code.
The replication package is structured as follows:
* ardoco+arcotl: contains the source code of ArCoTL and ArDoCo
* baselines: contains the baselines used in the paper
* data: contains the data used in the paper, including the textual software architecture documentation, the architecture models and the gold standards.
* evaluator: contains helper scripts to evaluate the generated results
* results: contains the results of the experiments

## Requirements
In summary, the following tools are required to run the experiments:
* Python 3
* Java 17
* Maven 3

In addition, we suggest a machine with at least 8GB of RAM.

## Example
This section illustrates the task of recovering trace links between SAD and code for the TEASTORE system.

### Artifacts
1. The source code of the TeaStore system is located here: [ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/model_2022/code](ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/model_2022/code). You can either use the code as described in the directory or you can use the acm file (JSON representation of code elements).
2. The architecture model of TeaStore can be found here: [ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/model_2020/pcm](ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/model_2020/pcm). In this directory, you can find the PCM repository (teastore.repository) that contains the components and interfaces of the system (XML representation).
3. Finally, you can find the textual software architecture documentation of TeaStore here: [ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/text_2020/teastore.txt](ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/text_2020/teastore.txt). The documentation is provided in the form of a single text file.

### Code to Documentation Trace Links
We aim to recover trace links between the code and the documentation of TeaStore.
The trace links that shall be recovered are provided as ground truth in the following file:
[ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/text_2020/goldstandard_code-2022.csv](ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/text_2020/goldstandard_code-2022.csv)

This file contains a mapping between the sentences of the documentation and the code elements that shall be linked to the respective sentence.

If we consider the first two sentences of the documentation ...
```text
The TeaStore consists of 5 replicatable services and a single Registry instance.
The WebUI service retrieves images from the Image Provider.
```

We should find the following trace links:
```csv
sentenceID,codeID
1,services/tools.descartes.teastore.registry/src/main/java/tools/descartes/teastore/registry/
2,services/tools.descartes.teastore.image/src/main/java/tools/descartes/teastore/image/
2,services/tools.descartes.teastore.webui/src/main/java/tools/descartes/teastore/webui/
2,tools/test_webui.sh
...
```

### Code to Architecture Model Trace Links
We use trace links between documentation and architecture models as an intermediate step for recovering trace links between documentation and code.
The architecture model contains multiple components and interfaces.
For TeaStore we have the following components with their resp. IDs:
```text
* WebUI (_bC13QDVWEeqPG_FgW3bi6Q)
* Registy (_dhM6oDVXEeqPG_FgW3bi6Q)
* Persistence (_lnx1oDVWEeqPG_FgW3bi6Q)
* Recommender (_m3fxEDVWEeqPG_FgW3bi6Q)
* Auth (_AiuxcDVdEeqPG_FgW3bi6Q)
...
```

Our approach recovers trace links between the code and the architecture model.
The trace links that shall be recovered are provided as ground truth in the following file: [ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/model_2022/goldstandard_sam_2020_code.csv](ardoco+arcotl/tests/tests-base/src/main/resources/benchmark/teastore/model_2022/goldstandard_sam_2020_code.csv)

We connect the code elements (ce_ids) to the architecture model elements (ae_id).
For convenience, we also provide the name of the architecture model element (ae_name).
We expect our approach to find trace links like the following:

```csv
ae_id,ae_name,ce_ids
_AiuxcDVdEeqPG_FgW3bi6Q,Component: Auth,services/tools.descartes.teastore.auth/src/main/java/tools/descartes/teastore/auth/
_BYKdQDVgEeqPG_FgW3bi6Q,Interface: RecommenderStrategy,services/tools.descartes.teastore.recommender/src/main/java/tools/descartes/teastore/recommender/algorithm/IRecommender.java
...
```


## Replication
The details for the replication of the baseline experiments are described in the README.md files of the respective baseline folder.

### Replication of the ArDoCo+ArCoTL experiments
The execution of the ArDoCo+ArCoTL (TransArC) experiments is encapsulated in a JUnit test suite: [TraceLinkEvaluationIT](ardoco+arcotl/tests/tests-tlr/src/test/java/edu/kit/kastel/mcse/ardoco/core/tests/integration/TraceLinkEvaluationIT.java).

In order to run the experiments, please execute the following command within the ardoco+arcotl folder: `mvn -q -P tlr clean test -Dsurefire.failIfNoSpecifiedTests=false -Dtest=TraceLinkEvaluationIT`

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
