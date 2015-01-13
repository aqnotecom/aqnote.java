java-tools
================

develop
-------------------------------------------------------------

1.install
 > mvn install -Dmaven.test.skip 

2.import into eclipse
 > mvn clean;mvn install -Dmaven.test.skip
 > mvn eclipse:eclipse

3.code template:
 if you want to add some tools or fix bugs,the follow path is need for you
 > tools.doc/src/main/resources/templates

update
--------------------------------------------------------------
### version 0.1.0
 TODO:

### version 1.0.0
 the init version contain the follow sub project:<br />
		tools.btrace				--- for save the btrace for business<br />
		tools.bugs					--- for simulate the bugs<br />
		tools.components			--- no relation with business components<br />
		tools.designpattern			--- use code to description design pattern<br />
		tools.doc					--- document relation to this project<br />
		tools.encrypt				--- about the cryptology code<br />
		tools.hadoop				--- usefull map reduce code<br />
		tools.image					--- relation to graphics or image<br />
		tools.javacc				--- for create language<br />
		tools.lang					--- lang tools<br/>
		tools.maven				--- maven plugins<br />
		tools.net					--- for socket or net code<br />
		tools.nodejs				--- nodejs<br />
		tools.protocol				--- relation to IEEE or ISO net protocol<br />
		tools.test					--- for project code test<br />


