/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequencealignment;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author SHOAIB
 */
public class SequenceAlignment extends Application {
     
    GridPane root;
    private Cell[][] matrix;
    private String seqA;
    private String seqB;
    private int match, mismatch, gap;
    GridPane pane;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        root = FXMLLoader.load(getClass().getResource("MatrixFXML.fxml"));        
        
        Scene scene = new Scene(root, 460, 530);
        
        TextField seqATextField = (TextField) scene.lookup("#seqA");
        TextField seqBTextField = (TextField) scene.lookup("#seqB");
        TextField fieldMatch = (TextField) scene.lookup("#match");        
        TextField fieldMismatch = (TextField) scene.lookup("#mismatch");
        TextField fieldGap = (TextField) scene.lookup("#gap");
        fieldMatch.setText("1");
        fieldMismatch.setText("-1");
        fieldGap.setText("-2");

        
        Button matrixButton = (Button) scene.lookup("#createMatrix");
        
        matrixButton.setOnAction(event -> {
           System.out.println("Button Clicked");
           seqA = seqATextField.getText().toUpperCase();
           seqB = seqBTextField.getText().toUpperCase();
           
           if((fieldMatch.getText().length() != 0) 
                   && (fieldMismatch.getText().length() != 0) 
                   && (fieldGap.getText().length() != 0)){
                match = Integer.parseInt(fieldMatch.getText());
                mismatch = Integer.parseInt(fieldMismatch.getText());
                gap = Integer.parseInt(fieldGap.getText());
                
                System.out.println(mismatch);
           }else{
               gap = 1;
               mismatch = -1;
               gap = -2;
           }
           
           if(seqA.length() != 0 && seqB.length() != 0 && seqA.length() >= seqB.length()){                   
               createMatrix();               
           }   
        });
        
        primaryStage.setTitle("Sequence Alignment");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public void createMatrix(){
        
        Matrix matrix = new Matrix(seqA, seqB);
        matrix.setMatch(match);
        matrix.setMismatch(mismatch);
        matrix.setGap(gap);
        
        matrix.initializeMatrix();

        this.matrix = matrix.getMatrix();

        System.out.println();

        matrix.fillScoreMatrix();
        
        this.matrix = matrix.getMatrix();
        printMatrixOnConsole();

        System.out.println();
        System.out.println("Matrix Filled");

        matrix.matrixTraceback();

        this.matrix = matrix.getMatrix();
        
        String[] sequences = matrix.getAlignedSequences();
        
        int trow = this.matrix.length-1;
        int tcol = this.matrix[trow].length-1;
        int score = this.matrix[trow][tcol].getValue();
        Table table;
        table = new Table(this.matrix, seqA, seqB, sequences, score);
        table.showTable();
        
        System.out.println(sequences[0]);
        System.out.println(sequences[1]);
        
//        showMatrix();

    }
    
    public void printMatrixOnConsole(){
        for (int row = 0; row < this.matrix.length; row++) {
            for (int col = 0; col < this.matrix[row].length; col++) {
                if(this.matrix[row][col] != null){
                    int value = matrix[row][col].getValue();
                    System.out.print(value + " ");
                } else {
                    System.out.print(0 + " ");
                }

            }
            System.out.println();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
