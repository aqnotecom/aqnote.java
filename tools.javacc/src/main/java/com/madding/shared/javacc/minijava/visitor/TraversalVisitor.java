package com.madding.shared.javacc.minijava.visitor;

import com.madding.shared.javacc.minijava.ASTAllocationExpression;
import com.madding.shared.javacc.minijava.ASTAndExpression;
import com.madding.shared.javacc.minijava.ASTArrayAllocationExpression;
import com.madding.shared.javacc.minijava.ASTArrayAssignmentStatement;
import com.madding.shared.javacc.minijava.ASTArrayLength;
import com.madding.shared.javacc.minijava.ASTArrayLookup;
import com.madding.shared.javacc.minijava.ASTArrayType;
import com.madding.shared.javacc.minijava.ASTAssignmentStatement;
import com.madding.shared.javacc.minijava.ASTBlock;
import com.madding.shared.javacc.minijava.ASTBooleanType;
import com.madding.shared.javacc.minijava.ASTBracketExpression;
import com.madding.shared.javacc.minijava.ASTClassDeclaration;
import com.madding.shared.javacc.minijava.ASTClassExtendsDeclaration;
import com.madding.shared.javacc.minijava.ASTCompareExpression;
import com.madding.shared.javacc.minijava.ASTExpression;
import com.madding.shared.javacc.minijava.ASTExpressionList;
import com.madding.shared.javacc.minijava.ASTExpressionRest;
import com.madding.shared.javacc.minijava.ASTFalseLiteral;
import com.madding.shared.javacc.minijava.ASTFormalParameter;
import com.madding.shared.javacc.minijava.ASTFormalParameterList;
import com.madding.shared.javacc.minijava.ASTFormalParameterRest;
import com.madding.shared.javacc.minijava.ASTGoal;
import com.madding.shared.javacc.minijava.ASTIdentifier;
import com.madding.shared.javacc.minijava.ASTIfStatement;
import com.madding.shared.javacc.minijava.ASTIntegerLiteral;
import com.madding.shared.javacc.minijava.ASTIntegerType;
import com.madding.shared.javacc.minijava.ASTMainClass;
import com.madding.shared.javacc.minijava.ASTMessageSend;
import com.madding.shared.javacc.minijava.ASTMethodDeclaration;
import com.madding.shared.javacc.minijava.ASTMinusExpression;
import com.madding.shared.javacc.minijava.ASTNotExpression;
import com.madding.shared.javacc.minijava.ASTPlusExpression;
import com.madding.shared.javacc.minijava.ASTPrimaryExpression;
import com.madding.shared.javacc.minijava.ASTPrintStatement;
import com.madding.shared.javacc.minijava.ASTStatement;
import com.madding.shared.javacc.minijava.ASTThisExpression;
import com.madding.shared.javacc.minijava.ASTTimesExpression;
import com.madding.shared.javacc.minijava.ASTTrueLiteral;
import com.madding.shared.javacc.minijava.ASTType;
import com.madding.shared.javacc.minijava.ASTTypeDeclaration;
import com.madding.shared.javacc.minijava.ASTVarDeclaration;
import com.madding.shared.javacc.minijava.ASTWhileStatement;
import com.madding.shared.javacc.minijava.MiniJavaParserVisitor;
import com.madding.shared.javacc.minijava.SimpleNode;


public class TraversalVisitor implements MiniJavaParserVisitor {

    @Override
    public Object visit(SimpleNode node, Object data) {
        System.out.println("SimpleNode");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTGoal node, Object data) {
        System.out.print("ASTGoal[" + data + "] ");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTMainClass node, Object data) {
        System.out.print("ASTMainClass[" + data + "] ");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTTypeDeclaration node, Object data) {
        System.out.print("ASTTypeDeclaration[" + data + "] ");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTClassDeclaration node, Object data) {
        System.out.print("ASTClassDeclaration ");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTClassExtendsDeclaration node, Object data) {
        System.out.println("ASTClassExtendsDeclaration");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTVarDeclaration node, Object data) {
        System.out.println("ASTVarDeclaration");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTMethodDeclaration node, Object data) {
        System.out.println("ASTMethodDeclaration");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFormalParameterList node, Object data) {
        System.out.println("ASTFormalParameterList");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFormalParameter node, Object data) {
        System.out.println("ASTFormalParameter");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFormalParameterRest node, Object data) {
        System.out.println("ASTFormalParameterRest");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTType node, Object data) {
        System.out.println("ASTType");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTArrayType node, Object data) {
        System.out.println("ASTArrayType");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTBooleanType node, Object data) {
        System.out.println("ASTBooleanType");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTIntegerType node, Object data) {
        System.out.println("ASTIntegerType");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTStatement node, Object data) {
        System.out.println("ASTStatement");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTBlock node, Object data) {
        System.out.println("ASTBlock");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTAssignmentStatement node, Object data) {
        System.out.println("ASTAssignmentStatement");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTArrayAssignmentStatement node, Object data) {
        System.out.println("ASTArrayAssignmentStatement");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTIfStatement node, Object data) {
        System.out.println("ASTIfStatement");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTWhileStatement node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTPrintStatement node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTAndExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTCompareExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTPlusExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTMinusExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTTimesExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTArrayLookup node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTArrayLength node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTMessageSend node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTExpressionList node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTExpressionRest node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTPrimaryExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTIntegerLiteral node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTTrueLiteral node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTFalseLiteral node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTThisExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTArrayAllocationExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTAllocationExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTNotExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTBracketExpression node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

}
