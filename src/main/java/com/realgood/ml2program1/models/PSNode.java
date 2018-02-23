package com.realgood.ml2program1.models;

import com.realgood.ml2program1.enums.Vector;
/*******************************************
 * <h1>PSNode</h1>                          *
 * Model representation of a point on a graph
 * @author Nicholas Moran                   *
 * @version 1.0                             *
 *                                          *
 *******************************************/
public class PSNode {
    private final Point point;
    private final AminoAcid acid;
    private final int step;

    /***
     * Constructor for a PSNode
     * @param point
     * @param acid
     * @param step
     */
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
