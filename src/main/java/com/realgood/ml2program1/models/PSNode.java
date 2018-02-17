package com.realgood.ml2program1.models;

import com.realgood.ml2program1.enums.Vector;

/**
 * Created by NicholasMoran on 2/14/18.
 */
public class PSNode {
    private final Point point;
    private final AminoAcid acid;
    private final int step;


    public PSNode(Point point, AminoAcid acid, int step) {
        this.point = point;
        this.acid = acid;
        this.step = step;
    }

    public Point getPoint() {
        return this.point;
    }

    public AminoAcid getAcid() {
        return this.acid;
    }

    public int getStep() {
        return this.step;
    }
}
