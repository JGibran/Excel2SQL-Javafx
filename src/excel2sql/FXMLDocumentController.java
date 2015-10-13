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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 *
 * @author Cristian
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private AnchorPane AnchorPane;
    @FXML private TextArea textResult;
    private FileChooser fileChooser = new FileChooser();
    private Thread loadQuerys;
    private String result;
    private File excel;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
      fileChooser.setTitle("Open Resource File");
       excel = fileChooser.showOpenDialog(AnchorPane.getScene().getWindow());
       loadQuerys.start();
      
       
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       loadQuerys = new Thread() {

                // runnable for that thread

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
                    }
                  
                     
                }
               
            };
        
        
        
        
        
        
       
    }    
    
}
