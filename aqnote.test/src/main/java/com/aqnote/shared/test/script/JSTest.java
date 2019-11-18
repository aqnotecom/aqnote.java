package com.aqnote.shared.test.script;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSTest {

    public ScriptEngine        engine   = new ScriptEngineManager().getEngineByName("nashorn");
    public Bindings            bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

    public static final String jsPath   = "/Users/madding/scode/github/aqnotecom/java.tools/tools.test/src/main/java/com/aqnote/shared/test/script/";

    public static void main(String args[]) throws Exception {

        JSTest test = new JSTest();
        test.init();

        test.test_Double();
        test.test_Integer();
        test.test_invokeJava();
        test.test_invokeJSfile1();
        test.test_invokeJSfile2();
        test.test_invokeJSfile3();
        test.test_invokeJSfile4();
        test.test_invokeJSfile5();
        test.test_invokeJSfile6();
        test.test_invokeJSfile7();
        test.test_invokeJSfile8();
    }

    public void init() {
    }

    public void test_Double() throws ScriptException {
        String script = "Math.PI";
        Double result = (Double) engine.eval(script);
        System.out.println(result);
        System.out.println("=====================");
    }

    public void test_Integer() throws ScriptException {
        String script = "1+1";
        Integer result = (Integer) engine.eval(script);
        System.out.println(result);
        System.out.println("=====================");
    }

    public void test_invokeJava() throws ScriptException {
        bindings.clear();
        bindings.put("stdout", System.out);

        String script = "1+1";
        engine.eval("stdout.println(" + script + ");");
        System.out.println("=====================");
    }

    public void test_invokeJSfile1() throws ScriptException {
        engine.eval("load('" + jsPath + "test_type.js')");
        System.out.println("=====================");
    }

    public void test_invokeJSfile2() throws ScriptException {
        engine.eval("load('" + jsPath + "test_hashmap.js')");
        System.out.println("=====================");
    }

    public void test_invokeJSfile3() throws ScriptException {
        engine.eval("load('" + jsPath + "test_string.js')");
        System.out.println("=====================");
    }

    public void test_invokeJSfile4() throws ScriptException {
        engine.eval("load('" + jsPath + "test_sysout.js')");
        System.out.println("=====================");
    }

    public void test_invokeJSfile5() throws ScriptException {
        engine.eval("load('" + jsPath + "test_date.js')");
        System.out.println("=====================");
    }

    public void test_invokeJSfile6() throws ScriptException, NoSuchMethodException {
        engine.eval("load('" + jsPath + "test_myobj.js')");
        if (engine instanceof Invocable) {
            Invocable invoke = (Invocable) engine;
            Boolean result = (Boolean) invoke.invokeFunction("execute", null);
            System.out.println(result);

        }
        System.out.println("=====================");
    }

    public void test_invokeJSfile7() throws ScriptException, NoSuchMethodException {
        bindings.clear();
        bindings.put("stdout", System.out);
        engine.eval("load('" + jsPath + "test_myquery.js')");
        if (engine instanceof Invocable) {
            Invocable invoke = (Invocable) engine;
            Boolean result = (Boolean) invoke.invokeFunction("execute", null);
            System.out.println(result);

        }
        System.out.println("=====================");
    }

    public void test_invokeJSfile8() throws ScriptException, NoSuchMethodException {
        bindings.clear();
        bindings.put("stdout", System.out);
        long start = System.currentTimeMillis();
        for(int i=0; i< 1000; i++) {
            String script = "function execute() {"
                    + "var QueryData = Java.type('com.aqnote.shared.test.script.QueryData');"
                    + "var user = QueryData.getUser();"
                    + "var session = QueryData.getSession();"
                    + "var device = QueryData.getDevice();"
                    + ""
                    + "var result = true;"
                    + "if(user.nick == \"" + i + "\") {"
                    + "     result = result && false;"
                    + "}"
                    + ""
                    + "if(session.isLogin == true) {"
                    + "     result = result && true;"
                    + "}"
                    + ""
                    + "if(device.type == \"android\") {"
                    + "     result = result && true;"
                    + "}"
                    + ""
                    + "return result;"
                    + "}";
            engine.eval(script);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                Boolean result = (Boolean) invoke.invokeFunction("execute", null);
//                System.out.println(result);
            }
        }
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        System.out.println("=====================");
    }
}
