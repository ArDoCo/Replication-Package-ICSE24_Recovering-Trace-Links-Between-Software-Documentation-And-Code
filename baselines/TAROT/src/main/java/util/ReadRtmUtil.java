package util;

import document.SimilarityMatrix;
import document.SingleLink;

import java.io.File;

public class ReadRtmUtil {
    public static SimilarityMatrix createSimilarityMatrix(String path) {
        SimilarityMatrix sims = new SimilarityMatrix();
        if (!path.endsWith(".txt"))
            throw new IllegalArgumentException("not a txt file");

        String contents = FileIOUtil.readFile(path);
        String[] lines = contents.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String[] tokens = lines[i].split(" ");
            String sentence = tokens[0].trim().replace(".txt:", "");
            String code_artifact = tokens[1].trim().replace("/", "_sep_").replace(".", "_dot_");
            SingleLink link = new SingleLink(sentence, code_artifact, 1.0);
            sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
        }
        return sims;
    }
}
