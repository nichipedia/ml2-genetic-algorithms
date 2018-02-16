package com.realgood.ml2program1.models;

import com.realgood.ml2program1.enums.Vector;

/**
 * Created by NicholasMoran on 2/14/18.
 */
public class PSNode {
    private final int x;
    private final int y;
    private final AminoAcid acid;
    private final int step;
    private Vector nextDirection;
    private Vector prevDirection;


    public PSNode(int x, int y, AminoAcid acid, int step) {
        this.x = x;
        this.y = y;
        this.acid = acid;
        this.step = step;
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

    public Vector getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Vector nextDirection) {
        this.nextDirection = nextDirection;
    }

    public Vector getPrevDirection() {
        return prevDirection;
    }

    public void setPrevDirection(Vector prevDirection) {
        this.prevDirection = prevDirection;
    }
}
