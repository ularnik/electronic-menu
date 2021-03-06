package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;

public class Advertisement {
    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        try{
            amountPerOneDisplaying = initialAmount/hits;
        }catch (ArithmeticException e){
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate(){
        if (hits<=0){
            throw new UnsupportedOperationException();
        }
        hits--;
    }

    public boolean isAlive(){
        return hits > 0;
    }

    public int getHits() {
        return hits;
    }
}
