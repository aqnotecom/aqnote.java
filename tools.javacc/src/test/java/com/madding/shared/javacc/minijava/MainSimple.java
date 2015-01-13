package com.madding.shared.javacc.minijava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.madding.shared.javacc.minijava.visitor.TraversalVisitor;

public class MainSimple {

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        args = new String[1];
        args[0] = "src/test/resources/minijava/1.minijava";
        File file = new File(args[0]);
        FileInputStream fs = new FileInputStream(file);
        MiniJavaParser parser = new MiniJavaParser(fs);
        // 进行词法分析、语法分析，并构建抽象语法树
        parser.Goal();

        // 获得AST的根节点
        SimpleNode root = (SimpleNode) parser.jjtree.rootNode();
        System.out.println("#################dump info###################");
        // 输出抽象语法树
        root.dump(">");

        System.out.println("#################dump&visitor info###################");
        TraversalVisitor tVisitor = new TraversalVisitor();
        root.jjtAccept(tVisitor, null);
        System.out.println();
        // 输出抽象语法树
        root.dump(">");

    }
}
