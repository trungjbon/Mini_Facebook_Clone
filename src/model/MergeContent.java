package model;

import java.util.ArrayList;
import java.util.List;

public class MergeContent {
    private static List<Content> sortedMerge(List<Content> l1, List<Content> l2) {
        List<Content> result = new ArrayList<>();
        int idx1 = 0;
        int idx2 = 0;
        while (idx1 < l1.size() && idx2 < l2.size()) {
            if (l1.get(idx1).getValueTimeStamp() <= l2.get(idx2).getValueTimeStamp()) {
                result.add(l1.get(idx1));
                idx1++;
            } else {
                result.add(l2.get(idx2));
                idx2++;
            }
        }
        while (idx1 < l1.size()) {
            result.add(l1.get(idx1));
            idx1++;
        }
        while (idx2 < l2.size()) {
            result.add(l2.get(idx2));
            idx2++;
        }

        return result;
    }

    public static List<Content> mergeKLists(List<List<Content>> lists, int last) {
        while (last != 0) {
            int i = 0;
            int j = last;
            while (i < j) {
                lists.set(i, sortedMerge(lists.get(i), lists.get(j)));
                i++;
                j--;
                if (i >= j) {
                    last = j;
                }
            }
        }
        return lists.get(0);
    }
}