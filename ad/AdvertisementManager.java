package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdvertisementManager {
    private int timeSeconds;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }
    
    private long maxAmount;
    private List<Advertisement> optimalVideoSet;
    private int totalTimeSecondsLeft;

    public void processVideos() throws NoVideoAvailableException{
        this.totalTimeSecondsLeft = Integer.MAX_VALUE;
        obtainOptimalVideoSet(new ArrayList<>(), timeSeconds, 0L);
        VideoSelectedEventDataRow videoSelectedEventDataRow = new VideoSelectedEventDataRow(optimalVideoSet, maxAmount, timeSeconds - totalTimeSecondsLeft);
        StatisticManager.getInstance().register(videoSelectedEventDataRow);
        displayAdvertisement();

    }
    
    private void obtainOptimalVideoSet(List<Advertisement> totalList, int currentTimeSecondsLeft, long currentAmount) {
        if (currentTimeSecondsLeft < 0) {
            return;
        } else if (currentAmount > maxAmount || currentAmount == maxAmount &&
                (totalTimeSecondsLeft > currentTimeSecondsLeft || totalTimeSecondsLeft == currentTimeSecondsLeft
                        && totalList.size() < optimalVideoSet.size())) {
            this.totalTimeSecondsLeft = currentTimeSecondsLeft;
            this.optimalVideoSet = totalList;
            this.maxAmount = currentAmount;
            if (currentTimeSecondsLeft == 0) {
                return;
            }
        }

        
        ArrayList<Advertisement> list = getActualAdvertisement();
        list.removeAll(totalList);
        for (Advertisement ad : list) {
            if (!ad.isAlive()) continue;
            ArrayList<Advertisement> currentList = new ArrayList<>(totalList);
            currentList.add(ad);
            obtainOptimalVideoSet(currentList, currentTimeSecondsLeft - ad.getDuration(), currentAmount + ad.getAmountPerOneDisplaying());
        }
    }
    
    private ArrayList<Advertisement> getActualAdvertisement(){
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        for (Advertisement ad : storage.list()) {
            if (ad.isAlive()){
                advertisements.add(ad);
            }
        }
        return advertisements;
    }
    
    private void displayAdvertisement(){
        if (optimalVideoSet == null || optimalVideoSet.isEmpty()){
            throw new NoVideoAvailableException();
        }
        
        optimalVideoSet.sort((o1, o2) -> {
            long l = o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying();
            return l != 0 ? (int) l : o2.getDuration() - o1.getDuration();
        });

        for (Advertisement ad : optimalVideoSet) {
            displayInPlayer(ad);
            ad.revalidate();
        }
    }
    
    private void displayInPlayer(Advertisement advertisement){
        ConsoleHelper.writeMessage(advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() + 
                ", " + (1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration()));
    }


}
