import com.gmail.bishoybasily.licensing.issuer.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
public class MainTest {

    @Test
    public void contextLoads() {
        System.out.println("context loads");
    }

}