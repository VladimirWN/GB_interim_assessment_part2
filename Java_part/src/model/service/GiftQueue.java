package model.service;

import model.data.Toy;

import java.util.*;


public class GiftQueue {

    private ArrayDeque<Toy> currentQueue;

    public GiftQueue() {
        this.currentQueue = new ArrayDeque<Toy>();
    }

    public ArrayDeque<Toy> getCurrentQueue() {
        return currentQueue;
    }

    public Toy fillingQueue(List<Toy> toys) {
        Random rnd = new Random();
        Map<Integer, Integer> idAndChance = new HashMap<>();
        for (Toy item :
                toys) {
            idAndChance.put(item.getToyID(), (int) (item.getDropChance() * 100));
        }

        int currentPoint = 0;
        for (Map.Entry<Integer, Integer> item :
                idAndChance.entrySet()) {
            idAndChance.put(item.getKey(), item.getValue() + currentPoint);
            currentPoint = idAndChance.get(item.getKey());
        }

        int rndNumber = rnd.nextInt(currentPoint + 1);
        for (Map.Entry<Integer, Integer> item :
                idAndChance.entrySet()) {
            if (rndNumber <= item.getValue()) {
                for (Toy toy :
                        toys) {
                    if (item.getKey() == toy.getToyID()) {
                        this.currentQueue.push(toy);
                        return toy;
                    }
                }
            }
        }
        return null;
    }

    public Toy getPrize() {
        return this.currentQueue.pollLast();
    }
}
