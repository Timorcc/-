import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test {


    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\test.jpg");

        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        is.read(bytes);
        System.out.println(new String(bytes));
        is.close();

    }
}
