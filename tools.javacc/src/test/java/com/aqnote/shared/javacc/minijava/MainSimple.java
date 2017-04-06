/*
 * Copyright (C) 2013-2016 aqnote.com<aqnote.com@gmail.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.javacc.minijava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.aqnote.shared.javacc.minijava.MiniJavaParser;
import com.aqnote.shared.javacc.minijava.ParseException;
import com.aqnote.shared.javacc.minijava.SimpleNode;
import com.aqnote.shared.javacc.minijava.visitor.TraversalVisitor;

public class MainSimple {

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        args = new String[1];
        args[0] = "src/test/resources/minijava/1.mjava";
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
