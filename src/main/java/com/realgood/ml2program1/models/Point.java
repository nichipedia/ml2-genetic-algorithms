package com.realgood.ml2program1.models;

/**
 * Created by NicholasMoran on 2/13/18.
 */
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point) o;
            return (p.getX() == this.x && p.getY() == this.y);
        }
        return false;
    }
}
