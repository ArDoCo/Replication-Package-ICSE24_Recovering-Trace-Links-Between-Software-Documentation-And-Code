import abc
from abc import ABC

import pandas as pd

from datasets.Dataset import Dataset
from precalculating.TraceLinkDataStructure import ElementLevelTraceLinkDataStructure
from utility import FileUtil


class ElementFilter(ABC):

    def filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, dataset: Dataset):
        if dataset.classification_file_path() is not None and FileUtil.file_exists(dataset.classification_file_path()):
            df = pd.read_csv(dataset.classification_file_path(), delimiter=',', encoding='utf8', header=0,
                             names=['file', 'ID', 'line', 'functional', 'Function', 'Behavior', 'Data', 'OnlyF', 'F',
                                    'OnlyQ', 'Q', 'UserRelated'])
            for file in df.file.unique():
                idxs = df.index[df['file'] == file].tolist()
                for i in idxs:
                        trace_link_data_structure = self._filter(trace_link_data_structure, df, file, i)
        return trace_link_data_structure

    @abc.abstractmethod
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        pass

    def check_and_remove(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx, columns):
        result = False
        for column,value in columns:
            result = result or (df.loc[idx, column] == value)
        if result:
            trace_link_data_structure.remove_req_file_element(file, df.loc[idx].ID)
        return trace_link_data_structure


class NFRElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        columns = [("F", '0'), ("F", 0)]
        return self.check_and_remove(trace_link_data_structure,df,file,idx,columns)


class OnlyQualityElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        columns = [("OnlyQ", '1'), ("OnlyQ", 1)]
        return self.check_and_remove(trace_link_data_structure, df, file, idx, columns)


class QualityElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        columns = [("Q", '1'), ("Q", 1)]
        return self.check_and_remove(trace_link_data_structure, df, file, idx, columns)


class DataElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        columns = [("Data", '1'), ("Data", 1)]
        return self.check_and_remove(trace_link_data_structure, df, file, idx, columns)


class BehaviorElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        columns = [("Behavior", '1'), ("Behavior", 1)]
        return self.check_and_remove(trace_link_data_structure, df, file, idx, columns)

class BehaviorAndQualityElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        bf = BehaviorElementFilter()
        qf = QualityElementFilter()
        tds = bf._filter(trace_link_data_structure, df, file, idx)
        return qf._filter(tds, df, file, idx)


class BehaviorAndNonFunctionalElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        bf = BehaviorElementFilter()
        nfr = NFRElementFilter()
        tds = bf._filter(trace_link_data_structure, df, file, idx)
        return nfr._filter(tds, df, file, idx)

class UserRelatedElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        columns = [("UserRelated", '1'), ("UserRelated", 1)]
        return self.check_and_remove(trace_link_data_structure, df, file, idx, columns)


class UserRelatedBehaviorFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        bf = BehaviorElementFilter()
        ur = UserRelatedElementFilter()
        tds = bf._filter(trace_link_data_structure, df, file, idx)
        return ur._filter(tds, df, file, idx)


class UserRelatedNFRElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        nfr = NFRElementFilter()
        ur = UserRelatedElementFilter()
        tds = nfr._filter(trace_link_data_structure, df, file, idx)
        return ur._filter(tds, df, file, idx)


class UserRelatedBehaviorNFRElementFilter(ElementFilter):
    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        bf = BehaviorElementFilter()
        nfr = NFRElementFilter()
        ur = UserRelatedElementFilter()
        tds = bf._filter(trace_link_data_structure, df, file, idx)
        tds = nfr._filter(tds, df, file, idx)
        return ur._filter(tds, df, file, idx)


class FuncConcernsElementFilter(ElementFilter):

    def __init__(self, function_factor=float(0.98), behavior_factor=float(1.02), data_factor=float(1.05)):
        super().__init__()
        self.function_factor = function_factor
        self.behavior_factor = behavior_factor
        self.data_factor = data_factor

    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        factor = 1.0
        if df.loc[idx].Function == '1' or df.loc[idx].Function == 1:
            factor = factor - (1 - self.function_factor)
        if df.loc[idx].Behavior == '1' or df.loc[idx].Behavior == 1:
            factor = factor - (1 - self.behavior_factor)
        if df.loc[idx].Data == '1' or df.loc[idx].Data == 1:
            factor = factor - (1 - self.data_factor)
        if not factor == 1.0:
            for code_file in trace_link_data_structure.all_code_file_names():
                for code_element in trace_link_data_structure.all_code_elements_of(code_file):
                    trace_link_data_structure.adapt_similarity(df.loc[idx].ID, code_element, factor)
        # if df.loc[idx].Function == '1' or df.loc[idx].Function == 1:
        #   for code_file in trace_link_data_structure.all_code_file_names():
        #      for code_element in trace_link_data_structure.all_code_elements_of(code_file):
        #         trace_link_data_structure.adapt_similarity(element, code_element, self.function_factor)
        # elif df.loc[idx].Behavior == '1' or df.loc[idx].Behavior == 1:
        #   for code_file in trace_link_data_structure.all_code_file_names():
        #      for code_element in trace_link_data_structure.all_code_elements_of(code_file):
        #         trace_link_data_structure.adapt_similarity(element, code_element, self.behavior_factor)
        # elif df.loc[idx].Data == '1' or df.loc[idx].Data == 1:
        #   for code_file in trace_link_data_structure.all_code_file_names():
        #      for code_element in trace_link_data_structure.all_code_elements_of(code_file):
        #         trace_link_data_structure.adapt_similarity(element, code_element, self.data_factor)

        return trace_link_data_structure


class FuncConcernsNFRElementFilter(FuncConcernsElementFilter):

    def _filter(self, trace_link_data_structure: ElementLevelTraceLinkDataStructure, df, file, idx):
        if df.loc[idx].functional == '0' or df.loc[idx].functional == 0:
            trace_link_data_structure.remove_req_file_element(file, df.loc[idx].ID)
            return trace_link_data_structure
        else:
            return super()._filter(trace_link_data_structure, df, file, idx)
