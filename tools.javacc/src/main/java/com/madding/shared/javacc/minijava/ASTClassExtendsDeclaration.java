package com.madding.shared.javacc.minijava;
/* Generated By:JJTree: Do not edit this line. ASTClassExtendsDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTClassExtendsDeclaration extends SimpleNode {
  public ASTClassExtendsDeclaration(int id) {
    super(id);
  }

  public ASTClassExtendsDeclaration(MiniJavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4166e652ac65287949c24100c0e323d7 (do not edit this line) */