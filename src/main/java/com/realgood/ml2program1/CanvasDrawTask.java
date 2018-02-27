package com.realgood.ml2program1;

import com.realgood.ml2program1.enums.Vector;
import com.realgood.ml2program1.models.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicReference;

public class CanvasDrawTask<T> extends AnimationTimer {

    private final AtomicReference<T> data = new AtomicReference<>(null);
    private final Canvas canvas;

    public CanvasDrawTask(Canvas canvas) {
        this.canvas = canvas;
    }

    public void requestRedraw(T structure) {
        data.set(structure);
        start();
    }

    @Override
    public void handle(long now) {
        T dataToDraw = data.getAndSet(null);
        if (dataToDraw != null) {
            //draw(canvas.getGraphicsContext2D(), (ProteinSequenceStructure) dataToDraw);
        }
    }

     final void draw(GraphicsContext gc, ProteinSequenceStructure structure, int generation) {
        gc.clearRect(0,0,600,600);
        drawInfo(gc, structure, generation);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        int length = structure.getStructureLength();
        PSNode start = structure.getNode(0);
        drawAcid(start.getPoint(), start.getAcid(), gc);
        Point current = start.getPoint();
        Point prev = null;
        for (int i = 0; i <= length; i++) {
            PSNode node = structure.getNode(i);
            PSNode next = structure.getNode(i+1);
            if (next != null) {
                if (node.getPoint().getVector(next.getPoint()) == Vector.RIGHT) {
                    prev = current;
                    current = new Point(current.getX() + 20, current.getY());
                    drawEdge(prev, current, gc);
                    drawAcid(current, next.getAcid(), gc);
                    drawAcid(prev, node.getAcid(), gc);
                } else if (node.getPoint().getVector(next.getPoint()) == Vector.LEFT) {
                    prev = current;
                    current = new Point(current.getX() - 20, current.getY());
                    drawEdge(prev, current, gc);
                    drawAcid(current, next.getAcid(), gc);
                    drawAcid(prev, node.getAcid(), gc);
                } else if (node.getPoint().getVector(next.getPoint()) == Vector.DOWN) {
                    prev = current;
                    current = new Point(current.getX(), current.getY() - 20);
                    drawEdge(prev, current, gc);
                    drawAcid(current, next.getAcid(), gc);
                    drawAcid(prev, node.getAcid(), gc);
                } else {
                    prev = current;
                    current = new Point(current.getX(), current.getY() + 20);
                    drawEdge(prev, current, gc);
                    drawAcid(current, next.getAcid(), gc);
                    drawAcid(prev, node.getAcid(), gc);
                }
            } else {
                System.out.println("WOO ODD NUMBAS!");
            }
        }
    }

    private void drawAcid(Point point, AminoAcid acid, GraphicsContext gc) {
        setFillColor(acid, gc);
        gc.fillRoundRect(point.getX() + 300, point.getY() + 300, 5, 5, 5, 5);
    }

    private void drawEdge(Point p1, Point p2, GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeLine(p1.getX() + 302, p1.getY() + 302, p2.getX() + 302, p2.getY() + 302);
    }

    private void setFillColor(AminoAcid acid, GraphicsContext gc) {
        if (acid instanceof HydrophobicAcid) {
            gc.setStroke(Color.RED);
            gc.setFill(Color.RED);
        } else {
            gc.setStroke(Color.BLUE);
            gc.setFill(Color.BLUE);
        }
    }

    private void drawInfo(GraphicsContext gc, ProteinSequenceStructure structure, int gen) {
        gc.setStroke(Color.BLACK);
        gc.strokeText(structure.toString(), 10,10);
        gc.strokeText("Best Structure from Generation: " + gen, 10, 25);
    }

    public void drawFinished(GraphicsContext gc, ProteinSequenceStructure structure) {
        draw(gc, structure, 0);
    }
}
