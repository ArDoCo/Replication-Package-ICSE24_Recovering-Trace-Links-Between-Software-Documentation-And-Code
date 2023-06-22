package experiment.preprocess.ocean.parsecode.java;

public class RunCodeProcess {
    public static void main(String[] args) {
        String project = "jabref";
        // 1. parse ASR
       JavaParse.parseSourceCode(project);

        // 2. parse ast xml、srcml xml
        AnalyzeCodeXml analyzeCodeXml = new AnalyzeCodeXml(project);
        analyzeCodeXml.run();
    }
}
