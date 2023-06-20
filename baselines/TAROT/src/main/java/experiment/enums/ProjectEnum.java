package experiment.enums;

public enum ProjectEnum {

    MEDIASTORE("experiment.project.MediaStore"),
    TEASTORE("experiment.project.TeaStore"),
    BIGBLUEBUTTON("experiment.project.BigBlueButton"),
    TEAMMATES("experiment.project.TeamMates"),
    JABREF("experiment.project.JabRef"),

    ;

    String name;

    ProjectEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ProjectEnum getProject(String projectName) {
        switch (projectName) {
            case "MediaStore":
                return MEDIASTORE;
            case "TeaStore":
                return TEASTORE;
            case "bigbluebutton":
                return BIGBLUEBUTTON;
            case "teammates":
                return TEAMMATES;
            case "jabref":
                return JABREF;
            default:
                return null;
        }
    }
}
