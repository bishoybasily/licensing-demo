package utils;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static com.gmail.bishoybasily.licensing.commons.utils.SerializationUtils.generateCanonicalString;
import static org.junit.jupiter.api.Assertions.assertEquals;


class SerializationUtilsTest {

    @Test
    void testNull() {
        assertEquals("null", generateCanonicalString(null));
    }

    @Test
    void testPrimitivesAndWrappers() {
        assertEquals("123", generateCanonicalString(123));
        assertEquals("true", generateCanonicalString(true));
        assertEquals("hello", generateCanonicalString("hello"));
    }

    @Test
    void testPrimitiveArray() {
        int[] arr = {1, 2, 3};
        assertEquals("[1,2,3]", generateCanonicalString(arr));
    }

    @Test
    void testObjectArray() {
        String[] arr = {"a", "b"};
        assertEquals("[a,b]", generateCanonicalString(arr));
    }

    @Test
    void testCollectionList() {
        List<String> list = Arrays.asList("x", "y");
        assertEquals("[x,y]", generateCanonicalString(list));
    }

    @Test
    void testCollectionSet() {
        Set<Integer> set = new LinkedHashSet<>();
        set.add(10);
        set.add(20);
        assertEquals("[10,20]", generateCanonicalString(set));
    }

    @Test
    void testMapOrdering() {
        Map<String, Object> map = new HashMap<>();
        map.put("z", 1);
        map.put("a", 2);
        map.put("m", 3);
        String result = generateCanonicalString(map);
        // Keys must be ordered alphabetically
        assertEquals("{a=2,m=3,z=1}", result);
    }

    @Test
    void testNestedPojo() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2025, 8, 30, 23, 13, 11, 731000000, ZoneOffset.UTC);
        User u = new User(1, "Alice", new Contact("alice@test.com", "1234", offsetDateTime));
        String expected = "{contact={email=alice@test.com,phone=1234,registration=2025-08-30T23:13:11.731Z},id=1,name=Alice}";
        assertEquals(expected, generateCanonicalString(u));
    }

    @Test
    void testInheritance() {
        Derived d = new Derived();
        String result = generateCanonicalString(d);
        // Fields sorted: baseField, derivedField
        assertEquals("{baseField=base,derivedField=derived}", result);
    }

    @Test
    void testEmptyCollection() {
        assertEquals("[]", generateCanonicalString(new ArrayList<>()));
    }

    @Test
    void testEmptyMap() {
        assertEquals("{}", generateCanonicalString(new HashMap<>()));
    }

    @Test
    void testPojoWithNullField() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2025, 8, 30, 23, 13, 11, 731000000, ZoneOffset.UTC);
        Contact c = new Contact(null, "1234", offsetDateTime);
        String result = generateCanonicalString(c);
        assertEquals("{email=null,phone=1234,registration=2025-08-30T23:13:11.731Z}", result);
    }

    static class Contact {

        String email;
        String phone;
        OffsetDateTime registration;

        Contact(String e, String p, OffsetDateTime r) {
            email = e;
            phone = p;
            this.registration = r;
        }
    }

    static class User {

        int id;
        String name;
        Contact contact;

        User(int id, String n, Contact c) {
            this.id = id;
            name = n;
            contact = c;
        }
    }

    static class Base {
        String baseField = "base";
    }

    static class Derived extends Base {
        String derivedField = "derived";
    }
}