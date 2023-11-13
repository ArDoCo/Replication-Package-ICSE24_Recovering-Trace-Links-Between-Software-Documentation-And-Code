# FTLR Adaptation
The code for the FTLR baseline is an adaptation of the code from the original [FTLR repository](https://github.com/tobhey/finegrained-traceability).


The original code stems from the ICSME'2021 paper ["Improving Traceability Link Recovery Using Fine-grained Requirements-to-Code Relations"](https://doi.org/10.1109/ICSME52107.2021.00008).
It uses word embedding-based fine-grained requirements-to-code relations in automated traceability link recovery.

## Running FTLR
You can find the requirements in the [`INSTALL.md`](INSTALL.md) file of FTLR.

To run the experiments, you have to run the [`App.py`](App.py). In this file you also find all configurations used in the paper.

## Data 
The data used in the paper is located in the `datasets` folder.

For each project, you find the following files in the `datasets` folder:
* In `docs` you can find the documentation of the project. Each file contains one sentence of the documentation.
* In `code_filenames.txt` you find all source code filenames within the project. This is needed by FTLR.
* In `req_filenames.txt` you can find the filenames of all requirements. In our case these are the names of the files in `docs`.
* Finally, in `goldstandard.txt` you find the gold standard for the project. This is needed to evaluate the results.

## Attribution of the code used
Hey, T., Chen, F., Weigelt, S., & Tichy, W. F. (2021, September). Improving traceability link recovery using fine-grained requirements-to-code relations. In *2021 IEEE International Conference on Software Maintenance and Evolution (ICSME)* (pp. 12-22). IEEE.
