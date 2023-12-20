/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.core.cli;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.kastel.mcse.ardoco.core.api.models.ArchitectureModelType;
import edu.kit.kastel.mcse.ardoco.core.execution.ArDoCoForSadSamCodeTraceabilityLinkRecovery;
import edu.kit.kastel.mcse.ardoco.core.execution.ArDoCoForSadSamTraceabilityLinkRecovery;
import edu.kit.kastel.mcse.ardoco.core.execution.ArDoCoForSamCodeTraceabilityLinkRecovery;
import edu.kit.kastel.mcse.ardoco.core.tests.eval.CodeProject;

public class ArDoCoCli {
    private static final Logger logger = LoggerFactory.getLogger(ArDoCoCli.class);
    private static final String CMD_EVAL = "e";
    private static final String CMD_HELP = "h";
    private static final String CMD_SAD = "d";
    private static final String CMD_CODE = "c";
    private static final String CMD_MODEL = "m";
    private static final String CMD_OUT = "o";
    private static final String CMD_TASK = "t";
    private static final String CMD_NAME = "n";
    private static final String ERROR_FILE_NOT_EXISTING = "The specified file does not exist and/or could not be created: ";
    private static final String ERROR_READING_FILES = "Error in reading files and/or directories!";
    private static final String WARNING_NO_CODE_MODEL = "Could not get code model to enroll gold standard. Using not enrolled gold standard!";
    private static final String GOLD_STANDARD_PATH_SEPARATOR = "/";
    private static Options options;

    private ArDoCoCli() {
        throw new IllegalAccessError();
    }

    public static void main(String[] args) {
        parseCliAndRunArDoCo(args);
    }

    private static void parseCliAndRunArDoCo(String[] args) {
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
        if (!cmd.hasOption(CMD_OUT)) {
            logger.error("No output directory specified.");
            return;
        }
        if (cmd.hasOption(CMD_EVAL)) {
            doEval(cmd);
            return;
        }

        if (cmd.hasOption(CMD_TASK)) {
            String task = cmd.getOptionValue(CMD_TASK);
            switch (task.toLowerCase()) {
            case "sad-sam":
                doSadSam(cmd);
                break;
            case "sam-code":
                doSamCode(cmd);
                break;
            case "sad-code":
                doSadCode(cmd);
                break;
            case "all":
                doSadSam(cmd);
                doSamCode(cmd);
                doSadCode(cmd);
                break;
            default:
                logger.error("Invalid task provided.");
            }
        } else {
            logger.error("No task specified. Either use the parameter to perform evaluation or specify the task.");
            printUsage();
        }

        cleanup(cmd);
    }

    private static void cleanup(CommandLine cmd) {
        File out = ensureDir(cmd.getOptionValue(CMD_OUT));

        deleteFiles(out, "inconsistencyDetection_.*\\.txt");
        deleteFiles(out, "traceLinks_.*\\.txt");
    }

    private static void deleteFiles(File out, final String filesRegex) {
        final File[] files = out.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.matches(filesRegex);
            }
        });
        if (files == null) {
            return;
        }

        for (final File file : files) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                logger.warn("Some error occurred during cleanup at the end.", e);
            }

        }
    }

    private static void doEval(CommandLine cmd) {
        logger.info("Starting evaluation-mode: Processing the benchmark projects to create the csv with TLR-results.");

        for (var project : CodeProject.values()) {
            String name = project.getProject().name();
            File out = null;
            File sad = null;
            File sam = null;
            File code = null;
            try {
                out = ensureDir(cmd.getOptionValue(CMD_OUT));
                sad = project.getProject().getTextFile();
                sam = project.getProject().getModelFile();
                code = ensureFile(project.getCodeModelDirectory());
            } catch (IOException e) {
                logger.error(ERROR_READING_FILES, e);
            }

            var runner = new ArDoCoForSadSamCodeTraceabilityLinkRecovery(name);
            runner.setUp(sad, sam, ArchitectureModelType.PCM, code, new HashMap<>(), out);
            runner.run();
        }
    }

    private static void doSadSam(CommandLine cmd) {
        logger.info("Starting SAD-SAM.");

        if (!cmd.hasOption(CMD_NAME) || !cmd.hasOption(CMD_SAD) || !cmd.hasOption(CMD_MODEL)) {
            logger.error("There is a parameter missing. Needing name, documentation, and model!");
        }

        String name = cmd.getOptionValue(CMD_NAME);
        File out = null;
        File sad = null;
        File sam = null;
        try {
            out = ensureDir(cmd.getOptionValue(CMD_OUT));
            sad = ensureFile(cmd.getOptionValue(CMD_SAD));
            sam = ensureFile(cmd.getOptionValue(CMD_MODEL));
        } catch (IOException e) {
            logger.error(ERROR_READING_FILES, e);
        }

        var runner = new ArDoCoForSadSamTraceabilityLinkRecovery(name);
        runner.setUp(sad, sam, ArchitectureModelType.PCM, new HashMap<>(), out);
        runner.run();
    }

    private static void doSamCode(CommandLine cmd) {
        logger.info("Starting SAM-CODE.");

        if (!cmd.hasOption(CMD_NAME) || !cmd.hasOption(CMD_MODEL) || !cmd.hasOption(CMD_CODE)) {
            logger.error("There is a parameter missing. Needing name, model, and code!");
        }

        String name = cmd.getOptionValue(CMD_NAME);
        File out = null;
        File sam = null;
        File code = null;
        try {
            out = ensureDir(cmd.getOptionValue(CMD_OUT));
            sam = ensureFile(cmd.getOptionValue(CMD_MODEL));
            code = getCodeDirectory(cmd);

        } catch (IOException e) {
            logger.error(ERROR_READING_FILES, e);
        }
        var runner = new ArDoCoForSamCodeTraceabilityLinkRecovery(name);
        runner.setUp(sam, ArchitectureModelType.PCM, code, new HashMap<>(), out);
        runner.run();
    }

    private static void doSadCode(CommandLine cmd) {
        logger.info("Starting SAD-Code.");

        if (!cmd.hasOption(CMD_NAME) || !cmd.hasOption(CMD_SAD) || !cmd.hasOption(CMD_MODEL) || !cmd.hasOption(CMD_CODE)) {
            logger.error("There is a parameter missing. Needing name, documentation, model, and code!");
        }

        String name = cmd.getOptionValue(CMD_NAME);
        File out = null;
        File sad = null;
        File sam = null;
        File code = null;
        try {
            out = ensureDir(cmd.getOptionValue(CMD_OUT));
            sad = ensureFile(cmd.getOptionValue(CMD_SAD));
            sam = ensureFile(cmd.getOptionValue(CMD_MODEL));
            code = getCodeDirectory(cmd);

        } catch (IOException e) {
            logger.error(ERROR_READING_FILES, e);
        }

        var runner = new ArDoCoForSadSamCodeTraceabilityLinkRecovery(name);
        runner.setUp(sad, sam, ArchitectureModelType.PCM, code, new HashMap<>(), out);
        runner.run();
    }

    private static File getCodeDirectory(CommandLine cmd) throws IOException {
        File code;
        try {
            code = ensureFile(cmd.getOptionValue(CMD_CODE));
        } catch (IOException e) {
            String path = cmd.getOptionValue(CMD_CODE);
            var file = new File(path);
            if (file.isDirectory() && file.exists()) {
                return file;
            } else {
                throw new IOException(ERROR_FILE_NOT_EXISTING + path);
            }
        }
        return code;
    }

    private static void printUsage() {
        var formatter = new HelpFormatter();
        formatter.printHelp("java -jar ardoco-cli.jar", options);
    }

    /**
     * Ensure that a file exists.
     *
     * @param path the path to the file
     * @return the file
     * @throws IOException if something went wrong
     */
    private static File ensureFile(String path) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IOException(ERROR_FILE_NOT_EXISTING + path);
        }
        var file = new File(path);
        if (file.exists()) {
            return file;
        }
        // File not available
        throw new IOException(ERROR_FILE_NOT_EXISTING + path);
    }

    /**
     * Ensure that a directory exists (or create ).
     *
     * @param path the path to the file
     * @return the file
     */
    private static File ensureDir(String path) {
        var file = new File(path);
        if (file.isDirectory() && file.exists()) {
            return file;
        }
        file.mkdirs();
        return file;
    }

    private static CommandLine parseCommandLine(String[] args) throws ParseException {
        options = new Options();
        Option opt;

        // Define Options ..
        opt = new Option(CMD_HELP, "help", false, "Show this message");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_EVAL, "eval", false, "If set, perform full evaluation, ignoring other parameters.");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_SAD, "documentation", true, "Path to the documentation (SAD)");
        opt.setType(String.class);
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_CODE, "code", true, "Path to the code");
        opt.setType(String.class);
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_MODEL, "model", true, "Path to the model (SAM)");
        opt.setType(String.class);
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_OUT, "output", true, "Path to the output directory");
        opt.setType(String.class);
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_NAME, "name", true, "Name of the project that should be analyzed");
        opt.setType(String.class);
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option(CMD_TASK, "task", true, "Specify the TLR-task to perform. Valid options are: SAD-SAM, SAD-CODE, SAM-CODE, ALL");
        opt.setType(String.class);
        opt.setRequired(false);
        options.addOption(opt);

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);

    }
}
