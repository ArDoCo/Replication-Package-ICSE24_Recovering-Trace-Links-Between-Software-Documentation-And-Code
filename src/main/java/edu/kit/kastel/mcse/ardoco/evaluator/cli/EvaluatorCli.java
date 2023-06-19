/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.evaluator.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.kastel.mcse.ardoco.evaluator.EvaluationResults;
import edu.kit.kastel.mcse.ardoco.evaluator.Evaluator;

public class EvaluatorCli {
    private static final Logger logger = LoggerFactory.getLogger(EvaluatorCli.class);

    private static final String CMD_HELP = "h";
    private static final String CMD_TRACE_LINK_CSV = "t";
    private static final String CMD_GOLD_STANDARD_CSV = "g";
    private static final String CMD_CONFUSION_MATRIX_SUM = "c";
    private static final String CMD_WEAK_COMPARISON = "w";

    private static Options options;

    private EvaluationResults<String> lastResults = null;

    protected EvaluatorCli() {
        // empty
    }

    public static void main(String[] args) {
        new EvaluatorCli().startEvaluator(args);
    }

    private static void printUsage() {
        var formatter = new HelpFormatter();
        formatter.printHelp("java -jar evaluator.jar", options);
    }

    private static CommandLine parseCommandLine(String[] args) throws ParseException {
        options = new Options();
        Option opt;

        // Define Options ..
        opt = new Option(CMD_HELP, "help", false, "show this message");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_TRACE_LINK_CSV, "traceLink", true, "Path to the CSV for the trace links");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(CMD_GOLD_STANDARD_CSV, "goldStandard", true, "Path to the CSV for the gold standard");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(CMD_CONFUSION_MATRIX_SUM, "confusionMatrixSum", true,
                "Integer for the size of the solution space (= number of artifacts on one side times the number of artifacts on the other side)");
        opt.setRequired(false);
        opt.setType(Number.class);
        options.addOption(opt);

        opt = new Option(CMD_WEAK_COMPARISON, "weakComparison", false, "perform a weak comparison");
        opt.setRequired(false);
        options.addOption(opt);

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    private static Path ensurePath(String path) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IOException("The specified file does not exist and/or could not be created: " + path);
        }
        var file = new File(path);
        if (file.exists()) {
            return Path.of(file.toURI());
        }
        // File not available
        throw new IOException("The specified file does not exist and/or could not be created: " + path);
    }

    protected void startEvaluator(String[] args) {
        CommandLine cmd;
        try {
            cmd = parseCommandLine(args);
        } catch (IllegalArgumentException | ParseException e) {
            logger.error(e.getMessage());
            printUsage();
            return;
        }

        if (cmd.hasOption(CMD_HELP)) {
            printUsage();
            return;
        }

        Path traceLinkCsv;
        Path goldStandardCsv;
        int confusionMatrixSum = -1;
        try {
            traceLinkCsv = ensurePath(cmd.getOptionValue(CMD_TRACE_LINK_CSV));
            goldStandardCsv = ensurePath(cmd.getOptionValue(CMD_GOLD_STANDARD_CSV));
            if (cmd.hasOption(CMD_CONFUSION_MATRIX_SUM)) {
                confusionMatrixSum = ((Number) cmd.getParsedOptionValue(CMD_CONFUSION_MATRIX_SUM)).intValue();
            }
        } catch (IOException | ParseException e) {
            logger.warn("Could not properly handle input. Aborting.", e);
            return;
        }

        boolean weakComparison = cmd.hasOption(CMD_WEAK_COMPARISON);

        EvaluationResults<String> results;
        if (confusionMatrixSum > 0) {
            if (weakComparison) {
                results = Evaluator.evaluateWeak(traceLinkCsv, goldStandardCsv, confusionMatrixSum);
            } else {
                results = Evaluator.evaluate(traceLinkCsv, goldStandardCsv, confusionMatrixSum);
            }
        } else {
            if (weakComparison) {
                results = Evaluator.evaluateSimpleWeak(traceLinkCsv, goldStandardCsv);
            } else {
                results = Evaluator.evaluateSimple(traceLinkCsv, goldStandardCsv);
            }
        }
        lastResults = results;
        logger.info("{}", results);
    }

    public EvaluationResults<String> getLastResults() {
        return lastResults;
    }
}
