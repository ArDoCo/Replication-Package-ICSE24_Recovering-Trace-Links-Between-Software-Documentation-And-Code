/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.evaluator;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EvaluationTest {
    private static final Logger logger = LoggerFactory.getLogger(EvaluationTest.class);
    private static final Map<String, Integer> nameToConfusionMatrixSum = Map.of( //
            "bigbluebutton", 47600,//
            "jabref", 26000, //
            "mediastore", 3589, //
            "teammates", 165330, //
            "teastore", 8815);

    private static Optional<File> getMatchingGoldStandard(File[] goldStandardsFiles, String name) {
        return Arrays.stream(goldStandardsFiles).filter(file -> file.getName().replaceAll("\\.csv", "").equals(name)).findFirst();
    }

    @Test
    @DisplayName("Evaluate")
    void evaluateTest() {
        Path goldStandardCsvDirectory = Paths.get("src/test/resources/testCSVs/gs-sad-code");
        Path traceLinksCsvDirectory = Paths.get("src/test/resources/testCSVs/tls-sad-code");
        File goldStandardCsvDirectoryFile = goldStandardCsvDirectory.toFile();
        File traceLinksCsvDirectoryFile = traceLinksCsvDirectory.toFile();
        Assertions.assertTrue(goldStandardCsvDirectoryFile.exists() && traceLinksCsvDirectoryFile.exists());

        var goldStandardsFiles = goldStandardCsvDirectoryFile.listFiles();
        var traceLinksFiles = traceLinksCsvDirectoryFile.listFiles();

        for (var traceLinks : traceLinksFiles) {
            var name = traceLinks.getName().replaceAll("\\.csv", "");

            var goldStandardOptional = getMatchingGoldStandard(goldStandardsFiles, name);
            if (goldStandardOptional.isEmpty()) {
                continue;
            }
            int confusionMatrixSum = nameToConfusionMatrixSum.get(name);

            EvaluationResults<String> evaluationResults = Evaluator.evaluate(traceLinks.toPath(), goldStandardOptional.get().toPath(), confusionMatrixSum);
            Assertions.assertNotNull(evaluationResults);
            logger.info(name + evaluationResults.getResultsString());
        }
    }

    @Test
    @DisplayName("Evaluate Simple")
    void evaluateSimpleTest() {
        Path goldStandardCsvDirectory = Paths.get("src/test/resources/testCSVs/gs-sad-code");
        Path traceLinksCsvDirectory = Paths.get("src/test/resources/testCSVs/tls-sad-code");
        File goldStandardCsvDirectoryFile = goldStandardCsvDirectory.toFile();
        File traceLinksCsvDirectoryFile = traceLinksCsvDirectory.toFile();
        Assertions.assertTrue(goldStandardCsvDirectoryFile.exists() && traceLinksCsvDirectoryFile.exists());

        var goldStandardsFiles = goldStandardCsvDirectoryFile.listFiles();
        var traceLinksFiles = traceLinksCsvDirectoryFile.listFiles();

        for (var traceLinks : traceLinksFiles) {
            var name = traceLinks.getName().replaceAll("\\.csv", "");

            var goldStandardOptional = getMatchingGoldStandard(goldStandardsFiles, name);
            if (goldStandardOptional.isEmpty()) {
                continue;
            }

            EvaluationResults<String> evaluationResults = Evaluator.evaluateSimple(traceLinks.toPath(), goldStandardOptional.get().toPath());
            Assertions.assertNotNull(evaluationResults);
            logger.info(name + evaluationResults.getResultsString());
        }
    }

    @Test
    @DisplayName("Evaluate Simple Weak")
    void evaluateSimpleWeakTest() {
        Path goldStandardCsvDirectory = Paths.get("src/test/resources/testCSVs/gs-sad-code");
        Path traceLinksCsvDirectory = Paths.get("src/test/resources/testCSVs/tls-sad-code-ftlr");
        File goldStandardCsvDirectoryFile = goldStandardCsvDirectory.toFile();
        File traceLinksCsvDirectoryFile = traceLinksCsvDirectory.toFile();
        Assertions.assertTrue(goldStandardCsvDirectoryFile.exists() && traceLinksCsvDirectoryFile.exists());

        var goldStandardsFiles = goldStandardCsvDirectoryFile.listFiles();
        var traceLinksFiles = traceLinksCsvDirectoryFile.listFiles();

        for (var traceLinks : traceLinksFiles) {
            var name = traceLinks.getName().split("_")[0].toLowerCase();

            var goldStandardOptional = getMatchingGoldStandard(goldStandardsFiles, name);
            if (goldStandardOptional.isEmpty()) {
                continue;
            }

            EvaluationResults<String> evaluationResults = Evaluator.evaluateSimpleWeak(traceLinks.toPath(), goldStandardOptional.get().toPath());
            Assertions.assertNotNull(evaluationResults);
            logger.info(name + evaluationResults.getResultsString());
        }
    }

}
