package model;

public class SearchContent {
    public static int search(String text, String pattern) {
        String concat = pattern + "$" + text;

        int[] Z = calculateZ(concat);

        int count = 0;
        for (int i = 0; i < concat.length(); i++) {
            if (Z[i] == pattern.length()) {
                count++;
            }
        }
        return count;
    }

    private static int[] calculateZ(String str) {
        int[] Z = new int[str.length()];
        int left = 0;
        int right = 0;

        for (int i = 1; i < str.length(); i++) {
            if (i > right) {
                left = right = i;
                while (right < str.length() && str.charAt(right) == str.charAt(right - left)) {
                    right++;
                }
                Z[i] = right - left;
                right--;
            } else {
                int k = i - left;
                if (Z[k] < right - i + 1) {
                    Z[i] = Z[k];
                } else {
                    left = i;
                    while (right < str.length() && str.charAt(right) == str.charAt(right - left)) {
                        right++;
                    }
                    Z[i] = right - left;
                    right--;
                }
            }
        }
        return Z;
    }
}