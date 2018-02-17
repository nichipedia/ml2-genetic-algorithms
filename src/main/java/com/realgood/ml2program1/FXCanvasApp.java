package com.realgood.ml2program1;

import com.realgood.ml2program1.enums.Vector;
import com.realgood.ml2program1.models.*;
import com.realgood.ml2program1.parser.ProteinSequenceParser;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * Created by NicholasMoran on 2/15/18.
 */
public class FXCanvasApp extends Application {

    private Canvas canvas;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        canvas = new Canvas(600, 600);
        //canvas.setScaleY(2);
        //canvas.setScaleX(2);
        canvas.setTranslateX(50);
        canvas.setTranslateY(50);
        gc = canvas.getGraphicsContext2D();

        ProteinSequenceParser repo = new ProteinSequenceParser("/Users/NicholasMoran/Downloads/input.txt");
        for (ProteinSequence sequence:repo.getProteinSequences()) {
            ProteinSequenceStructure struct = new ProteinSequenceStructure(sequence);
            drawStructure(struct);
            System.out.println(struct);
        }
        //drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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

    private void drawShapes(GraphicsContext gc) {
        //moveCanvas(150, 150);
//        gc.setLineWidth(5);
          //gc.strokeLine(0, 0, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
    }

    private void drawAcid(Point point, AminoAcid acid) {
        setFillColor(acid);
        gc.fillRoundRect(point.getX() + 300, point.getY() + 300, 5, 5, 5, 5);
    }

    private void drawEdge(Point p1, Point p2) {
        this.gc.setStroke(Color.BLACK);
        gc.strokeLine(p1.getX() + 300, p1.getY() + 300, p2.getX() + 300, p2.getY() + 300);
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

    private void moveCanvas(int x, int y) {
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
    }
}
