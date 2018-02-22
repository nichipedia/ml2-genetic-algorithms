package com.realgood.ml2program1.models;

import com.realgood.ml2program1.enums.Vector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Created by NicholasMoran on 2/14/18.
 */
public class ProteinSequenceStructure implements Comparable<ProteinSequenceStructure> {
    private final ProteinSequence sequence;
    private final LinkedList<PSNode> structure;
    private final int length;
    private final Random rando;
    private final int fitness;
    private final Set<Point> visited;

    public ProteinSequenceStructure(ProteinSequence sequence) {
        this.sequence = sequence;
        this.rando = new Random();
        this.visited = new HashSet<>();
        this.structure = selfAvoidingWalk();
        this.length = sequence.getLength();
        this.fitness = evaluate();
    }

    public ProteinSequenceStructure(ProteinSequenceStructure frontSection, ProteinSequenceStructure backSection, ProteinSequence sequence) {
        this.sequence = sequence;
        this.rando = new Random();
        this.visited = new HashSet<>();
        this.length = sequence.getLength();
        this.structure = sawCross(frontSection, backSection);
        this.fitness = evaluate();
    }

    public int getFitness() {
        return this.fitness;
    }

    private int evaluate() {
        int fitness = 0;
        PSNode[][] graph = new PSNode[600][600];
        for (PSNode node:structure) {
            graph[node.getPoint().getY()+300][node.getPoint().getX()+300] = node;
        }
        for (int y = 0; y < 600; y++) {
            for (int x = 0; x < 600; x++) {
                if (graph[y][x] != null && graph[y][x].getAcid() instanceof HydrophobicAcid) {
                    if (graph[y+1][x] != null && !connected(graph[y][x], graph[y+1][x]) && typeOfPoint(graph[y+1][x].getPoint()) instanceof HydrophobicAcid) {
                        fitness++;
                    }
                    if (graph[y][x+1] != null && !connected(graph[y][x], graph[y][x+1]) && typeOfPoint(graph[y][x+1].getPoint()) instanceof HydrophobicAcid) {
                        fitness++;
                    }
                }
            }
        }
        return fitness;
    }

    private AminoAcid typeOfPoint(Point point) {
        for (PSNode node:structure) {
            if (node.getPoint().equals(point)) {
                return node.getAcid();
            }
        }
        return null;
    }

    private boolean connected(PSNode first, PSNode second) {
        int i = 0;
        while (i<structure.size()) {
            PSNode temp = structure.get(i);
            if (first.getPoint().equals(temp.getPoint())) {
                PSNode prev = getNode(i-1);
                PSNode next = getNode(i+1);
                if (prev != null) {
                    if (prev.getPoint().equals(second.getPoint())) {
                        return true;
                    }
                }
                if (next != null) {
                    if (next.getPoint().equals(second.getPoint())) {
                        return true;
                    }
                }
            }
            i++;
        }
        return false;
    }


    private LinkedList<PSNode> sawCross(ProteinSequenceStructure mother, ProteinSequenceStructure father) {
        LinkedList<PSNode> temp = new LinkedList<>();
        LinkedList<PSNode> sect1 = new LinkedList<>();
        LinkedList<PSNode> sect2 = new LinkedList<>();
        int max = this.sequence.getLength();
        for (int i = 0; i < max; i++) {
            int index = rando.nextInt(max);

            for (int j = 0; j < index; j++) {
                sect1.add(mother.getNode(j));
            }
            for (int j = index; j < max; j++) {
                sect2.add(father.getNode(j));
            }
            if (validCross(sect1, sect2)) {
                for (PSNode node : sect1) {
                    temp.addLast(node);
                }
                for (PSNode node : sect2) {
                    temp.addLast(node);
                }
                return temp;
            }
            sect1.clear();
            sect2.clear();
        }
        return selfAvoidingWalk();
    }

    private boolean validCross(LinkedList<PSNode> sect1, LinkedList<PSNode> sect2) {
        for (PSNode first:sect1) {
            for (PSNode second:sect2) {
                if (first.getPoint().equals(second.getPoint())) {
                    return false;
                }
            }
        }
        return true;
    }

//    private Set<Point> generateVisited(LinkedList<PSNode> frontSection, LinkedList<PSNode> backSection) {
//        Set<Point> temp = new HashSet<>();
//        for (PSNode node:frontSection) {
//            temp.add(node.getPoint());
//        }
//        for (PSNode node:backSection) {
//            temp.add(node.getPoint());
//        }
//        return temp;
//    }

    public int getStructureLength() {
        return this.length;
    }

    private LinkedList<PSNode> selfAvoidingWalk() {
        LinkedList<PSNode> tempStruct = new LinkedList<>();
        AminoAcid acid = this.sequence.getAcid(0);
        PSNode first = new PSNode(new Point(0,0), acid, 0);
        acid = this.sequence.getAcid(1);
        PSNode node = new PSNode(new Point(1,0), acid, 1);
        tempStruct.add(first);
        tempStruct.addLast(node);
        visited.add(new Point(1,0));
        visited.add(new Point(0,0));
        int i = 2;
        Point current = new Point(1, 0);
        while (i < sequence.getLength()) {
            if (movePossible(current)) {
                Point next = chooseNextMove(current);
                acid = sequence.getAcid(i++);
                node = new PSNode(next, acid, i);
                tempStruct.addLast(node);
                this.visited.add(next);
                current = next;
            } else {
                while (!movePossible(current)) {
                    i--;
                    PSNode temp = tempStruct.removeLast();
                    temp = tempStruct.getLast();
                    current = temp.getPoint();
                }
                pruneVisited(tempStruct);
            }
        }

        return tempStruct;
    }

    public PSNode getNode(int step) {
        if (step < this.length && step >= 0) {
            return this.structure.get(step);
        }
        return null;
    }

    private void pruneVisited(LinkedList<PSNode> tempStruct) {
        visited.clear();
        for (PSNode node:tempStruct) {
            visited.add(node.getPoint());
        }
    }


    private Point chooseNextMove(Point current) {
        Point next = null;
        int eval = rando.nextInt(4);
        if (eval == 0) {
            next = new Point(current.getX() + 1, current.getY());
        } else if (eval == 1) {
            next = new Point(current.getX() - 1, current.getY());
        } else if (eval == 2) {
            next = new Point(current.getX(), current.getY() + 1);
        } else {
            next = new Point(current.getX(), current.getY() - 1);
        }
        if (!visited(next)) {
            return next;
        } else {
            return chooseNextMove(current);
        }
    }



    private boolean movePossible(Point current) {
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
            Point point = node.getPoint();
            temp += "X: " + point.getX() + ", Y: " + point.getY() + "\n";
        }
        return temp;
    }

    @Override
    public int compareTo(ProteinSequenceStructure o) {
        return o.getFitness() - this.fitness;
    }
}
