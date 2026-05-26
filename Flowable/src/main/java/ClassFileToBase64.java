import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ClassFileToBase64 {
    public static void main(String[] args) {

        // 指定 class 文件路径
        String classFilePath = "D:\\tools\\exp\\class\\A12311.class"; // 替换为实际路径
        try {
            String base64Encoded = encodeClassFileToBase64(classFilePath);
            System.out.println("Base64 编码结果：");
            System.out.println(base64Encoded);
        } catch (IOException e) {
            System.err.println("发生错误：" + e.getMessage());
        }

    }

    public static String encodeClassFileToBase64(String classFilePath) throws IOException {
        File classFile = new File(classFilePath);
        if (!classFile.exists()) {
            throw new IOException("指定的 class 文件不存在：" + classFilePath);
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(classFile);
            byte[] buffer = new byte[(int) classFile.length()];
            int bytesRead = fis.read(buffer);
            if (bytesRead != buffer.length) {
                throw new IOException("无法完整读取文件内容");
            }
            // 使用 Base64 编码
            return Base64.getEncoder().encodeToString(buffer);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
