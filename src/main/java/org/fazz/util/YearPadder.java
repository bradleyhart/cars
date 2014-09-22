package org.fazz.util;

public class YearPadder {

    public static String padYearLowerBound(String toPad) {
        return padYear(toPad, '0');
    }

    public static String padYearHigherBound(String toPad) {
        return padYear(toPad, '9');
    }

    public static String padYear(String toPad, char padChar) {
        switch (toPad.length()) {
            case 1:
                return toPad + padChar + padChar + padChar;
            case 2:
                return toPad + padChar + padChar;
            case 3:
                return toPad + padChar;
            case 4:
                return toPad;
            default:
                throw new IllegalArgumentException("Year must be integer 0 >= year <= 9999");
        }
    }
}
