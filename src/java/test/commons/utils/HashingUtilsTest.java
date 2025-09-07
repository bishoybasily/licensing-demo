package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.gmail.bishoybasily.licensing.commons.utils.HashingUtils.*;

class HashingUtilsTest {

    @Test
    void md5() {
        final var hash = generateHash("abc", MD5);
        Assertions.assertEquals("900150983cd24fb0d6963f7d28e17f72", hash);
    }

    @Test
    void sha1() {
        final var hash = generateHash("abc", SHA1);
        Assertions.assertEquals("a9993e364706816aba3e25717850c26c9cd0d89d", hash);
    }

}