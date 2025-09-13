package com.gmail.bishoybasily.licensing.commons.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SerializationUtils {

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private static final DateTimeFormatter CANONICAL_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZONE_OFFSET);

    public static String generateCanonicalString(Object obj) {
        if (obj == null) return "null";

        // --- Terminal types ---
        if (obj instanceof String || obj instanceof Number || obj instanceof Boolean || obj instanceof Enum<?>) {
            return obj.toString();
        }

        // --- Java 8+ Date/Time handling ---
        if (obj instanceof Instant) {
            return CANONICAL_DATETIME.format((Instant) obj);
        }
        if (obj instanceof OffsetDateTime) {
            return CANONICAL_DATETIME.format(((OffsetDateTime) obj).toInstant());
        }
        if (obj instanceof ZonedDateTime) {
            return CANONICAL_DATETIME.format(((ZonedDateTime) obj).toInstant());
        }
        if (obj instanceof LocalDateTime) {
            return CANONICAL_DATETIME.format(((LocalDateTime) obj).atZone(ZoneOffset.UTC).toInstant());
        }
        if (obj instanceof LocalDate) {
            return ((LocalDate) obj).toString(); // always yyyy-MM-dd
        }
        if (obj instanceof LocalTime) {
            return ((LocalTime) obj).toString(); // always HH:mm:ss(.SSS)
        }

        // Arrays
        if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            List<String> parts = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                parts.add(generateCanonicalString(Array.get(obj, i)));
            }
            return "[" + String.join(",", parts) + "]";
        }

        // Collections
        if (obj instanceof Collection<?>) {
            List<String> parts = new ArrayList<>();
            for (Object o : (Collection<?>) obj) {
                parts.add(generateCanonicalString(o));
            }
            return "[" + String.join(",", parts) + "]";
        }

        // Maps (sorted by key to guarantee order)
        if (obj instanceof Map<?, ?> map) {
            List<String> parts = new ArrayList<>();
            map.entrySet().stream()
                    .sorted(Comparator.comparing(a -> a.getKey().toString()))
                    .forEach(e -> parts.add(e.getKey() + "=" + generateCanonicalString(e.getValue())));
            return "{" + String.join(",", parts) + "}";
        }

        // Fallback: POJO (reflect fields, sorted by name)
        List<String> parts = new ArrayList<>();
        for (Field field : listFields(obj.getClass())) {
            try {
                field.setAccessible(true);
            } catch (Exception ignored) {
            }

            try {
                Object value = field.get(obj);
                parts.add(field.getName() + "=" + generateCanonicalString(value));
            } catch (IllegalAccessException ignored) {
            }
        }
        return "{" + String.join(",", parts) + "}";
    }

    private static List<Field> listFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        fields.sort(Comparator.comparing(Field::getName));
        return fields;
    }

}
