package experiment.preprocess.ocean;

import experiment.enums.ProjectEnum;
import experiment.preprocess.StanfordNLP;
import experiment.preprocess.TextPreprocess;
import experiment.project.Project;
import util.FileIOUtil;

import java.io.File;

public class ProcessText {
    private static StanfordNLP stanfordNlpUtil = new StanfordNLP();

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ProjectEnum projectEnum = ProjectEnum.JABREF; // select project
        Class projectClass = Class.forName(projectEnum.getName());
        Project project = (Project) projectClass.newInstance();
        initDirectory(project);
        processReqText(project);
    }

    private static void initDirectory(Project project) {
        FileIOUtil.initDirectory(project.getReqPath());
    }

    private static void processReqText(Project project) {
        File idDirectory = new File(project.getUnProcessedIssueDescripDirPath());
        if (idDirectory.isDirectory()) {
            for (File f : idDirectory.listFiles()) {
                if (!f.getName().contains(".txt"))
                    continue;
                String text = FileIOUtil.readFile(f.getPath());
                TextPreprocess textPreprocess = new TextPreprocess(text);
                String processedText = textPreprocess.doReqTextProcess();
                FileIOUtil.continueWriteFile(processedText, project.getReqPath() + "/" + f.getName());
            }
        }
    }
}