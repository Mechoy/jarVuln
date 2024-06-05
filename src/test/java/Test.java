import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1","2");

        PrintModel printModel = new PrintModel();
        printModel.setParams(jsonObject);
        printModel.setCustomId(1232131L);
        printModel.setReportId(12312333L);
        printModel.setReportType(1);

        String jsonString = JSONObject.toJSONString(printModel);
        System.out.println(jsonString);

    }
    public static void MsgFileSave2015(String file) {
        try {
//            File f = new File(file);
            byte[] buf = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(stream());
            FileOutputStream fos = new FileOutputStream(file);
            while (true) {
                int size = bis.read(buf);
                if (size != -1) {
                    fos.write(buf, 0, size);
                } else {
                    fos.close();
                    bis.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream stream(){
        File file = new File("111");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
