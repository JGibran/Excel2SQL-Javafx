/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel2sql;

import excel2sql.util.ExcelReader;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Cristian
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private AnchorPane ap;
    @FXML private TextArea textResult;
    private FileChooser fileChooser = new FileChooser();
    private String result;
    private File excel;
    private Label lblLoading;
    @FXML
    private void handleButtonAction(ActionEvent event) {
      fileChooser.setTitle("Open Resource File");
       excel = fileChooser.showOpenDialog(ap.getScene().getWindow());
       ap.getChildren().add(lblLoading);
       loadQueryes().start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblLoading = LoadingAnimation(); 
    }
    
     public Label LoadingAnimation(){
        final Label    status   = new Label("Loading");
        final Timeline timeline = new Timeline(
          new KeyFrame(Duration.ZERO, new EventHandler() {
              
            @Override 
            public void handle(Event event) {
              String statusText = status.getText();
              status.setText(
                ("Loading . . .".equals(statusText))
                  ? "Loading ." 
                  : statusText + " ."
              );
            }
          }),  
          new KeyFrame(Duration.millis(1000))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return status;
    }
     
     
     public Thread loadQueryes(){
        Thread loadQueryes = new Thread() {
            public void run() {
                try{
                    ExcelReader exReader = new ExcelReader(excel.getAbsolutePath());
                    ArrayList<String> queryes = exReader.read(10);
                    result = "";
                    for(int i = 0; i<queryes.size(); i++){
                        result += queryes.get(i) + "\n" ;
                    }
                }finally{
                    textResult.setText(result);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                           ap.getChildren().remove(lblLoading);
                        }
                    });
                  
                }
            }   
        };
        return loadQueryes;
     }
    
}
