package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private StatisticStorage statisticStorage = new StatisticStorage();


    private static class InstanceHolder{
        private static final StatisticManager instance = new StatisticManager();
    }

    private class StatisticStorage{
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage() {
            for (EventType type : EventType.values()) {
                this.storage.put(type, new ArrayList<EventDataRow>());
            }

        }

        private void put(EventDataRow data){
            if (!this.storage.containsKey(data.getType()))
                throw new UnsupportedOperationException();
            storage.get(data.getType()).add(data);

        }

        private List<EventDataRow> get(EventType type) {
            if (!this.storage.containsKey(type))
                throw new UnsupportedOperationException();

            return this.storage.get(type);
        }
    }

    private StatisticManager() {
    }

    public static StatisticManager getInstance(){
        return InstanceHolder.instance;
    }

    public void register(EventDataRow data){
        this.statisticStorage.put(data);
    }



    public Map<String, Long> getAdvertisementProfitData(){
        Map<String, Long> profitForEachDay = new HashMap<>();
        List<EventDataRow> list = statisticStorage.get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        long total = 0L;
        for (EventDataRow ed : list) {
            VideoSelectedEventDataRow vs = (VideoSelectedEventDataRow) ed;
            String date = simpleDateFormat.format(vs.getDate());
            if (!profitForEachDay.containsKey(date)){
                profitForEachDay.put(date,0L);
            }
            total += vs.getAmount();
            profitForEachDay.put(date, profitForEachDay.get(date) + vs.getAmount());
        }
        
        profitForEachDay.put("Total", total);
        
        return profitForEachDay;

    }

    public Map<String, Map<String, Integer>> getCookWorkloadingData(){
        Map<String, Map<String, Integer>> res = new HashMap<>();
        List<EventDataRow> list = statisticStorage.get(EventType.COOKED_ORDER);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (EventDataRow ed:list) {
            CookedOrderEventDataRow co = (CookedOrderEventDataRow) ed;
            String date = simpleDateFormat.format(co.getDate());
            if (!res.containsKey(date)){
                res.put(date, new HashMap<String, Integer>());
            }
            Map<String, Integer> cookMap = res.get(date);
            if (!cookMap.containsKey(co.getCookName())){
                cookMap.put(co.getCookName(),0);
            }
            cookMap.put(co.getCookName(), cookMap.get(co.getCookName()) + co.getTime());

        }

        return res;
    }


}
