from TraceabilityRunner import BaseLineMCRunner, \
    OUTPUT_DIR, FileLevelWMDMCRunner, \
    FileLevelAvgMCRunner
from datasets.Dataset import MediaStore, Teammates, TeaStore, BigBlueButton, JabRef
from utility.FileUtil import setup_clear_dir

ENGLISH_FASTTEXT_MODEL_PATH = "/../models/cc.en.300.bin"
ITALIAN_FASTTEXT_MODEL_PATH = "/../models/cc.it.300.bin"
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

b = BaseLineMCRunner(MediaStore())
# b = BaseLineMCRunner(Teammates())
# b = BaseLineMCRunner(TeaStore())
# b = BaseLineMCRunner(BigBlueButton())
# b = BaseLineMCRunner(JabRef())
b.precalculate(models, matrix_file_path=None, artifact_map_file_path=None)

# b.calculate_f1([FTLR_DEFAULT_FINAL_THRESHOLD], [FTLR_DEFAULT_MAJORITY_THRESHOLD])
# b.calculate_f1_and_map([FTLR_DEFAULT_FINAL_THRESHOLD], [FTLR_DEFAULT_MAJORITY_THRESHOLD])
b.output_trace_links([FTLR_DEFAULT_FINAL_THRESHOLD], [FTLR_DEFAULT_MAJORITY_THRESHOLD], matrix_file_path=None,
                     artifact_map_file_path=None, final=FTLR_DEFAULT_FINAL_THRESHOLD,
                     maj=FTLR_DEFAULT_MAJORITY_THRESHOLD)

"""
FileLevelWMD Variant
"""
FILE_LEVEL_WMD_DEFAULT_THRESHOLD = 0.506

b = FileLevelWMDMCRunner(MediaStore())
# b = FileLevelWMDMCRunner(Teammates())
# b = FileLevelWMDMCRunner(TeaStore())
# b = FileLevelWMDMCRunner(BigBlueButton())
# b = FileLevelWMDMCRunner(JabRef())
b.precalculate(models, matrix_file_path=None, artifact_map_file_path=None)
b.output_trace_links([FILE_LEVEL_WMD_DEFAULT_THRESHOLD], [FILE_LEVEL_WMD_DEFAULT_THRESHOLD], matrix_file_path=None,
                     artifact_map_file_path=None, final=FILE_LEVEL_WMD_DEFAULT_THRESHOLD,
                     maj=FILE_LEVEL_WMD_DEFAULT_THRESHOLD)

"""
FileLevel Average Cosine Distance Variant
"""
FILE_LEVEL_AVG_DEFAULT_THRESHOLD = 0.741

b = FileLevelAvgMCRunner(MediaStore())
# b = FileLevelAvgMCRunner(Teammates())
# b = FileLevelAvgMCRunner(TeaStore())
# b = FileLevelAvgMCRunner(BigBlueButton())
# b = FileLevelAvgMCRunner(JabRef())
b.precalculate(models, matrix_file_path=None, artifact_map_file_path=None)
b.output_trace_links([FILE_LEVEL_AVG_DEFAULT_THRESHOLD], [FILE_LEVEL_AVG_DEFAULT_THRESHOLD], matrix_file_path=None,
                     artifact_map_file_path=None, final=FILE_LEVEL_AVG_DEFAULT_THRESHOLD,
                     maj=FILE_LEVEL_AVG_DEFAULT_THRESHOLD)
