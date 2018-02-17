package com.realgood.ml2program1.models;

import com.realgood.ml2program1.enums.Vector;

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

    public Vector getVector(Point p2) {
        int dx = p2.getX() - this.x;
        int dy = p2.getY() - this.y;
        if (dx == 1 && dy == 0) {
            return Vector.RIGHT;
        } else if (dx == -1 && dy == 0) {
            return Vector.LEFT;
        } else if (dy == -1 && dx == 0) {
            return Vector.DOWN;
        } else {
            return Vector.UP;
        }
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
