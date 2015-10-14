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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


/**
 *
 * @author Cristian
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private AnchorPane ap;
    @FXML private TabPane tpResultContainer;
    @FXML private ProgressBar pbLoadExcel;
    
    
    private FileChooser fileChooser = new FileChooser();
    private String result;
    private File excel;

    @FXML
    private void handleButtonAction(ActionEvent event) {
      fileChooser.setTitle("Open Excel File");
       excel = fileChooser.showOpenDialog(ap.getScene().getWindow());
       pbLoadExcel.setVisible(true);
       loadQueryes().start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

     public Thread loadQueryes(){
        Thread loadQueryes = new Thread() {
            public void run() {
                try{
                    ExcelReader exReader = new ExcelReader(excel.getAbsolutePath());
                    ArrayList<String> queryes = exReader.read(0);
                    result = "";
                    for(int i = 0; i<queryes.size(); i++){
                        result += queryes.get(i) + "\n" ;
                    }
                    //
                }finally{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                           tpResultContainer.getTabs().add(getQuerySheet(result));
                        }
                    });
                  pbLoadExcel.setVisible(false);
                }
            }   
        };
        return loadQueryes;
     }
    
     public Tab getQuerySheet(String query){
         Tab tbSheet = new Tab("Query");
         tbSheet.setClosable(true);
         AnchorPane apTabContainer = new AnchorPane();
         TextArea txtQuery = new TextArea(query);
         txtQuery.setPrefWidth(tpResultContainer.getPrefWidth());
         txtQuery.setPrefHeight(tpResultContainer.getPrefWidth());
         apTabContainer.getChildren().add(txtQuery);
         tbSheet.setContent(apTabContainer);
         return tbSheet;
     }
     
}
