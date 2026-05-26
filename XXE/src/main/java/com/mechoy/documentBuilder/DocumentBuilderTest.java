package com.mechoy.documentBuilder;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class DocumentBuilderTest {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setExpandEntityReferences(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        java.lang.Thread.sleep(60000);


        String resourcePath = "documentBuilder.txt";
        InputStream inputStream = DocumentBuilderTest.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource文件未找到: " + resourcePath);
        }
        // 3. 安全解析XML
        Document doc = db.parse(inputStream);
        // 4. 处理XML内容（示例）
        System.out.println("Root节点: " + doc.getDocumentElement().getTextContent());
    }
}
