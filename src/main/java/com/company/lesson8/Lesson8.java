package com.company.lesson8;

import java.util.*;
import java.util.stream.Collectors;

/**
 * #Summary:
 * #Author: Andrii_Marchenko1
 * #Author’s Email:
 * #Creation Date: 4/20/2018
 * #Comments:
 */
public class Lesson8 {
    private final String LINE = "Задание 3: В тексте найти и напечатать n символов (и их количество), встречающихся наиболее часто.";

    private List<String> getListOfChars(String s) {
        return new ArrayList<>(Arrays.asList(s.split("")));
    }

    private Map<String, Integer> getUniqueCharsCount(List<String> list) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        for (String ch : list) {
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
        }
        return map;
    }

    private Map<String, Integer> sortMapByValueWithStreamApi(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new
                        ));
    }

    private Map<String, Integer> sortMapByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map<String, Integer> res = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            res.put(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private void printMostCommonlyUsedLetters(Map<String, Integer> map, int number) {
        int counter = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (++counter < number) {
                System.out.println(String.format("Key: %s; Value: %s", entry.getKey(), entry.getValue()));
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Lesson8 lesson8 = new Lesson8();
        List<String> list = lesson8.getListOfChars(lesson8.LINE);
        Map<String, Integer> map = lesson8.getUniqueCharsCount(list);
        Map<String, Integer> sortedMapWithStreamApi = lesson8.sortMapByValueWithStreamApi(map);
        lesson8.printMostCommonlyUsedLetters(sortedMapWithStreamApi, 5);
        Map<String, Integer> sortedMap = lesson8.sortMapByValue(map);
        lesson8.printMostCommonlyUsedLetters(sortedMap, 5);
    }
}
