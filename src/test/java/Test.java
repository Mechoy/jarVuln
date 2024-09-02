import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://1111.y3zdmn.dnslog.cn");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String line = null;
        InputStream inputStream = urlConnection.getInputStream();
    }
}
