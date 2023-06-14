
from TraceabilityRunner import BaseLineRunner, BaseLineCDRunner, \
    BaseLineUCTRunner, BaseLineMCRunner, \
    BaseLineUCTMCRunner, BaseLineUCTCDRunner, BaseLineUCTMCCDRunner, \
    BaseLineMCCDRunner, OUTPUT_DIR, BaseLineUCTMCCDCCRunner, FileLevelWMDRunner,FileLevelWMDUCTRunner, FileLevelWMDMCRunner, \
    FileLevelWMDUCTMCRunner, FileLevelAvgRunner, FileLevelAvgUCTRunner, FileLevelAvgMCRunner, FileLevelAvgUCTMCRunner, \
    ElementLevelAvgRunner, ElementLevelAvgCDRunner, ElementLevelAvgMCRunner, ElementLevelAvgUCTRunner, \
    ElementLevelAvgUCTMCRunner, ElementLevelAvgMCCDRunner, ElementLevelAvgUCTCDRunner, ElementLevelAvgUCTMCCDRunner
from traceLinkProcessing.ElementFilter import ElementFilter, NFRElementFilter, OnlyQualityElementFilter, \
    QualityElementFilter,  DataElementFilter, BehaviorElementFilter, \
    BehaviorAndQualityElementFilter, FuncConcernsElementFilter, \
    FuncConcernsNFRElementFilter, BehaviorAndNonFunctionalElementFilter, \
    UserRelatedElementFilter, UserRelatedNFRElementFilter, \
    UserRelatedBehaviorNFRElementFilter
from datasets.Dataset import MediaStore, JabRef, TeaStore, Teammates, BigBlueButton
from utility import Util
from utility.FileUtil import setup_clear_dir

ENGLISH_FASTTEXT_MODEL_PATH = "/mnt/raid1/diss/INDIRECT/models/cc.en.300.bin"
ITALIAN_FASTTEXT_MODEL_PATH = "/mnt/raid1/diss/INDIRECT/models/cc.it.300.bin"
models = {'english': ENGLISH_FASTTEXT_MODEL_PATH, 'italian': ITALIAN_FASTTEXT_MODEL_PATH}

FINAL_THRESHOLDS = [0.44]
MAJORITY_THRESHOLDS = [0.59]

setup_clear_dir(OUTPUT_DIR)

"""
PRECALCULATION EXAMPLE

1. Modify ENGLISH_FASTTEXT_MODEL_PATH and ITALIAN_FASTTEXT_MODEL_PATH to point to your fastText model location
2. Run the code below
3. Caution: With matrix_file_path=None, artifact_map_file_path=None the output precalculated files will be written
            to their default location which will overwrite the existing files. Change the location to avoid this.

b = BaseLineRunner(Etour())
b.precalculate(ENGLISH_FASTTEXT_MODEL_PATH, matrix_file_path=None, artifact_map_file_path=None)

"""

b = BaseLineMCRunner(MediaStore(), use_types=True)
#b = BaseLineMCRunner(Teammates(), use_types=True)
#b = BaseLineMCRunner(TeaStore(), use_types=True)
#b = BaseLineMCRunner(BigBlueButton(), use_types=True)
#b = BaseLineMCRunner(JabRef(), use_types=True)
#b.precalculate(models, matrix_file_path=None, artifact_map_file_path=None)

FINAL_THRESHOLDS = Util.get_range_array(0.4, 0.70, 0.001)  # [0.4, 0.41, ..., 0.5]
MAJORITY_THRESHOLDS = Util.get_range_array(0.53, 0.63, 0.001)  # [0.53, 0.54, ..., 0.63]

#b.calculate_f1(FINAL_THRESHOLDS, MAJORITY_THRESHOLDS)
#b.calculate_f1_and_map(FINAL_THRESHOLDS, MAJORITY_THRESHOLDS)
b.output_trace_links([0.44],[0.59])



