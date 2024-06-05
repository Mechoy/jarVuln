package com.mechoy.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JdomTest {
    public static void main(String[] args) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/xxe.xml"));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document build = saxBuilder.build(byteArrayInputStream);
        Element rootElement = build.getRootElement();
        System.out.println(rootElement.getName());

        Element debug = rootElement.getChild("debug");
        System.out.println(debug.getValue());

        Element key = rootElement.getChild("key");
        System.out.println(key.getValue());
    }
}
