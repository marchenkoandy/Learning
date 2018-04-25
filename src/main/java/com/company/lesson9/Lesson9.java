package com.company.lesson9;

import java.util.*;

public class Lesson9 {
//    10. С использованием множества выполнить попарное суммирование произ-
//    вольного конечного ряда чисел по следующим правилам: на первом этапе
//    суммируются попарно рядом стоящие числа, на втором этапе суммируются
//    результаты первого этапа и т. д. до тех пор, пока не останется одно число.

    public long getSumStream(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public long getSumOrdinar(List<Integer> list) {
        long sum = 0;
        for (int i1 = 0, listSize = list.size(); i1 < listSize; i1++) {
            Integer i = list.get(i1);
            sum += i;
        }
        return sum;
    }

    public void printList(List<Integer> list) {
        System.out.println(String.format("Sum of elements '%s'. List:\n%s", getSumStream(list), list));
    }

    public Set<Integer> generateRandomSet(int count) {
        Set<Integer> set = new TreeSet<>();
        while (count > set.size()) {
            set.add(new Random().nextInt(count + 100));
        }
//        printList(new ArrayList<>(set));
        return set;
    }

    public int sumSet(Set<Integer> set) {
        List<Integer> list = new ArrayList<>(set);
        while (list.size() > 1) {
            List<Integer> subList = new ArrayList<>();
            int listSize = list.size();
            for (int i = 0; i < listSize; i = i + 2) {
                if ((listSize - 1) == i && listSize % 2 == 1) {
                    subList.add(list.get(i));
                } else {
                    subList.add(list.get(i) + list.get(i + 1));
                }
            }
            list = subList;
            printList(list);
        }
        return list.get(0);
    }

    public static void main(String[] args) {
        Lesson9 lesson9 = new Lesson9();
        List <Integer> list = new ArrayList<>(lesson9.generateRandomSet(10_000_000));
        long start;
        long stop;
        start = new Date().getTime();
        System.out.println(lesson9.getSumOrdinar(list));
        stop = new Date().getTime();
        System.out.println("Stream loop: "+(stop-start));


        start = new Date().getTime();
        System.out.println(lesson9.getSumOrdinar(list));
        stop = new Date().getTime();
        System.out.println("Ordinar loop: "+(stop-start));


    }


}
