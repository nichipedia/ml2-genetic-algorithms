package com.realgood.ml2program1.models;

/**
 * Created by NicholasMoran on 2/14/18.
 */
public class PSNode {
    private final int x;
    private final int y;
    private final AminoAcid acid;

    public PSNode(int x, int y, AminoAcid acid) {
        this.x = x;
        this.y = y;
        this.acid = acid;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public AminoAcid getAcid() {
        return this.acid;
    }
}
