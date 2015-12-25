### java-tools ###

#### develop ####
-------------------------------------------------------------

1.install
 > mvn install -Dmaven.test.skip 

2.import into eclipse
 > mvn clean;mvn install -Dmaven.test.skip
 > mvn eclipse:eclipse

3.code template:
 if you want to add some tools or fix bugs,the follow path is need for you
 > tools.doc/src/main/resources/templates

#### update ####
--------------------------------------------------------------
**version 0.2.0**
 
 TODO:

 * add More MD to tools.encrypt

----------------------------------------

**version 0.1.0**

  * remove tools.nodejs 
  * remove tools.doc to docs
  * add SHA1-3 MD2、4、5  SM\* to tools.encrypt

----------------------------------------

**version 0.0.1**

the init version contain the follow sub project:  
  * tools.btrace				  --- for save the btrace for business
  *	tools.bugs					  --- for simulate the bugs
  * tools.components			--- no relation with business components
  *	tools.designpattern		--- use code to description design pattern
  *	tools.doc					    --- document relation to this project
  * tools.encrypt				  --- about the cryptology code
  * tools.hadoop				  --- usefull map reduce code
  * tools.image					  --- relation to graphics or image
  * tools.javacc				  --- for create language
  * tools.lang					  --- lang tools
  * tools.maven			  	  --- maven plugins
  * tools.net					    --- for socket or net code
  * tools.nodejs				  --- nodejs
  * tools.protocol				--- relation to IEEE or ISO net protocol
  * tools.test					  --- for project code test


