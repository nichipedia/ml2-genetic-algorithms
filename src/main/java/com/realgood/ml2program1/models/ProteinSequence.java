package com.realgood.ml2program1.models;

import java.util.HashMap;
/*******************************************
 * <h1>Main class for Canvas app            *
 * Class is the intro to the canvas app     *
 * @author Nicholas Moran                   *
 * @version 1.0                             *
 *                                          *
 *******************************************/
public class ProteinSequence {
    private final String aminoAcids;
    private final int length;
    private final HashMap<Integer, AminoAcid> map;
    private final int fitness;

    public ProteinSequence(String aminoAcids, int fitness) {
        map = new HashMap<>();
        length = aminoAcids.length();
        for (int i = 0; i < length; i++) {
            if (aminoAcids.charAt(i) == 'h' || aminoAcids.charAt(i) == 'H') {
                map.put(i, new HydrophobicAcid());
            } else if (aminoAcids.charAt(i) == 'p' || aminoAcids.charAt(i) == 'P') {
                map.put(i, new PolarAcid());
            } else {
                System.out.println("Error somewhere while creating protein sequence Idk");
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
            System.out.println("Error somewhere while swaping a protein Idk");
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
