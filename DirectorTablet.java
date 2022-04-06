package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.*;

public class DirectorTablet {
    public void printAdvertisementProfit(){
        Map<String, Long> profitForEachDay = StatisticManager.getInstance().getAdvertisementProfitData();
        ArrayList<String> list = new ArrayList<>(profitForEachDay.keySet());
        Collections.sort(list);
        Collections.reverse(list);

        for (String s:list) {
            double price = 1.0 * profitForEachDay.get(s) / 100;
            System.out.println(s + " - " + String.format(Locale.ENGLISH,"%.2f", price));
        }


    }

    public void printCookWorkloading(){
        Map<String, Map<String, Integer>> cookWorkloading = StatisticManager.getInstance().getCookWorkloadingData();
        ArrayList<String> list = new ArrayList<>(cookWorkloading.keySet());
        Collections.sort(list);
        Collections.reverse(list);

        for (String date:list) {
            System.out.println(date);
            for (Map.Entry<String,Integer> entry: cookWorkloading.get(date).entrySet()) {
                if (entry.getValue() == null){
                    continue;
                }
                int time = Math.round(entry.getValue() / 60.0f);
                System.out.println(entry.getKey() + " - " + time + " min");
            }
        }

    }

    public void printActiveVideoSet(){
        Map<String, Integer> activeVideoSet = StatisticAdvertisementManager.getInstance().getActiveVideoSet();
        ArrayList<String> list = new ArrayList<>(activeVideoSet.keySet());
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        for (String s:list){
            System.out.println(s + " - " + activeVideoSet.get(s));
        }

    }

    public void printArchivedVideoSet(){
        ArrayList<String> list = StatisticAdvertisementManager.getInstance().getArchivedVideoSet();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        for (String s:list){
            System.out.println(s);
        }
    }
}
