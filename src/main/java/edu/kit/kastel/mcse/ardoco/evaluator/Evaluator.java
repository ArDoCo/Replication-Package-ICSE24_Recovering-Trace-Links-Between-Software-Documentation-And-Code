/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.evaluator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Evaluator {
    public static final List<String> POSSIBLE_SEPARATORS = List.of(",", ";", "-");
    private static final Logger logger = LoggerFactory.getLogger(Evaluator.class);

    private Evaluator() {
        throw new IllegalStateException();
    }

    public static EvaluationResults<String> evaluateSimple(Path traceLinkPath, Path goldStandardPath) {
        return evaluate(traceLinkPath, goldStandardPath, -1);
    }

    public static EvaluationResults<String> evaluateSimpleWeak(Path traceLinkPath, Path goldStandardPath) {
        return evaluateWeak(traceLinkPath, goldStandardPath, -1);
    }

    public static EvaluationResults<String> evaluate(Path traceLinkPath, Path goldStandardPath, int confusionMatrixSum) {
        var traceLinks = getLinesFromCsvFile(traceLinkPath);
        var goldStandard = getLinesFromCsvFile(goldStandardPath);

        return evaluate(traceLinks, goldStandard, confusionMatrixSum);
    }

    public static EvaluationResults<String> evaluateWeak(Path traceLinkPath, Path goldStandardPath, int confusionMatrixSum) {
        var traceLinks = getLinesFromCsvFile(traceLinkPath);
        var goldStandard = getLinesFromCsvFile(goldStandardPath);

        return evaluateWeak(traceLinks, goldStandard, confusionMatrixSum);
    }

    public static EvaluationResults<String> evaluate(ImmutableList<String> traceLinks, ImmutableList<String> goldStandard, int confusionMatrixSum) {
        return calculateEvaluationResults(traceLinks, goldStandard, confusionMatrixSum, false);
    }

    public static EvaluationResults<String> evaluateWeak(ImmutableList<String> traceLinks, ImmutableList<String> goldStandard, int confusionMatrixSum) {
        return calculateEvaluationResults(traceLinks, goldStandard, confusionMatrixSum, true);
    }

    private static EvaluationResults<String> calculateEvaluationResults(ImmutableList<String> traceLinks, ImmutableCollection<String> goldStandard,
            int confusionMatrixSum, boolean weakComparison) {
        Set<String> distinctTraceLinks = new HashSet<>(traceLinks.castToCollection());
        Set<String> distinctGoldStandard = new HashSet<>(goldStandard.castToCollection());

        // True Positives are the trace links that are contained on both lists
        Set<String> truePositives = distinctTraceLinks.stream()
                .filter(tl -> isTraceLinkContainedInGoldStandard(tl, distinctGoldStandard, weakComparison))
                .collect(Collectors.toSet());
        ImmutableList<String> truePositivesList = Lists.immutable.ofAll(truePositives);

        // False Positives are the trace links that are only contained in the result set
        Set<String> falsePositives = distinctTraceLinks.stream()
                .filter(tl -> !isTraceLinkContainedInGoldStandard(tl, distinctGoldStandard, weakComparison))
                .collect(Collectors.toSet());
        ImmutableList<String> falsePositivesList = Lists.immutable.ofAll(falsePositives);

        // False Negatives are the trace links that are only contained in the gold standard
        Set<String> falseNegatives = distinctGoldStandard.stream()
                .filter(gstl -> !isGoldStandardTraceLinkContainedInTraceLinks(gstl, distinctTraceLinks, weakComparison))
                .collect(Collectors.toSet());
        ImmutableList<String> falseNegativesList = Lists.immutable.ofAll(falseNegatives);

        int trueNegatives = confusionMatrixSum - truePositives.size() - falsePositives.size() - falseNegatives.size();
        return EvaluationResults.createEvaluationResults(new ResultMatrix<>(truePositivesList, trueNegatives, falsePositivesList, falseNegativesList));
    }

    private static boolean areTraceLinksMatching(String goldStandardTraceLink, String traceLink, boolean weakComparison) {
        traceLink = traceLink.strip();
        goldStandardTraceLink = goldStandardTraceLink.strip();
        if (weakComparison) {
            var splitTraceLink = splitTraceLinkAtSeparator(traceLink);
            var splitGoldStandard = splitTraceLinkAtSeparator(goldStandardTraceLink);
            int splitTraceLinkLength = splitTraceLink.length;
            int splitGoldStandardLength = splitGoldStandard.length;
            if (splitTraceLinkLength <= 1 || splitGoldStandardLength <= 1) {
                return goldStandardTraceLink.equals(traceLink);
            }
            return splitTraceLink[0].equals(splitGoldStandard[0]) && //
                    splitGoldStandard[splitGoldStandardLength - 1].endsWith(splitTraceLink[splitTraceLinkLength - 1]);
        }
        return (goldStandardTraceLink.equals(traceLink));
    }

    private static String[] splitTraceLinkAtSeparator(String traceLink) {
        for (String separator : POSSIBLE_SEPARATORS) {
            var splitTraceLink = traceLink.split(separator);
            if (splitTraceLink.length > 1) {
                return splitTraceLink;
            }
        }
        return new String[] { traceLink };
    }

    private static boolean isTraceLinkContainedInGoldStandard(String traceLink, Set<String> goldStandard, boolean weakComparison) {
        return goldStandard.stream().anyMatch(goldStandardTraceLink -> areTraceLinksMatching(goldStandardTraceLink, traceLink, weakComparison));
    }

    private static boolean isGoldStandardTraceLinkContainedInTraceLinks(String goldStandardTraceLink, Set<String> traceLinks, boolean weakComparison) {
        return traceLinks.stream().anyMatch(traceLink -> areTraceLinksMatching(goldStandardTraceLink, traceLink, weakComparison));
    }

    private static ImmutableList<String> getLinesFromCsvFile(Path path) {
        List<String> lines = Lists.mutable.empty();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        lines.remove(0); //remove header
        return Lists.immutable.fromStream(lines.stream().filter(Predicate.not(String::isBlank)));
    }

}
