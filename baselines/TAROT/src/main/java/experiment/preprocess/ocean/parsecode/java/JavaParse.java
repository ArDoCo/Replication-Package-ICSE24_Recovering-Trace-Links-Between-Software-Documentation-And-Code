package experiment.preprocess.ocean.parsecode.java;

import experiment.preprocess.ocean.parsecode.java.entity.ClassEntity;
import experiment.preprocess.ocean.parsecode.java.util.ClassASTVisitor;
import experiment.preprocess.ocean.parsecode.java.util.ClassUtil;
import experiment.preprocess.ocean.parsecode.java.util.JavaToXmlUtil;
import experiment.preprocess.ocean.parsecode.java.util.JdtAstUtil;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JavaParse {


    // convert the Java source code file to AST, output the xml file
    public static void parseSourceCode(String project) {
        ClassUtil classUtil = new ClassUtil(project);
        int classId = 0;
        ArrayList<File> fileList = ClassUtil.getFileList(ClassUtil.srcPath,
                ClassUtil.codeFileSuffix);
        for (File file : fileList) {
            System.out.println(file.getName());
            CompilationUnit comp = JdtAstUtil.getCompilationUnit(file.getPath());
            ClassASTVisitor visitor = new ClassASTVisitor();
            comp.accept(visitor);
            visitor.getClassEntity().setId(classId);
            visitor.getClassEntity().setField(visitor.getFieldList());
            visitor.getClassEntity().setMethod(visitor.getMethodList());
            ClassEntity classEntity = visitor.getClassEntity();
            String xml = JavaToXmlUtil.beanToXml(classEntity);
            classId++;

            String fileName = file.getName();
            String fileMainName = fileName.substring(0, fileName.lastIndexOf("."));
            File outputFile = new File(ClassUtil.classXmlDirectory, fileMainName + ".xml");
            try {
                FileWriter fileWriter = new FileWriter(outputFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(xml);
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
