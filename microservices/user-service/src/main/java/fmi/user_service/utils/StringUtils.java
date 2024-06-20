package fmi.user_service.utils;

import java.util.Arrays;

public class StringUtils {
    public static <T> String joinElementsFromArray(T[] elements) {
        return Arrays.stream(elements)
                .map(T::toString)
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }

    public static <T> String getFirstNLinesOfStacktrace(T[] elements, int lines) {
        return Arrays.stream(elements)
                .map(T::toString)
                .limit(lines)
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }
}
