from TraceabilityRunner import BaseLineMCRunner, \
    OUTPUT_DIR, FileLevelWMDMCRunner, \
    FileLevelAvgMCRunner
from datasets.Dataset import MediaStore, Teammates, TeaStore, BigBlueButton, JabRef
from utility.FileUtil import setup_clear_dir

ENGLISH_FASTTEXT_MODEL_PATH = "/replication/baselines/models/cc.en.300.bin"
ITALIAN_FASTTEXT_MODEL_PATH = "/replication/baselines/models/cc.it.300.bin"
models = {'english': ENGLISH_FASTTEXT_MODEL_PATH, 'italian': ITALIAN_FASTTEXT_MODEL_PATH}

setup_clear_dir(OUTPUT_DIR)

"""
PRECALCULATION EXAMPLE

1. Modify ENGLISH_FASTTEXT_MODEL_PATH and ITALIAN_FASTTEXT_MODEL_PATH to point to your fastText model location
2. Run the code below
3. Caution: With matrix_file_path=None, artifact_map_file_path=None the output precalculated files will be written
            to their default location which will overwrite the existing files. Change the location to avoid this.

b = BaseLineMCRunner(MediaStore())
b.precalculate(models, matrix_file_path=None, artifact_map_file_path=None)

"""
FTLR_DEFAULT_FINAL_THRESHOLD = 0.44
FTLR_DEFAULT_MAJORITY_THRESHOLD = 0.59

projects = [MediaStore(), Teammates(), TeaStore(), BigBlueButton(), JabRef()]

for project in projects:
    print("Project: " + project.name())
    b = BaseLineMCRunner(project)
    b.precalculate(models, matrix_file_path=None, artifact_map_file_path=None)
    b.output_trace_links([FTLR_DEFAULT_FINAL_THRESHOLD], [FTLR_DEFAULT_MAJORITY_THRESHOLD], matrix_file_path=None,
                        artifact_map_file_path=None, final=FTLR_DEFAULT_FINAL_THRESHOLD,
                        maj=FTLR_DEFAULT_MAJORITY_THRESHOLD)
