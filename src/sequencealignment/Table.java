/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequencealignment;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author SHOAIB
 */
public class Table {
    
    
    private Cell[][] matrix;
    private String seqA;
    private String seqB;
    private String[] alignedSequences;
    private int score;
    
    Table(Cell[][] matrix, String seqA, String seqB, String[] alignedSequences, int score){
        this.matrix = matrix;
        this.seqA = seqA;
        this.seqB = seqB;
        this.alignedSequences = alignedSequences;
        this.score = score;
    }
    
    private String generateHTML(){
        String html = null;
        
        html = "<html>";
        html += "<head>";
        html += "<style>";
        html += "html{ background-color:#fbfbfb;margin: 5px auto;text-align:center;font-family: 'Century Gothic';margin:auto;} "
                + "td{ width:50px;height:55px;padding:5px;font-size:24px;text-align:center;background-color:#b0b5b5;color:#222830;} "
                + "th{ width:50px;height:55px;font-size:28px;font-weight:bold;text-align:center;background-color:#222830;color:#fff;} "
                + ".value{ width:50px;height:55px;margin:10px 5px;padding:5px;font-size:24px;text-align:center;background-color:#222830;color:#fff; }"
                + "div{ margin: 10px 0px; text-align:center; }"
                + "h1, h2{text-align:left;}";      
        html += "</style>";
        html += "</head>";
        html +=   "<body>";
        html +=       "<table>";
        html +=           "<thead>";
        html +=              "<th></th><th></th>";
        for(char ch : this.seqB.toCharArray()){
            html += "<th>";
            html +=   ch;
            html += "</th>";
        }
        html += "</thead>";
        html +=  "<tbody>";
        
        int i = 0;
        for(Cell[] array : this.matrix){
            
            html += "<tr>";
            if(i == 0){
                html += "<th>";
                html += "</th>";   
            } else {
                html += "<th>";
                html += this.seqA.charAt(i-1);
                html += "</th>";
            }
            
            for(Cell e : array){
                if(e.isFlag()){
                    html += "<th>";
                    html += e.getValue();
                    html += "</th>";   
                }else{
                    html += "<td>";
                    html += e.getValue();
                    html += "</td>";
                }
                
            }
            html += "</tr>";
            i++;            
        }
        html += "</tbody>";
        html += "</table>";
        html += "<div>";
        html += "<h2>Alignment Score = " + this.score + "</h2>";
        html += "<h1>Sequence Alignment</h1>";
        html += "</div>";
        html += "<div>";
        html += "<table>";
        for(String s: alignedSequences){
            html += "<tr>";
            for(char ch : s.toCharArray()){
              if(ch == '_'){
                html += "<td>";
                html += ch;
                html += "</td>";
              }else{
                html += "<td class=\"value\">";
                html += ch;
                html += "</td>";
              }      
            }
            html += "</tr>";
        }
        
        html += "</div>";
        html += "</body>";
        html += "</html>";
        
        return html;
    }
    
    public void showTable(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Matrix");
        
        String content = generateHTML();
        
        WebView web = new WebView();
        WebEngine engine = web.getEngine();        
        engine.loadContent(content);

        Label label;
        label = new Label("Table Matrix");
        label.setAlignment(Pos.TOP_CENTER);
        label.setStyle("-fx-font-size:30px;-fx-font-family:'Century Gothic';-fx-font-weight:bold;-fx-padding-bottom:10px ;-fx-text-fill:#fff;");
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color:#222830;-fx-padding:5px;");
        box.getChildren().add(label);        
        box.getChildren().add(web);
        
        Scene scene = new Scene(box);
        window.setScene(scene);
        window.showAndWait();
    }
}
