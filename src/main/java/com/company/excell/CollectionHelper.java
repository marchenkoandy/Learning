package com.company.excell;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * #Summary:
 * #Author: Andrii_Marchenko1
 * #Author’s Email:
 * #Creation Date: 4/25/2018
 * #Comments:
 */
public class CollectionHelper {

    //<editor-fold desc="Constructor">
    private CollectionHelper() {
    }
    //</editor-fold>

    //<editor-fold desc="Public Methods">
    public static <K, V> int getMaxValueListSize(Map<K, List<V>> map) {
        Optional<Map.Entry<K, List<V>>> optional = map.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue().size()));
        return optional.map(kListEntry -> kListEntry.getValue().size()).orElse(0);
    }

    public static <K, V> Map<K, List<V>> mergeMaps(Map<K, List<V>> map1, Map<K, List<V>> map2, V defaultValue) {
        Map<K, List<V>> copyMap1 = normalizeMap(new LinkedHashMap<>(map1), defaultValue);
        Map<K, List<V>> copyMap2 = normalizeMap(new LinkedHashMap<>(map2), defaultValue);

        Set<K> mergedKeys = new LinkedHashSet<>(copyMap1.keySet());
        mergedKeys.addAll(copyMap2.keySet());
        Map<K, List<V>> mergedMap = new LinkedHashMap<>(copyMap1);
        for (K key : mergedKeys) {
            if (!mergedMap.containsKey(key)) {
                mergedMap.put(key, new LinkedList<>());
            }
        }
        mergedMap = normalizeMap(mergedMap, defaultValue);
        mergedMap = Stream
                .of(mergedMap, copyMap2)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                ));
        return normalizeMap(mergedMap, defaultValue);
    }

    public static <T, U> List<U> convertList(List<T> listFrom, Function<T, U> function) {
        return listFrom.stream().map(function).collect(Collectors.toList());
    }

    public static <T> List<T> filterList(List<T> initList, Predicate<T> predicate) {
        return initList.stream().filter(predicate).collect(Collectors.toList());
    }
    //</editor-fold>

    //<editor-fold desc="Private Methods">
    private static <T> List<T> fillListWithDefaultValues(int count, T defaultValue) {
        List<T> list = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            list.add(defaultValue);
        }
        return list;
    }

    private static <K, V> Map<K, List<V>> normalizeMap(Map<K, List<V>> map, V defaultValue) {
        Map<K, List<V>> mOut = new LinkedHashMap<>();
        int max = getMaxValueListSize(map);
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            int delta = max - entry.getValue().size();
            K key = entry.getKey();
            List<V> value = entry.getValue();
            List<V> deltaList = new LinkedList<>();
            if (delta > 0) {
                deltaList.addAll(fillListWithDefaultValues(delta, defaultValue));
            }
            value.addAll(deltaList);
            mOut.put(key, value);
        }
        return mOut;
    }
    //</editor-fold>
}
