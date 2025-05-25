package model;

import java.util.ArrayList;

public class FindIndexInsert {
    public static int findIndex(ArrayList<String> list, String key) {
        int start = 0;
        int end = list.size() - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (list.get(mid).compareTo(key) == 0) {
                return mid;
            } else if (list.get(mid).compareTo(key) < 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return end + 1;
    }
}