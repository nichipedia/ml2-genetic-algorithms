package com.realgood.ml2program1.models;

import com.realgood.ml2program1.enums.Vector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Created by NicholasMoran on 2/14/18.
 */
public class ProteinSequenceStructure {
    private final ProteinSequence sequence;
    private final LinkedList<PSNode> structure;
    private final int length;
    private final Random rando;
    private final Set<Point> visited;

    public ProteinSequenceStructure(ProteinSequence sequence) {
        this.sequence = sequence;
        this.rando = new Random();
        this.visited = new HashSet<>();
        this.structure = selfAvoidingWalk();
        this.length = sequence.getLength();
    }

    public int getStructureLength() {
        return this.length;
    }

    public LinkedList<PSNode> getStructure() {
        return this.structure;
    }

    private LinkedList<PSNode> selfAvoidingWalk() {
        LinkedList<PSNode> tempStruct = new LinkedList<>();
        AminoAcid acid = this.sequence.getAcid(0);
        PSNode first = new PSNode(0, 0, acid, 0);
        first.setPrevDirection(null);
        first.setNextDirection(Vector.LEFT);
        acid = this.sequence.getAcid(1);
        PSNode node = new PSNode(1, 0, acid, 1);
        node.setPrevDirection(Vector.LEFT);
        tempStruct.add(first);
        tempStruct.addLast(node);
        visited.add(new Point(1,0));
        visited.add(new Point(0,0));
        int i = 2;
        Point current = new Point(0, 1);
        while (i < sequence.getLength()) {
            if (movePossible(current)) {
                Vector direction = chooseNextMove(current);
                acid = sequence.getAcid(i++);
                Point next = getNextPoint(direction, current);
                node = new PSNode(next.getX(), next.getY(), acid, i);
                tempStruct.addLast(node);
                this.visited.add(next);
                current = next;
            } else {
                while (!movePossible(current)) {
                    i--;
                    PSNode temp = tempStruct.removeLast();
                    temp = tempStruct.getLast();
                    current = new Point(temp.getX(), temp.getY());
                    Vector direction = chooseNextMove(current);
                    Point next = getNextPoint(direction, current);
                    acid = sequence.getAcid(i++);
                    node = new PSNode(next.getX(), next.getY(), acid, i);
                    tempStruct.addLast(node);
                    this.visited.add(next);
                    current = next;
                }
            }
        }

        return tempStruct;
    }



    public Vector chooseNextMove(Point current) {
        Point next = null;
        Vector direction = null;
        int eval = rando.nextInt(4);
        if (eval == 0) {
            next = new Point(current.getX() + 1, current.getY());
            direction = Vector.RIGHT;
        } else if (eval == 1) {
            next = new Point(current.getX() - 1, current.getY());
            direction = Vector.LEFT;
        } else if (eval == 2) {
            next = new Point(current.getX(), current.getY() + 1);
            direction = Vector.UP;
        } else {
            next = new Point(current.getX(), current.getY() - 1);
            direction = Vector.DOWN;
        }
        if (!visited(next)) {
            return direction;
        } else {
            return chooseNextMove(current);
        }
    }

    public Point getNextPoint(Vector direction, Point current) {
        if (direction == Vector.UP) {
            return new Point(current.getX(), current.getY() + 1);
        } else if (direction == Vector.DOWN) {
            return new Point(current.getX(), current.getY() - 1);
        } else if (direction == Vector.LEFT) {
            return new Point(current.getX() - 1, current.getY());
        } else {
            return new Point(current.getX() +1, current.getY());
        }
    }

    public boolean movePossible(Point current) {
        Point left = new Point(current.getX() - 1, current.getY());
        Point down = new Point(current.getX(), current.getY() - 1);
        Point right = new Point(current.getX() + 1, current.getY());
        Point up = new Point(current.getX(), current.getY() + 1);
        return !(visited(left) && visited(right) && visited(down) && visited(up));
    }

    private int getRandom() {
        return rando.nextInt(1 + 1 + 1) - 1;
    }

    private boolean visited(Point point) {
        for (Point suspect:visited) {
            if (point.equals(suspect)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String temp = "";
        for (PSNode node:structure) {
            temp += "X: " + node.getX() + ", Y: " + node.getY() + "\n";
        }
        return temp;
    }
}
