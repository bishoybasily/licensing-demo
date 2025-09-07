package config.licensing;

import com.gmail.bishoybasily.licensing.issuer.config.licensing.LicensingConfig;
import com.gmail.bishoybasily.licensing.issuer.config.licensing.LicensingTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(
        initializers = {
                ConfigDataApplicationContextInitializer.class
        },
        classes = {
                LicensingConfig.class
        }
)
@ExtendWith(SpringExtension.class)
public class LicensingTemplateTest {

    @Autowired
    LicensingTemplate template;

    @Test
    void signatureMatchesObject() throws Exception {

        class OriginalObj {
            String field1 = "value1";
            String field2 = "value2";
        }

        class SimilarObj {
            String field1 = "value1";
            String field2 = "value2";
        }

        final var orgSign = template.generateEncodedSignature(new OriginalObj());
        final var simSign = template.generateEncodedSignature(new SimilarObj());

        assertEquals(orgSign, simSign);

    }

    @Test
    void signatureMatchesMap() throws Exception {

        var originalMap = Map.of("field1", "value1", "field2", "value2");
        var similarMap = Map.of("field1", "value1", "field2", "value2");

        final var orgSign = template.generateEncodedSignature(originalMap);
        final var simSign = template.generateEncodedSignature(similarMap);

        assertEquals(orgSign, simSign);

        assertEquals(orgSign, simSign);

    }
}
