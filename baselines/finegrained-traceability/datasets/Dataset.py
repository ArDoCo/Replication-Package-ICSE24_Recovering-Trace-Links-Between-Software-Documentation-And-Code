import abc, re, logging

from pathlib import Path

from datasets.SolutionMatrix import SolutionMatrix
from preprocessing.CodeASTTokenizer import JavaCodeASTTokenizer, CCodeASTTokenizer, MixedASTTokenizer
from preprocessing.Tokenizer import JavaDocDescriptionOnlyTokenizer, WordTokenizer
from utility import FileUtil

log = logging.getLogger(__name__)

DATASETS_FOLDER = Path(__file__).parent


class Dataset(abc.ABC):
    """
    Subclasses represent the evaluation datasets
    """

    def __init__(self):
        self._solution_matrix = None

    def all_original_code_file_names(self):
        return [name.strip("\n") for name in FileUtil.read_textfile_into_lines_list(self._all_code_filenames_file())]

    def all_original_req_file_names(self):
        return [name.strip("\n") for name in FileUtil.read_textfile_into_lines_list(self._all_req_filenames_file())]

    def encoding(self):
        return "utf-8-sig"


    @abc.abstractmethod
    def _all_code_filenames_file(self):
        pass

    @abc.abstractmethod
    def _all_req_filenames_file(self):
        pass

    @abc.abstractmethod
    def name(self):
        pass

    @abc.abstractmethod
    def folder(self):
        pass

    @abc.abstractmethod
    def code_folder(self):
        pass

    @abc.abstractmethod
    def req_folder(self):
        pass

    @abc.abstractmethod
    def method_callgraph(self):
        """
        returns the precalculated method call graph dictionary
        """
        pass

    @abc.abstractmethod
    def method_callgraph_path(self):
        pass

    @abc.abstractmethod
    def class_callgraph_path(self):
        pass

    def solution_matrix(self):
        if self._solution_matrix is None:
            self._read_solution_matrix()
        return self._solution_matrix

    @abc.abstractmethod
    def _read_solution_matrix(self):
        pass

    @abc.abstractmethod
    def raw_call_graph_path(self):
        """
        Returns the path to the raw call graph file (generated by an external call graph parser)
        """
        pass

    @abc.abstractmethod
    def class_callgraph(self):
        pass

    @abc.abstractmethod
    def is_english(self):
        pass

    @abc.abstractmethod
    def classification_file_path(self):
        pass

    @abc.abstractmethod
    def code_tokenizer(self):
        pass

    def has_UCT(self):
        return False

class JavaDataset(Dataset):

    def code_tokenizer(self):
       return JavaCodeASTTokenizer(self, JavaDocDescriptionOnlyTokenizer(self, not self.is_english()))

class MediaStore(JavaDataset):

    FOLDER = DATASETS_FOLDER / "MediaStore"
    DOCU_DIR = FOLDER / "docs"
    CODE_DIR = FOLDER / "code"

    def __init__(self):
        super().__init__()

    def name(self):
        return "MediaStore"

    def folder(self):
        return self.FOLDER

    def code_folder(self):
        return self.CODE_DIR

    def req_folder(self):
        return self.DOCU_DIR

    def is_english(self):
        return True

    def has_UCT(self):
        return False

    def _all_code_filenames_file(self):
        pass

    def _all_req_filenames_file(self):
        pass

    def method_callgraph(self):
        pass

    def method_callgraph_path(self):
        pass

    def class_callgraph_path(self):
        pass

    def _read_solution_matrix(self):
        pass

    def raw_call_graph_path(self):
        pass

    def class_callgraph(self):
        pass

    def classification_file_path(self):
        pass


class JabRef(JavaDataset):

    FOLDER = DATASETS_FOLDER / "jabref"
    DOCU_DIR = FOLDER / "docs"
    CODE_DIR = FOLDER / "code"

    def __init__(self):
        super().__init__()

    def name(self):
        return "jabref"

    def folder(self):
        return self.FOLDER

    def code_folder(self):
        return self.CODE_DIR

    def req_folder(self):
        return self.DOCU_DIR

    def is_english(self):
        return True

    def has_UCT(self):
        return False

    def _all_code_filenames_file(self):
        pass

    def _all_req_filenames_file(self):
        pass

    def method_callgraph(self):
        pass

    def method_callgraph_path(self):
        pass

    def class_callgraph_path(self):
        pass

    def _read_solution_matrix(self):
        pass

    def raw_call_graph_path(self):
        pass

    def class_callgraph(self):
        pass

    def classification_file_path(self):
        pass

class Teammates(JavaDataset):

    FOLDER = DATASETS_FOLDER / "teammates"
    DOCU_DIR = FOLDER / "docs"
    CODE_DIR = FOLDER / "code"

    def __init__(self):
        super().__init__()

    def name(self):
        return "teammates"

    def folder(self):
        return self.FOLDER

    def code_folder(self):
        return self.CODE_DIR

    def req_folder(self):
        return self.DOCU_DIR

    def is_english(self):
        return True

    def has_UCT(self):
        return False

    def _all_code_filenames_file(self):
        pass

    def _all_req_filenames_file(self):
        pass

    def method_callgraph(self):
        pass

    def method_callgraph_path(self):
        pass

    def class_callgraph_path(self):
        pass

    def _read_solution_matrix(self):
        pass

    def raw_call_graph_path(self):
        pass

    def class_callgraph(self):
        pass

    def classification_file_path(self):
        pass

class TeaStore(JavaDataset):

    FOLDER = DATASETS_FOLDER / "TeaStore"
    DOCU_DIR = FOLDER / "docs"
    CODE_DIR = FOLDER / "code"

    def __init__(self):
        super().__init__()

    def name(self):
        return "TeaStore"

    def folder(self):
        return self.FOLDER

    def code_folder(self):
        return self.CODE_DIR

    def req_folder(self):
        return self.DOCU_DIR

    def is_english(self):
        return True

    def has_UCT(self):
        return False

    def _all_code_filenames_file(self):
        pass

    def _all_req_filenames_file(self):
        pass

    def method_callgraph(self):
        pass

    def method_callgraph_path(self):
        pass

    def class_callgraph_path(self):
        pass

    def _read_solution_matrix(self):
        pass

    def raw_call_graph_path(self):
        pass

    def class_callgraph(self):
        pass

    def classification_file_path(self):
        pass

    def code_tokenizer(self):
        return MixedASTTokenizer(self, JavaDocDescriptionOnlyTokenizer(self, not self.is_english()), WordTokenizer(self, not self.is_english()))

class BigBlueButton(JavaDataset):

    FOLDER = DATASETS_FOLDER / "bigbluebutton"
    DOCU_DIR = FOLDER / "docs"
    CODE_DIR = FOLDER / "code"

    def __init__(self):
        super().__init__()

    def name(self):
        return "bigbluebutton"

    def folder(self):
        return self.FOLDER

    def code_folder(self):
        return self.CODE_DIR

    def req_folder(self):
        return self.DOCU_DIR

    def is_english(self):
        return True

    def has_UCT(self):
        return False

    def _all_code_filenames_file(self):
        pass

    def _all_req_filenames_file(self):
        pass

    def method_callgraph(self):
        pass

    def method_callgraph_path(self):
        pass

    def class_callgraph_path(self):
        pass

    def _read_solution_matrix(self):
        pass

    def raw_call_graph_path(self):
        pass

    def class_callgraph(self):
        pass

    def classification_file_path(self):
        pass

    def code_tokenizer(self):
        return MixedASTTokenizer(self, JavaDocDescriptionOnlyTokenizer(self, not self.is_english()), WordTokenizer(self, not self.is_english()))