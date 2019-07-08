package com.mycompany.githubanalyzergui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class GitHubAnalyzerGUI extends Application {
    private final TableView<Dados> table = new TableView<>();
    public ObservableList<Dados> data;
    ArrayList<String> links;
    ArrayList<Repositorios> repos;
    @Override
    public void start(Stage primaryStage) throws ParseException {
        links = new ArrayList<>();
        repos = new ArrayList<>();
        
        final Menu menuFile = new Menu("File");
        final Menu menuTools = new Menu("Tools");
        final Menu menuHelp = new Menu("Help");
        
        MenuItem menuItemExit = new MenuItem("Exit");
        MenuItem menuItemAbout = new MenuItem("About");
        MenuItem menuItemOpen = new MenuItem("Open");
        MenuItem menuItemCommitAnalyzer = new MenuItem("Commit Analyzer");
        menuItemCommitAnalyzer.setDisable(true);
        
        MenuBar menuBar = new MenuBar();
        
        VBox vbox = new VBox();
        
        Label sobre = new Label();
        Label maiorCommits = new Label();
        Label menorCommits = new Label();
        Label maisRecente = new Label();
        Label menosRecente = new Label();
        TableColumn<Dados,String> url = new TableColumn<>("Url do repositorio");
        TableColumn<Dados,String> numeroCommits = new TableColumn<>("Numero de commits");
        TableColumn<Dados,String> tamMedioMensagens = new TableColumn<>("Tamanho medio das mensagens");
        url.setCellValueFactory(new PropertyValueFactory<>("url"));
        numeroCommits.setCellValueFactory(new PropertyValueFactory<>("numeroCommits"));
        tamMedioMensagens.setCellValueFactory(new PropertyValueFactory<>("tamMedioMensagens"));
        data = FXCollections.observableArrayList(
            new Dados("EX : https://api.github.com/repos/carlinhos1807/paradigmas"," "," ")
        );
        table.getColumns().addAll(Arrays.asList(url,numeroCommits,tamMedioMensagens));
        table.setItems(data); 
        links.add("EX : https://api.github.com/repos/carlinhos1807/paradigmas");
        menuItemAbout.setOnAction((ActionEvent e) -> {
            sobre.setText("GitHubAnalyzerGUI - Feito por Carlos Alberto Rosa dos Santos");
        });
        menuItemExit.setOnAction((ActionEvent e) -> {
            primaryStage.close();
        });
         
        menuItemOpen.setOnAction((ActionEvent e) -> {
            sobre.setText("");
            maiorCommits.setText("");
            menorCommits.setText("");
            maisRecente.setText("");
            menosRecente.setText("");
            TableView<Dados> table2 = new TableView<>();
            table2.getItems().clear();
            table2.getColumns().clear();
            table2.getColumns().addAll(Arrays.asList(url,numeroCommits,tamMedioMensagens));
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            
            if(file != null){
                    menuItemCommitAnalyzer.setDisable(false);
                    Arquivo a = new Arquivo(file.getPath());
                    try {
                        ArrayList<String> list = a.leitor();
                        links.clear();
                        links.addAll(list);
                    } catch (IOException ex) {
                        Logger.getLogger(GitHubAnalyzerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Dados> l = new ArrayList<>();
                    for(int i = 0; i < links.size();i++){
                             Dados d = new Dados(links.get(i),"","");
                             l.add(d);
                         }
                    ObservableList<Dados> data2  = FXCollections.observableArrayList(l);
                    table2.setItems(data2);
                    vbox.getChildren().remove(6);
                    vbox.getChildren().add(table2);
            }
        });
        menuItemCommitAnalyzer.setOnAction((ActionEvent e) -> {
            repos.clear();
            for(int i = 0; i < links.size();i++){
                Repositorios r = new Repositorios(links.get(i));
                repos.add(r);
            }
            Task tarefaCargaPg = new Task(){
                @Override
                protected String call() throws Exception {
                    for(int i = 0; i < links.size();i++){
                        boolean teste = repos.get(i).commits();
                        if(!teste){
                            return "falha";
                        }
                   }
                    return "sucesso";
                }
                @Override
                protected void succeeded() {
                    String codigo = (String) getValue();
                    System.out.println(codigo);
                    if("falha".equals(codigo)){
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("ERRO");
                        alert.setHeaderText("Erro");
                        alert.showAndWait();
                        return;
                    }
                    TableView<Dados> table3 = new TableView<>();
                    table3.getItems().clear();
                    table3.getColumns().clear();
                    table3.getColumns().addAll(Arrays.asList(url,numeroCommits,tamMedioMensagens));
                    ArrayList<Dados> l = new ArrayList<>();
                    
                    String DataMaisRecente = "";
                    String DataMenosRecente = "";
                    
                    int maiorNumCommits = 0;
                    int menorNumCommits = 0;
                    
                    int indiceMaiorNumCommits = 0;
                    int indiceMenorNumCommits = 0;
                    
                    int indiceDataMaisRecente = 0;
                    int indiceDataMenosRecente = 0;
                    for(int i = 0; i < links.size();i++){
                        if(i == 0){
                            maiorNumCommits = Integer.parseInt(repos.get(0).numeroCommits);
                            menorNumCommits = Integer.parseInt(repos.get(0).numeroCommits);
                            DataMaisRecente = Repositorios.converte(repos.get(0).dataRecente);
                            DataMenosRecente = Repositorios.converte(repos.get(0).dataRecente);
                        }else{

                            try {
                                if(Repositorios.dataCompare1(repos.get(i).dataRecente,DataMaisRecente)){
                                    DataMaisRecente = Repositorios.converte(repos.get(i).dataRecente);
                                    indiceDataMaisRecente = i;
                                }} catch (ParseException ex) {
                                Logger.getLogger(GitHubAnalyzerGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                if(!Repositorios.dataCompare1(repos.get(i).dataRecente,DataMenosRecente)){
                                    DataMenosRecente = Repositorios.converte(repos.get(i).dataRecente);
                                    indiceDataMenosRecente = i;
                                }} catch (ParseException ex) {
                                Logger.getLogger(GitHubAnalyzerGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }

                           if(Integer.parseInt(repos.get(i).numeroCommits) > maiorNumCommits){
                               maiorNumCommits = Integer.parseInt(repos.get(i).numeroCommits);
                               indiceMaiorNumCommits = i;
                           }
                           if(Integer.parseInt(repos.get(i).numeroCommits) < menorNumCommits){
                               menorNumCommits = Integer.parseInt(repos.get(i).numeroCommits);
                               indiceMenorNumCommits = i;
                           }
                        }
                        Dados d = new Dados(links.get(i),repos.get(i).numeroCommits,repos.get(i).tamMedioMensagens);
                        l.add(d);
                    }
                    
                    ObservableList<Dados> data3  = FXCollections.observableArrayList(l);

                    maisRecente.setText("Repositorio com Commit mais recente(" +repos.get(indiceDataMaisRecente).dataRecente+ ") = " +links.get(indiceDataMaisRecente));
                    menosRecente.setText("Repositorio com Commit menos recente(" +repos.get(indiceDataMenosRecente).dataRecente+ ") = " +links.get(indiceDataMenosRecente));
                    maiorCommits.setText("Repositorio com maior numero de commits = " +links.get(indiceMaiorNumCommits));
                    menorCommits.setText("Repositorio com menor numero de commits = " +links.get(indiceMenorNumCommits)); 


                    table3.setItems(data3);
                    vbox.getChildren().remove(6);
                    vbox.getChildren().add(table3);
                }
            };
            Thread t = new Thread(tarefaCargaPg);
            t.setDaemon(true);
            t.start();
        });
        vbox.setSpacing(10);
        menuFile.getItems().addAll(menuItemOpen,menuItemExit);
        menuHelp.getItems().add(menuItemAbout);
        menuTools.getItems().add(menuItemCommitAnalyzer);
        menuBar.getMenus().addAll(menuFile, menuTools, menuHelp);
       
        vbox.getChildren().addAll(menuBar,sobre,maiorCommits,menorCommits,maisRecente,menosRecente,table);
        Scene scene = new Scene(vbox, 800, 600);
        
        primaryStage.setTitle("GitHubAnalyzerGui");
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
