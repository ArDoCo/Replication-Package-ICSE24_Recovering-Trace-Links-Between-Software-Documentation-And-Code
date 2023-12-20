/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.core.cli;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.mcse.ardoco.core.tests.eval.CodeProject;
import edu.kit.kastel.mcse.ardoco.core.tests.eval.Project;

class ArDoCoCliTest {

    private static final CodeProject CODE_PROJECT = CodeProject.MEDIASTORE;
    private static final Project PROJECT = CODE_PROJECT.getProject();
    private static final String NAME;
    private static final String DOCUMENTATION;
    private static final String MODEL;
    private static final String OUT;
    private static final String CODE;

    static {
        NAME = PROJECT.name();
        DOCUMENTATION = PROJECT.getTextFile().getAbsolutePath();
        MODEL = PROJECT.getModelFile().getAbsolutePath();
        CODE = CODE_PROJECT.getCodeLocation();
        OUT = "src/test/resources/testout";
    }

    @Test
    @DisplayName("Help")
    void testHelp() {
        String[] args = { "-h" };
        ArDoCoCli.main(args);
    }

    @Test
    @Disabled
    @DisplayName("Evaluation")
    void testEval() {
        String[] args = { "-e", "-o", OUT };
        ArDoCoCli.main(args);
    }

    @Test
    @DisplayName("SAD-SAM")
    void testSadSam() {
        String[] args = { "-t", "SAD-SAM", "-n", NAME, "-d", DOCUMENTATION, "-m", MODEL, "-o", OUT };
        ArDoCoCli.main(args);
    }

    @Test
    @DisplayName("SAM-Code")
    void testSamCode() {
        String[] args = { "-t", "SAM-Code", "-n", NAME, "-m", MODEL, "-c", CODE, "-o", OUT };
        ArDoCoCli.main(args);
    }

    @Test
    @DisplayName("SAD-Code")
    void testSadCode() {
        String[] args = { "-t", "SAD-Code", "-n", NAME, "-d", DOCUMENTATION, "-m", MODEL, "-c", CODE, "-o", OUT };
        ArDoCoCli.main(args);
    }

    @Test
    @Disabled
    @DisplayName("All")
    void testAll() {
        String[] args = { "-t", "ALL", "-n", NAME, "-d", DOCUMENTATION, "-m", MODEL, "-c", CODE, "-o", OUT };
        ArDoCoCli.main(args);
    }
}
