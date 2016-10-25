package io.oilfox.backend.api.shared.helpers;

/**
 * Created by ipusic on 1/21/16.
 */
public final class StringHelper {
    public static String removeButHex(String input) {
        return input.replaceAll("[^A-Fa-f0-9]", "");
    }
}