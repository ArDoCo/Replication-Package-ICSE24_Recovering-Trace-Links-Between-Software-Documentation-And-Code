package experiment.project;


public class MediaStore implements Project {

    private static String projectName = "MediaStore";
    private static String projectPath = "dataset/MediaStore/";

    //    private static String rtmClassPath = projectPath + "rtm/RTM_CLASS.txt";
//    private static String reqDirPath = projectPath + "req";
//    private static String classDirPath = projectPath + "code";
//
//    private static String classNameDirPath = projectPath + "code_part/class_name";
//    private static String methodNameDirPath = projectPath + "code_part/method_name";
//    private static String commentDirPath = projectPath + "code_part/comment";
//    private static String invokeMethodDirPath = projectPath + "code_part/invoke_method";
//    private static String fieldNameDirPath = projectPath + "code_part/field/fieldName";
//    private static String fieldTypeDirPath = projectPath + "code_part/field/fieldType";
//    private static String paramNameDirPath = projectPath + "code_part/param/paramName";
//    private static String paramTypeDirPath = projectPath + "code_part/param/paramType";
//
//
//    private static String unProcessedUcTitleDirPath = projectPath + "/unprocessed/req_part/uc_title";
//    private static String unProcessedUcPreconDirPath = projectPath + "/unprocessed/req_part/uc_precon";
//    private static String unProcessedUcMainflowDirPath = projectPath + "/unprocessed/req_part/uc_mainflow";
//    private static String unProcessedUcSubflowDirPath = projectPath + "/unprocessed/req_part/uc_subflow";
//    private static String unProcessedUcAlterflowDirPath = projectPath + "/unprocessed/req_part/uc_alterflow";
//
//    private static String ucTitleBitermDirPath = projectPath + "/processed/req_biterm/uc_title";
//    private static String ucPreconBitermDirPath = projectPath + "/processed/req_biterm/uc_precon";
//    private static String ucMainflowBitermDirPath = projectPath + "/processed/req_biterm/uc_mainflow";
//    private static String ucSubflowBitermDirPath = projectPath + "/processed/req_biterm/uc_subflow";
//    private static String ucAlterflowBitermDirPath = projectPath + "/processed/req_biterm/uc_alterflow";
    ProjectConfig config = new ProjectConfig(projectPath);

    @Override
    public String getRtmClassPath() {
        return config.rtmClassPath;
    }

    @Override
    public String getReqPath() {
        return config.reqDirPath;
    }

    @Override
    public String getClassDirPath() {
        return config.classDirPath;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public String getProjectPath() {
        return projectPath;
    }

    @Override
    public String getClsNameDirPath() {
        return config.classNameDirPath;
    }

    @Override
    public String getMethodNameDirPath() {
        return config.methodNameDirPath;
    }

    @Override
    public String getCommentDirPath() {
        return config.commentDirPath;
    }

    @Override
    public String getInvokeMethodDirPath() {
        return config.invokeMethodDirPath;
    }

    @Override
    public String getFieldNameDirPath() {
        return config.fieldNameDirPath;
    }

    @Override
    public String getFieldTypeDirPath() {
        return config.fieldTypeDirPath;
    }

    @Override
    public String getParamNameDirPath() {
        return config.paramNameDirPath;
    }

    @Override
    public String getParamTypeDirPath() {
        return config.paramTypeDirPath;
    }

    @Override
    public String getUnProcessedUcTitleDirPath() {
        return null;
    }

    @Override
    public String getUnProcessedUcPreconDirPath() {
        return null;
    }

    @Override
    public String getUnProcessedUcMainflowDirPath() {
        return null;
    }

    @Override
    public String getUnProcessedUcSubflowDirPath() {
        return null;
    }

    @Override
    public String getUnProcessedUcAlterflowDirPath() {
        return null;
    }

    @Override
    public String getUcTitleBitermDirPath() {
        return null;
    }

    @Override
    public String getUcPreconBitermDirPath() {
        return null;
    }

    @Override
    public String getUcMainflowBitermDirPath() {
        return null;
    }

    @Override
    public String getUcSubflowBitermDirPath() {
        return null;
    }

    @Override
    public String getUcAlterflowBitermDirPath() {
        return null;
    }

    @Override
    public String getUnProcessedIssueSummDirPath() {
        return null;
    }

    @Override
    public String getUnProcessedIssueDescripDirPath() {
        return config.unProcessedIssueDescripDirPath;
    }

    @Override
    public String getIssueSummBitermDirPath() {
        return null;
    }

    @Override
    public String getIssueDescripBitermDirPath() {
        return config.issueDescripBitermDirPath;
    }
}
