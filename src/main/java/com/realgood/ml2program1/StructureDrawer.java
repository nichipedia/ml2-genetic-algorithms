package com.realgood.ml2program1;

import com.realgood.ml2program1.enums.Vector;
import com.realgood.ml2program1.models.*;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * Created by NicholasMoran on 2/25/18.
 */
public class StructureDrawer implements Runnable {

    private final ProteinSequence sequence;
    private final GraphicsContext gc;

    public StructureDrawer(ProteinSequence sequence, GraphicsContext graphicsContext) {
        this.sequence = sequence;
        this.gc = graphicsContext;
    }

    @Override
    public void run() {
        ProteinSequenceStructure[] repo = new ProteinSequenceStructure[200];
        ProteinSequenceStructure[] nextGen = new ProteinSequenceStructure[200];
        for(int i = 0; i < 200; i++) {
            repo[i] = new ProteinSequenceStructure(sequence);
        }
        Arrays.sort(repo);

        drawStructure(repo[0]);


        for (int i = 0; i < 2000 && repo[0].getFitness() < -1*sequence.getFitness(); i++) {
            for (int j = 0; j < 10; j++) {
                nextGen[j] = repo[j];
            }

            for (int j = 10; j < 200; j++) {
                //ProteinSequenceStructure father = rws(repo);
                //ProteinSequenceStructure mother = rws(repo);
                //nextGen[j] = new ProteinSequenceStructure(mother, father, sequence);
                nextGen[j] = new ProteinSequenceStructure(sequence);
            }

            repo = nextGen;

            Arrays.sort(repo);

            //gc.clearRect(0,0,600,600);

            drawStructure(repo[0]);
        }
        //drawStructure(repo);
    }

    private void drawStructure(ProteinSequenceStructure structure) {
        //Affine aff = new Affine(1,0, 50, 0, 1, 50);
        //gc.transform(aff);
        gc.clearRect(0,0,600,600);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        int length = structure.getStructureLength();
        PSNode start = structure.getNode(0);
        drawAcid(start.getPoint(), start.getAcid());
        Point current = start.getPoint();
        Point prev = null;
        for (int i = 0; i <= length; i++) {
            PSNode node = structure.getNode(i);
            PSNode next = structure.getNode(i+1);
            if (next != null) {
                if (node.getPoint().getVector(next.getPoint()) == Vector.RIGHT) {
                    prev = current;
                    current = new Point(current.getX() + 20, current.getY());
                    drawEdge(prev, current);
                    drawAcid(current, next.getAcid());
                    drawAcid(prev, node.getAcid());
                } else if (node.getPoint().getVector(next.getPoint()) == Vector.LEFT) {
                    prev = current;
                    current = new Point(current.getX() - 20, current.getY());
                    drawEdge(prev, current);
                    drawAcid(current, next.getAcid());
                    drawAcid(prev, node.getAcid());
                } else if (node.getPoint().getVector(next.getPoint()) == Vector.DOWN) {
                    prev = current;
                    current = new Point(current.getX(), current.getY() - 20);
                    drawEdge(prev, current);
                    drawAcid(current, next.getAcid());
                    drawAcid(prev, node.getAcid());
                } else {
                    prev = current;
                    current = new Point(current.getX(), current.getY() + 20);
                    drawEdge(prev, current);
                    drawAcid(current, next.getAcid());
                    drawAcid(prev, node.getAcid());
                }
            } else {
                System.out.println("WOO ODD NUMBAS!");
            }
        }
    }

    private void drawAcid(Point point, AminoAcid acid) {
        setFillColor(acid);
        gc.fillRoundRect(point.getX() + 300, point.getY() + 300, 5, 5, 5, 5);
    }

    private void drawEdge(Point p1, Point p2) {
        this.gc.setStroke(Color.BLACK);
        gc.strokeLine(p1.getX() + 302, p1.getY() + 302, p2.getX() + 302, p2.getY() + 302);
    }

    private void setFillColor(AminoAcid acid) {
        if (acid instanceof HydrophobicAcid) {
            this.gc.setStroke(Color.RED);
            this.gc.setFill(Color.RED);
        } else {
            this.gc.setStroke(Color.BLUE);
            this.gc.setFill(Color.BLUE);
        }
    }
}
