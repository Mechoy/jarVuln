package com.mechoy.flowable;

import java.lang.reflect.Method;
import java.util.Base64;

public class Test {
    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String bytecodeBase64 = "";
        byte[] bytecode;
        try {
            Class<?> clsString = contextClassLoader.loadClass("java.lang.String");
            Class<?> clsBase64 = contextClassLoader.loadClass("java.util.Base64");
            Class<?> clsDecoder = contextClassLoader.loadClass("java.util.Base64$Decoder");
            Base64.Decoder decoder = (Base64.Decoder) clsBase64.getMethod("getDecoder").invoke(null);
            bytecode = (byte[]) clsDecoder.getMethod("decode",clsString).invoke(decoder,bytecodeBase64);

            Class<?> clsClassLoader = contextClassLoader.loadClass("java.lang.ClassLoader");
            Class<?> clasByteArray = (new java.lang.String("c").getBytes().getClass());
            Class<?> clsInt = java.lang.Integer.TYPE;

            Method defineClass = clsClassLoader.getDeclaredMethod("defineClass", new Class[]{clasByteArray, clsInt, clsInt});
            defineClass.setAccessible(true);
            Class clazz = (Class) defineClass.invoke(contextClassLoader, bytecode, new Integer(0), new Integer(bytecode.length));
            clazz.newInstance();
        } catch (Exception ee) {
            java.lang.System.out.println(ee.getMessage());
        }

    }
}
