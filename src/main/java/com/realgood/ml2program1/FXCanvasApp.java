package com.realgood.ml2program1;

import com.realgood.ml2program1.models.PSNode;
import com.realgood.ml2program1.models.Point;
import com.realgood.ml2program1.models.ProteinSequence;
import com.realgood.ml2program1.models.ProteinSequenceStructure;
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
        canvas = new Canvas(300, 300);
        //canvas.setScaleY(2);
        //canvas.setScaleX(2);
        canvas.setTranslateX(50);
        canvas.setTranslateY(50);
        gc = canvas.getGraphicsContext2D();

        ProteinSequenceParser repo = new ProteinSequenceParser("/Users/NicholasMoran/Downloads/input.txt");
        for (ProteinSequence sequence:repo.getProteinSequences()) {
            ProteinSequenceStructure struct = new ProteinSequenceStructure(sequence);
            drawStructure(struct);
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
        for (PSNode node:structure.getStructure()) {
            drawAcid(new Point(node.getX(), node.getY()));
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

    private void drawAcid(Point point) {
        gc.strokeRoundRect(point.getX() + 150, point.getY() + 150, 2, 2, 2, 2);
    }

    private void moveCanvas(int x, int y) {
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
    }
}
