/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.evaluator.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EvaluatorCliTest {

    private EvaluatorCli cli;

    @BeforeEach
    void setUp() {
        cli = new EvaluatorCli();
    }

    @Test
    @DisplayName("With no arguments")
    void cliNoArgumentsTest() {
        String[] args = new String[0];

        cli.startEvaluator(args);
        Assertions.assertNull(cli.getLastResults());
    }

    @Test
    @DisplayName("With no ConfusionMatrixSum")
    void cliNoConfusionMatrixSumTest() {
        String[] args = new String[] {//
                "-g", "src/test/resources/testCSVs/gs-sad-code/teastore.csv", //
                "-t", "src/test/resources/testCSVs/tls-sad-code/teastore.csv"//
        };
        cli.startEvaluator(args);
        Assertions.assertNotNull(cli.getLastResults());
    }

    @Test
    @DisplayName("With ConfusionMatrixSum")
    void cliWithConfusionMatrixSumTest() {
        String[] args = new String[] {//
                "-g", "src/test/resources/testCSVs/gs-sad-code/teastore.csv", //
                "-t", "src/test/resources/testCSVs/tls-sad-code/teastore.csv",//
                "-c", "165330" //
        };
        cli.startEvaluator(args);
        Assertions.assertNotNull(cli.getLastResults());
    }

    @Test
    @DisplayName("Minimized Output With ConfusionMatrixSum")
    void cliMinimizedWithConfusionMatrixSumTest() {
        String[] args = new String[] {//
                "-g", "src/test/resources/testCSVs/gs-sad-code/teastore.csv", //
                "-t", "src/test/resources/testCSVs/tls-sad-code/teastore.csv",//
                "-c", "165330", //
                "-m" //
        };
        cli.startEvaluator(args);
        Assertions.assertNotNull(cli.getLastResults());
    }

    @Test
    @DisplayName("Minimized Output")
    void cliMinimizedTest() {
        String[] args = new String[] {//
                "-g", "src/test/resources/testCSVs/gs-sad-code/teastore.csv", //
                "-t", "src/test/resources/testCSVs/tls-sad-code/teastore.csv",//
                "-m" //
        };
        cli.startEvaluator(args);
        Assertions.assertNotNull(cli.getLastResults());
    }

    @Test
    @DisplayName("With weak comparison")
    void cliWeakComparisonTest() {
        String[] args = new String[] {//
                "-g", "src/test/resources/testCSVs/gs-sad-code/teastore.csv", //
                "-t", "src/test/resources/testCSVs/tls-sad-code-ftlr/teastore_FileLevelWMDMc_tracelinks.csv",//
                "-w"//
        };
        cli.startEvaluator(args);
        Assertions.assertNotNull(cli.getLastResults());
    }

    @Test
    @DisplayName("With weak comparison and ConfusionMatrixSum")
    void cliWeakComparisonAndConfusionMatrixSumTest() {
        String[] args = new String[] {//
                "-g", "src/test/resources/testCSVs/gs-sad-code/teastore.csv", //
                "-t", "src/test/resources/testCSVs/tls-sad-code-ftlr/teastore_FileLevelWMDMc_tracelinks.csv",//
                "-c", "8815", //
                "-w"//
        };
        cli.startEvaluator(args);
        Assertions.assertNotNull(cli.getLastResults());
    }

}
