package experiment.preprocess.ocean.parsecode.java.util;




import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class JdtAstUtil {
    /**
     * get compilation unit of source code
     * @param javaFilePath
     * @return CompilationUnit
     */
    public static CompilationUnit getCompilationUnit(String javaFilePath){
        byte[] input = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
            input = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(input);
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ASTParser astParser = ASTParser.newParser(AST.JLS14);
        astParser.setSource(new String(input).toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);

        Map<String, String> compilerOptions = JavaCore.getOptions();
        compilerOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_14); //设置Java语言版本
        compilerOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_14);
        compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_14);
        astParser.setCompilerOptions(compilerOptions); //设置编译选项

        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));

        return result;
    }
}

