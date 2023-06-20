/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.evaluator;

import java.util.Locale;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public record EvaluationResults<T>(double precision, double recall, double f1, ImmutableList<T> truePositives, int trueNegatives,
                                   ImmutableList<T> falseNegatives, ImmutableList<T> falsePositives, double accuracy, double phiCoefficient, double specificity,
                                   double phiCoefficientMax, double phiOverPhiMax) {

    /**
     * creates new {@link EvaluationResults} from a {@link ResultMatrix}
     *
     * @param matrix the {@link ResultMatrix}
     * @return new {@link EvaluationResults}
     */
    public static <T> EvaluationResults<T> createEvaluationResults(ResultMatrix<T> matrix) {
        int nrTruePos = matrix.truePositives().size();
        int nrTrueNeg = matrix.trueNegatives();
        int nrFalsePos = matrix.falsePositives().size();
        int nrFalseNeg = matrix.falseNegatives().size();

        double precision = EvaluationMetrics.calculatePrecision(nrTruePos, nrFalsePos);
        double recall = EvaluationMetrics.calculateRecall(nrTruePos, nrFalseNeg);
        double f1 = EvaluationMetrics.calculateF1(precision, recall);

        double accuracy = 0;
        double phiCoefficient = 0;
        double specificity = 0;
        double phiCoefficientMax = 0;
        double phiOverPhiMax = 0;

        // There are no true negatives set, so we cannot calculate the other metrics
        if (nrTrueNeg <= 0) {
            return new EvaluationResults<>(precision, recall, f1, matrix.truePositives(), matrix.trueNegatives(), matrix.falseNegatives(),
                    matrix.falsePositives(), accuracy, phiCoefficient, specificity, phiCoefficientMax, phiOverPhiMax);
        }

        if (nrTruePos + nrFalsePos + nrFalseNeg + nrTrueNeg != 0) {
            accuracy = EvaluationMetrics.calculateAccuracy(nrTruePos, nrFalsePos, nrFalseNeg, nrTrueNeg);
        }
        phiCoefficient = EvaluationMetrics.calculatePhiCoefficient(nrTruePos, nrFalsePos, nrFalseNeg, nrTrueNeg);
        if (nrTrueNeg + nrFalsePos != 0) {
            specificity = EvaluationMetrics.calculateSpecificity(nrTrueNeg, nrFalsePos);
        }
        if ((nrFalseNeg + nrTrueNeg) * (nrTruePos + nrFalseNeg) != 0) {
            phiCoefficientMax = EvaluationMetrics.calculatePhiCoefficientMax(nrTruePos, nrFalsePos, nrFalseNeg, nrTrueNeg);
        }
        if (phiCoefficientMax != 0) {
            phiOverPhiMax = EvaluationMetrics.calculatePhiOverPhiMax(nrTruePos, nrFalsePos, nrFalseNeg, nrTrueNeg);
        }

        return new EvaluationResults<>(precision, recall, f1, matrix.truePositives(), matrix.trueNegatives(), matrix.falseNegatives(), matrix.falsePositives(),
                accuracy, phiCoefficient, specificity, phiCoefficientMax, phiOverPhiMax);
    }

    @Override
    public String toString() {
        return getResultsString();
    }

    public String getResultsString() {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(String.format(Locale.ENGLISH, "%nPrecision:%8.2f%nRecall:%11.2f%nF1:%15.2f", precision, recall, f1));
        if (trueNegatives > 0) {
            outputBuilder.append(String.format(Locale.ENGLISH, "%nAccuracy:%9.2f%nSpecificity:%6.2f", accuracy, specificity));
            outputBuilder.append(String.format(Locale.ENGLISH, "%nPhi Coef.:%8.2f", phiCoefficient));
        }

        return outputBuilder.toString();
    }

    public String getMinimizedResultsString() {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(String.format(Locale.ENGLISH, "%.2f;%.2f;%.2f", precision, recall, f1));
        if (trueNegatives > 0) {
            outputBuilder.append(String.format(Locale.ENGLISH, ";%.2f;%.2f;%.2f", accuracy, specificity, phiCoefficient));
        }

        return outputBuilder.toString();
    }

    /**
     * returns the weight (truePos + falseNeg)
     *
     * @return the weight
     */
    public int getWeight() {
        return this.truePositives().size() + this.falseNegatives().size();
    }

    public ImmutableList<T> getFound() {
        MutableList<T> found = Lists.mutable.empty();
        found.addAll(truePositives.castToCollection());
        found.addAll(falsePositives.castToCollection());
        return found.toImmutable();
    }

}
