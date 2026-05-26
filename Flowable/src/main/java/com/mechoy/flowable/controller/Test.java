package com.mechoy.flowable.controller;


import com.mechoy.flowable.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/test")
@Slf4j
public class Test {
    @GetMapping("/111")
    public void deploy() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String base64 = "yv66vgAAADQALgoACgAZCgAaABsIABwKABoAHQoAHgAfBwAgBwAhCgAHACIHACMHACQBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEADUxvcmcvQWFxYXFhcTsBAAg8Y2xpbml0PgEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBAA1TdGFja01hcFRhYmxlBwAgAQAKU291cmNlRmlsZQEADEFhcWFxYXEuamF2YQwACwAMBwAlDAAmACcBAAdzbGVlcCA1DAAoACkHACoMACsALAEAE2phdmEvbGFuZy9FeGNlcHRpb24BABpqYXZhL2xhbmcvUnVudGltZUV4Y2VwdGlvbgwACwAtAQALb3JnL0FhcWFxYXEBABBqYXZhL2xhbmcvT2JqZWN0AQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAEWphdmEvbGFuZy9Qcm9jZXNzAQAHd2FpdEZvcgEAAygpSQEAGChMamF2YS9sYW5nL1Rocm93YWJsZTspVgAhAAkACgAAAAAAAgABAAsADAABAA0AAAAvAAEAAQAAAAUqtwABsQAAAAIADgAAAAYAAQAAAAUADwAAAAwAAQAAAAUAEAARAAAACAASAAwAAQANAAAAaQADAAEAAAAauAACEgO2AAS2AAVXpwANS7sAB1kqtwAIv7EAAQAAAAwADwAGAAMADgAAABYABQAAAAgADAALAA8ACQAQAAoAGQAMAA8AAAAMAAEAEAAJABMAFAAAABUAAAAHAAJPBwAWCQABABcAAAACABg=";
        byte[] bytes = Base64.decodeBase64(base64);
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class clsClassLoader = contextClassLoader.loadClass("java.lang.ClassLoader");
        Class byteClass = new String("c").getBytes().getClass();
        Class clsInt = java.lang.Integer.TYPE;

        Method defineClass = clsClassLoader.getDeclaredMethod("defineClass", new Class[]{byteClass, clsInt, clsInt});
        defineClass.setAccessible(true);
        Class clazz = (Class)defineClass.invoke(contextClassLoader, bytes, new Integer(0), new Integer(bytes.length));
        clazz.newInstance();
    }
}
