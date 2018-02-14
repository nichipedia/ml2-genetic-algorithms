package com.realgood.ml2program1.models;

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
    private final Random rando;
    private final Set<Point> visited;

    public ProteinSequenceStructure(ProteinSequence sequence) {
        this.sequence = sequence;
        this.rando = new Random();
        this.visited = new HashSet<>();
        this.structure = selfAvoidingWalk();
    }

    private LinkedList<PSNode> selfAvoidingWalk() {
        LinkedList<PSNode> tempStruct = new LinkedList<>();
        AminoAcid acid = this.sequence.getAcid(0);
        PSNode first = new PSNode(0, 0, acid);
        acid = this.sequence.getAcid(1);
        PSNode node = new PSNode(1, 0, acid);
        tempStruct.add(first);
        tempStruct.add(node);
        visited.add(new Point(1,0));
        visited.add(new Point(0,0));
        int i = 2;
        Point current = new Point(0, 1);
        while (i < sequence.getLength()) {
            if (movePossible(current)) {
                Point next = chooseNextMove(current);
                acid = sequence.getAcid(i++);
                node = new PSNode(next.getX(), next.getY(), acid);
                this.structure.addLast(node);
                this.visited.add(next);
            } else {
                while (!movePossible(current)) {
                    i--;
                    PSNode temp = this.structure.removeLast();
                    temp = this.structure.getLast();
                    Point next = chooseNextMove(current);
                    acid = sequence.getAcid(i++);
                    node = new PSNode(next.getX(), next.getY(), acid);
                    this.structure.addLast(node);
                    this.visited.add(next);
                }
            }

        }

        return tempStruct;
    }

    public Point chooseNextMove(Point current) {
        Point next = null;
        if (rando.nextBoolean()) {
            int newX = current.getX() + getRandom();
            next = new Point(newX, current.getY());
        } else {
            int newY = current.getX() + getRandom();
            next = new Point(current.getX(), newY);
        }
        if (!visited(next)) {
            return new Point(next.getX(), next.getY());
        } else {
            return chooseNextMove(current);
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
}
