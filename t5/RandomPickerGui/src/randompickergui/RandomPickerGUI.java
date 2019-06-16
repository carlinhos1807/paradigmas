/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randompickergui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;



/**
 *
 * @author Carlinhos
 */
public class RandomPickerGUI extends Application {
    ArrayList<String> nomes;
    String l;
    @Override
    public void start(Stage primaryStage) {
        nomes = new ArrayList<>();
        l = "";
        final Menu menuFile = new Menu("File");
        final Menu menuHelp = new Menu("Help");
        MenuItem menuItemExit = new MenuItem("Exit");
        MenuItem menuItemAbout = new MenuItem("About");
        MenuItem menuItemOpen = new MenuItem("Open");
        MenuBar menuBar = new MenuBar();
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        
        Button next = new Button("Next");
        next.setDisable(true);
        Button shuffle = new Button("Shuffle");

        Text sobre = new Text();
        TextArea textArea = new TextArea();
        Label label = new Label();
        label.setPadding(new Insets(0, 12, 15, 12));
        
        hbox.getChildren().addAll(next,shuffle);
        menuFile.getItems().addAll(menuItemOpen,menuItemExit);
        menuHelp.getItems().add(menuItemAbout);
        menuBar.getMenus().addAll(menuFile, menuHelp);
        vbox.getChildren().addAll(menuBar,sobre,textArea,hbox,label);

        
        menuItemOpen.setOnAction((ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            Arquivo a = new Arquivo(file.getPath());
            label.setText("");
            l = "";
            next.setDisable(true);
            textArea.setEditable(true);
            try {
                ArrayList<String> list = a.leitor();
                nomes.clear();
                nomes.addAll(list);
                textArea.setText(Embaralhamento.mostraGui(nomes));
            } catch (IOException ex) {
                Logger.getLogger(RandomPickerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        shuffle.setOnAction((ActionEvent e) -> {
            String data = textArea.getText().trim(); //read contents of text area into 'data'
            if(!data.equals("")) {
                next.setDisable(false);
                nomes.clear();
                label.setText("");
                l = "";
                
                for (String line : textArea.getText().split("\\n")){
                    if(!line.trim().equals("")){
                        nomes.add(line.trim());
                    }
                }
            textArea.setText(Embaralhamento.mostraGui(nomes));
            if(nomes.size() > 100){
                EmbaralhamentoOnline e1 = new EmbaralhamentoOnline(nomes);
                boolean teste = e1.embaralha();
                if(!teste){
                  EmbaralhamentoOffline e2 = new EmbaralhamentoOffline(nomes);
                  e2.embaralha();
                }
            }else{
                EmbaralhamentoOffline e2 = new EmbaralhamentoOffline(nomes);
                e2.embaralha();
            }
                textArea.setEditable(false);
           }
        });
        next.setOnAction((ActionEvent e) -> {
            int i = 0;
            l+=nomes.get(0);
            l+="\n";
            label.setText(l);
            nomes.remove(0);
            if(nomes.isEmpty()){
                next.setDisable(true);
                textArea.setEditable(true);
            }
        });
        menuItemAbout.setOnAction((ActionEvent e) -> {
            sobre.setText("RandomPickerGui - Feito por Carlos Alberto Rosa dos Santos");
        });
        menuItemExit.setOnAction((ActionEvent e) -> {
            primaryStage.close();
        });
        Scene scene = new Scene(vbox, 800, 600);
        
        primaryStage.setTitle("RandomPickerGUI!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
