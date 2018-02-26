package com.realgood.ml2program1;

import com.realgood.ml2program1.enums.Vector;
import com.realgood.ml2program1.models.*;
import com.realgood.ml2program1.parser.ProteinSequenceParser;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by NicholasMoran on 2/15/18.
 */
public class FXCanvasApp extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Label fitnessValue;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Program 1");
        Group root = new Group();
        canvas = new Canvas(600, 600);
        //canvas.setScaleY(2);
        //canvas.setScaleX(2);
        canvas.setTranslateX(50);
        canvas.setTranslateY(50);
        gc = canvas.getGraphicsContext2D();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        Label fitnessLabel = new Label("Fitness: ");
        fitnessValue = new Label();
        grid.add(fitnessLabel, 0, 1);
        grid.add(fitnessValue, 1,1);
        root.getChildren().add(grid);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ProteinSequenceParser parser = new ProteinSequenceParser("C:\\Users\\nmoran\\Desktop\\junk\\input.txt");
        for (ProteinSequence sequence:parser.getProteinSequences()) {
            ProteinSequenceStructure[] repo = new ProteinSequenceStructure[200];
            ProteinSequenceStructure[] nextGen = new ProteinSequenceStructure[200];
            for(int i = 0; i < 200; i++) {
                repo[i] = new ProteinSequenceStructure(sequence);
            }
            Arrays.sort(repo);

            Platform.runLater(new StructureDrawer(repo[0], gc));
            //drawStructure(repo[0]);

            for (int i = 0; i < 2000 && repo[0].getFitness() < 9; i++) {
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
                Platform.runLater(new StructureDrawer(repo[0], gc));
                //drawStructure(repo[0]);
            }


        }
        //drawShapes(gc);


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

    private void drawStructure(ProteinSequenceStructure structure) {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Affine aff = new Affine(1,0, 50, 0, 1, 50);
                //gc.transform(aff);
                gc.setFill(Color.GREEN);
                gc.setStroke(Color.BLUE);
                int length = structure.getStructureLength();
                PSNode start = structure.getNode(0);

                drawAcid(start.getPoint(), start.

                        getAcid());
                Point current = start.getPoint();
                Point prev = null;
                for (
                        int i = 0;
                        i <= length; i++)

                {
                    PSNode node = structure.getNode(i);
                    PSNode next = structure.getNode(i + 1);
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
        }.start();
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

    private void moveCanvas(int x, int y) {
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
    }
}
