package com.realgood.ml2program1;

import com.realgood.ml2program1.enums.Vector;
import com.realgood.ml2program1.models.*;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;


import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by NicholasMoran on 2/25/18.
 */
public class StructureDrawer implements Runnable {

    private final ProteinSequence sequence;
    private final Canvas canvas;
    private boolean run;

    public StructureDrawer(ProteinSequence sequence, Canvas canvas) {
        this.sequence = sequence;
        this.canvas = canvas;
        this.run = true;
    }

    public boolean running() {
        return run;
    }

    @Override
    public void run() {
        ProteinSequenceStructure[] repo = new ProteinSequenceStructure[200];
        ProteinSequenceStructure[] nextGen = new ProteinSequenceStructure[200];
        for(int i = 0; i < 200; i++) {
            repo[i] = new ProteinSequenceStructure(sequence);
        }
        Arrays.sort(repo);

        CanvasDrawTask<ProteinSequenceStructure> drawer = new CanvasDrawTask<>(canvas);
        for (int i = 0; i < 2000 && repo[0].getFitness() < -1*sequence.getFitness(); i++) {
            for (int j = 0; j < 10; j++) {
                nextGen[j] = repo[j];
            }

            for (int j = 10; j < 200; j++) {
                ProteinSequenceStructure father = rws(repo);
                ProteinSequenceStructure mother = rws(repo);
                nextGen[j] = new ProteinSequenceStructure(mother, father, sequence);
            }

            repo = nextGen;
            Arrays.sort(repo);
            final ProteinSequenceStructure best = repo[0];
            final int generation = i;
            Platform.runLater(() -> drawer.draw(canvas.getGraphicsContext2D(), best, generation));
            //drawer.requestRedraw(repo[0], i);
            //gc.clearRect(0,0,600,600);

            //drawStructure(repo[0]);
        }
        run = false;
    }

    private ProteinSequenceStructure rws(ProteinSequenceStructure[] structs) {
        int sum = 0;
        int partial = 0;
        for (ProteinSequenceStructure struct:structs) {
            sum += struct.getFitness();
        }
        Random rando = new Random();
        int bound = rando.nextInt(sum);

        for (int i = 0; i < structs.length; i++) {
            partial += structs[i].getFitness();
            if (partial > bound) {
                return structs[i];
            }
        }
        return null;
    }

//    private void drawStructure(ProteinSequenceStructure structure) {
//        //Affine aff = new Affine(1,0, 50, 0, 1, 50);
//        //gc.transform(aff);
//        gc.clearRect(0,0,600,600);
//        gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        int length = structure.getStructureLength();
//        PSNode start = structure.getNode(0);
//        drawAcid(start.getPoint(), start.getAcid());
//        Point current = start.getPoint();
//        Point prev = null;
//        for (int i = 0; i <= length; i++) {
//            PSNode node = structure.getNode(i);
//            PSNode next = structure.getNode(i+1);
//            if (next != null) {
//                if (node.getPoint().getVector(next.getPoint()) == Vector.RIGHT) {
//                    prev = current;
//                    current = new Point(current.getX() + 20, current.getY());
//                    drawEdge(prev, current);
//                    drawAcid(current, next.getAcid());
//                    drawAcid(prev, node.getAcid());
//                } else if (node.getPoint().getVector(next.getPoint()) == Vector.LEFT) {
//                    prev = current;
//                    current = new Point(current.getX() - 20, current.getY());
//                    drawEdge(prev, current);
//                    drawAcid(current, next.getAcid());
//                    drawAcid(prev, node.getAcid());
//                } else if (node.getPoint().getVector(next.getPoint()) == Vector.DOWN) {
//                    prev = current;
//                    current = new Point(current.getX(), current.getY() - 20);
//                    drawEdge(prev, current);
//                    drawAcid(current, next.getAcid());
//                    drawAcid(prev, node.getAcid());
//                } else {
//                    prev = current;
//                    current = new Point(current.getX(), current.getY() + 20);
//                    drawEdge(prev, current);
//                    drawAcid(current, next.getAcid());
//                    drawAcid(prev, node.getAcid());
//                }
//            } else {
//                System.out.println("WOO ODD NUMBAS!");
//            }
//        }
//    }
//
//    private void drawAcid(Point point, AminoAcid acid) {
//        setFillColor(acid);
//        gc.fillRoundRect(point.getX() + 300, point.getY() + 300, 5, 5, 5, 5);
//    }
//
//    private void drawEdge(Point p1, Point p2) {
//        this.gc.setStroke(Color.BLACK);
//        gc.strokeLine(p1.getX() + 302, p1.getY() + 302, p2.getX() + 302, p2.getY() + 302);
//    }
//
//    private void setFillColor(AminoAcid acid) {
//        if (acid instanceof HydrophobicAcid) {
//            this.gc.setStroke(Color.RED);
//            this.gc.setFill(Color.RED);
//        } else {
//            this.gc.setStroke(Color.BLUE);
//            this.gc.setFill(Color.BLUE);
//        }
//    }
}
