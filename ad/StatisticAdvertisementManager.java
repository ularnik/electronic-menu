package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticAdvertisementManager {
    private static final StatisticAdvertisementManager myInstance = new StatisticAdvertisementManager();
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {

    }

    public static StatisticAdvertisementManager getInstance(){
        return myInstance;
    }

    public Map<String, Integer> getActiveVideoSet(){
        Map<String, Integer> res = new HashMap<>();
        List<Advertisement> list = storage.list();
        for (Advertisement ad:list) {
            if (ad.isAlive()){
                res.put(ad.getName(), ad.getHits());
            }
        }

        return res;
    }

    public ArrayList<String> getArchivedVideoSet(){
        ArrayList<String> res = new ArrayList<>();
        List<Advertisement> list = storage.list();
        for (Advertisement ad:list) {
            if (!ad.isAlive()){
                res.add(ad.getName());
            }
        }

        return res;
    }
}
