package com.realgood.ml2program1;

import com.realgood.ml2program1.enums.Vector;
import com.realgood.ml2program1.models.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by NicholasMoran on 2/25/18.
 */
public class StructureDrawer implements Runnable {

    private final ProteinSequenceStructure struct;
    private final GraphicsContext gc;

    public StructureDrawer(ProteinSequenceStructure structure, GraphicsContext graphicsContext) {
        this.struct = structure;
        this.gc = graphicsContext;
    }

    @Override
    public void run() {
        gc.clearRect(0,0,600,600);
        drawStructure(struct);
    }

    private void drawStructure(ProteinSequenceStructure structure) {
        //Affine aff = new Affine(1,0, 50, 0, 1, 50);
        //gc.transform(aff);
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
