import java.io.IOException;

public class Cattle {
    public Cattle() {
        try {
            Thread.sleep(5000);
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}