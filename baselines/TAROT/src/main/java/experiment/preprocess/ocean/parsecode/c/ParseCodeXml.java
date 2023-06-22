package experiment.preprocess.ocean.parsecode.c;

import experiment.project.Project;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ParseCodeXml {

    private static String srcmlXmlDir;
    private static Map<String, List<String>> codeFileNameMap = new HashMap<>();
    private static Map<String, List<String>> codeMethodMap = new HashMap<>();
    private static Map<String, List<String>> codeParamTypeMap = new HashMap<>();
    private static Map<String, List<String>> codeParamNameMap = new HashMap<>();
    private static Map<String, List<String>> codeFieldMap = new HashMap<>();
    private static Map<String, List<String>> codeCommentMap = new HashMap<>();
    private static Map<String, List<String>> codeInvokeMethodMap = new HashMap<>();
    private static Set<String> allMethodSet = new HashSet<>();

    private static void filterInvokeMethod() {
        for (String codeName : codeInvokeMethodMap.keySet()) {
            List<String> invokeMethodList = codeInvokeMethodMap.get(codeName);
            if (invokeMethodList == null) continue;
            List<String> newList = new ArrayList<>();
            for (String invokeMethod : invokeMethodList) {
                if (allMethodSet.contains(invokeMethod))
                    newList.add(invokeMethod);
            }
            codeInvokeMethodMap.put(codeName, newList);
        }
    }

    public void run() {
        try {
            srcmlXmlProcess();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }


    public Map<String, List<String>> getCodeFileNameMap() {
        return codeFileNameMap;
    }

    public Map<String, List<String>> getCodeMethodMap() {
        return codeMethodMap;
    }

    public Map<String, List<String>> getCodeParamTypeMap() {
        return codeParamTypeMap;
    }

    public Map<String, List<String>> getCodeParamNameMap() {
        return codeParamNameMap;
    }

    public Map<String, List<String>> getCodeFieldMap() {
        return codeFieldMap;
    }

    public Map<String, List<String>> getCodeCommentMap() {
        return codeCommentMap;
    }

    public Map<String, List<String>> getCodeInvokeMethodMap() {
        return codeInvokeMethodMap;
    }

    public Set<String> getAllMethodSet() {
        return allMethodSet;
    }

    public ParseCodeXml(Project project, String codeType) {
        this.srcmlXmlDir = project.getProjectPath() + "/unprocessed/"+codeType+"/code_xml";
    }


    private static void srcmlXmlProcess() throws IOException, JDOMException {
        File filePath = new File(srcmlXmlDir);
        if (filePath.isDirectory()) {
            for (File f : filePath.listFiles()) {
                String fileName = f.getName();
                fileName = fileName.replace(".xml", "");
                System.out.println(fileName);
                codeFileNameMap.put(fileName, new ArrayList<>());
                codeFileNameMap.get(fileName).add(fileName);

                codeMethodMap.put(fileName, new ArrayList<>());
                codeInvokeMethodMap.put(fileName, new ArrayList<>());
                codeParamNameMap.put(fileName, new ArrayList<>());
                codeParamTypeMap.put(fileName, new ArrayList<>());
                codeCommentMap.put(fileName, new ArrayList<>());
                codeFieldMap.put(fileName, new ArrayList<>());

                SAXBuilder saxBuilder = new SAXBuilder();
                InputStream is = new FileInputStream(f);
                Document document = saxBuilder.build(is);
                Element rootElement = document.getRootElement();
                Queue<Element> children = new LinkedList<>();
                children.add(rootElement);
                while (!children.isEmpty()) {
                    Element child = children.poll();
                    Namespace ns = child.getNamespace();
                    // block comment
                    if (child.getName().equals("define")) {
                        Element macro = child.getChild("macro", ns);
                        String fieldName = macro.getValue();
                        codeFieldMap.get(fileName).add(fieldName);
//                        System.out.println("field: " + fieldName);
                    } else if (child.getName().equals("comment")) {
                        String comment = child.getValue();
                        if (!child.getAttributeValue("type").equals("block") || comment.length() > 1000)
                            continue;
                        codeCommentMap.get(fileName).add(comment);

                    } else if (child.getName().equals("function")) {
                        String methodName = child.getChildText("name", ns);
                        codeMethodMap.get(fileName).add(methodName);
                        allMethodSet.add(methodName);

                        Element paramListElement = child.getChild("parameter_list", ns);
                        if (paramListElement != null) {
                            List<Element> paramList = paramListElement.getChildren("parameter", ns);
                            for (Element param : paramList) {
                                Element decl = param.getChild("decl", ns);
                                if (decl != null) {
                                    String paramName = decl.getChildText("name", ns);
                                    if (paramName != null) {
                                        String paramType = decl.getChild("type", ns).getChildText("name", ns);
//                                        System.out.println("param: " + paramName + " " + paramType);
                                        codeParamNameMap.get(fileName).add(paramName);
                                        codeParamTypeMap.get(fileName).add(paramType);
                                    }
                                }
                            }
                        }
                        // invoke method
                        List<Element> exptStmtList = child.getChild("block", ns).getChild("block_content", ns).getChildren("expr_stmt", ns);
                        if (exptStmtList != null) {
                            for (Element exprStmt : exptStmtList) {
                                if (exprStmt.getChild("expr", ns) != null && exprStmt.getChild("expr", ns).getChild("call", ns) != null) {
                                    String callFunction = exprStmt.getChild("expr", ns).getChild("call", ns).getChildText("name", ns);
//                                    System.out.println("call: " + callFunction);
                                    codeInvokeMethodMap.get(fileName).add(callFunction);
                                }
                            }
                        }
                    } else if (child.getName().equals("function_decl")) {
                        String functionName = child.getChildText("name", ns);
                        allMethodSet.add(functionName);
                        Element paramListElement = child.getChild("parameter_list", ns);
                        if (paramListElement != null) {
                            List<Element> paramList = paramListElement.getChildren("parameter", ns);
                            for (Element param : paramList) {
                                Element decl = param.getChild("decl", ns);
                                if (decl != null) {
                                    String paramName = decl.getChildText("name", ns);
                                    if (paramName != null) {
                                        String paramType = decl.getChild("type", ns).getChildText("name", ns);
                                        codeParamNameMap.get(fileName).add(paramName);
                                        codeParamTypeMap.get(fileName).add(paramType);
//                                        System.out.println("param: " + paramName + " " + paramType);
                                    }
                                }
                            }
                        }
                    }
                    List<Element> children1 = child.getChildren();
                    if (children1.size() != 0) {
                        for (Element child1 : children1) {
                            children.offer(child1);
                        }
                    }
                }// while
            } // for
        }
    }


}
