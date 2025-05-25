package model;

import java.util.*;

public class SearchUser {
    private static int binarySearch(ArrayList<String> list, String value) {
        String target = value.toLowerCase();
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            int middle = start + (end - start) / 2;
            if (list.get(middle).toLowerCase().contains(target)) {
                return middle;
            } else if (list.get(middle).toLowerCase().compareTo(target) > 0) {
                end = middle - 1;
            } else if (list.get(middle).toLowerCase().compareTo(target) < 0) {
                start = middle + 1;
            }
        }
        return -1;
    }

    private static void searchRightLeft(ArrayList<Integer> indexes, ArrayList<ArrayList<String>> listUsername,
                                        String target, Map<Integer, Integer> map) {
        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i) != (-1)) {
                int id = Integer.parseInt(listUsername.get(i).get(indexes.get(i)).split(" - ")[1]);
                map.put(id, map.getOrDefault(id, 0) + 1);
                int left = indexes.get(i) - 1;
                int right = indexes.get(i) + 1;
                while (left >= 0) {
                    if (listUsername.get(i).get(left).toLowerCase().contains(target)) {
                        int leftId = Integer.parseInt(listUsername.get(i).get(left).split(" - ")[1]);
                        map.put(leftId, map.getOrDefault(leftId, 0) + 1);
                        left--;
                    } else {
                        break;
                    }
                }
                while (right < listUsername.get(i).size()) {
                    if (listUsername.get(i).get(right).toLowerCase().contains(target)) {
                        int rightId = Integer.parseInt(listUsername.get(i).get(right).split(" - ")[1]);
                        map.put(rightId, map.getOrDefault(rightId, 0) + 1);
                        right++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static Map<Integer, Integer> getIdSearchUser(String name, ArrayList<ArrayList<String>> listUsername) {
        Map<Integer, Integer> map = new HashMap<>();
        String[] target = name.toLowerCase().split(" ");
        for (String s : target) {
            ArrayList<Integer> indexes = new ArrayList<>();
            for (ArrayList<String> list : listUsername) {
                indexes.add(binarySearch(list, s));
            }

            searchRightLeft(indexes, listUsername, s, map);
        }

        return map;
    }
}
