package com.realgood.ml2program1.models;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;

/**
 * Created by NicholasMoran on 2/11/18.
 */
public class ProteinSequence {
    private final String aminoAcids;
    private final int length;
    private final HashMap<Integer, AminoAcid> map;
    private final int fitness;

    public ProteinSequence(String aminoAcids, int fitness) {
        map = new HashMap<>();
        length = aminoAcids.length();
        for (int i = 0; i < length; i++) {
            if (aminoAcids.charAt(i) == 'h') {
                map.put(i, new HydrophobicAcid());
            } else if (aminoAcids.charAt(i) == 'p') {
                map.put(i, new PolarAcid());
            } else {
                System.out.println("Error somewhere Idk");
            }
        }
        this.aminoAcids = aminoAcids;
        this.fitness = fitness;
    }

    public AminoAcid getAcid(int index) {
        return map.get(index);
    }

    public void swapAcid(int index) {
        AminoAcid acid = map.get(index);
        if (acid instanceof HydrophobicAcid) {
            map.remove(index);
            map.put(index, new PolarAcid());
        } else if (acid instanceof PolarAcid) {
            map.remove(index);
            map.put(index, new HydrophobicAcid());
        } else {
            System.out.println("Error somewhere Idk");
        }
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i:map.keySet()) {
            temp += map.get(i);
        }
        return temp;
    }

    public int getLength() {
        return this.length;
    }

    public int getFitness() {
        return this.fitness;
    }
}
