package com.mycompany.enadeex;

import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author Carlinhos
 */
public class EnadeUFSMExplorer extends Application {
    private final TableView<Enade> table = new TableView<>();
    List<List<String>> records;
    public ObservableList<Enade> data;
    public String url;
    @Override
    public void start(Stage primaryStage) {
        records = new ArrayList<>();
        url = "https://docs.google.com/spreadsheets/d/e/2PACX-1vTO06Jdr3J1kPYoTPRkdUaq8XuslvSD5--FPMht-ilVBT1gExJXDPTiX0P3FsrxV5VKUZJrIUtH1wvN/pub?gid=0&single=true&output=csv";
        Dados c = new Dados(url);
        Planilha p = new Planilha(c);
        if(!p.load()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro no carregamento do arquivo!");
            alert.setContentText("Verifique se está conectado à internet e dê reload !");
            alert.showAndWait();
        }
        records = c.records;
        data = c.data;
        url = c.url;
        
        TextArea textArea = new TextArea();
        Label about = new Label();
        about.setText("");
        textArea.setPrefHeight(5);
        Button button = new Button("Alterar URL");
        button.setDisable(true);
        textArea.setEditable(false);
        
        final Menu menuFile = new Menu("File");
        final Menu menuHelp = new Menu("Help");
        MenuItem menuItemExit = new MenuItem("Exit");
        MenuItem menuItemReload = new MenuItem("Reload");
        MenuItem menuItemSource = new MenuItem("Source");
        MenuItem menuItemAbout = new MenuItem("About");
        MenuBar menuBar = new MenuBar();
        
        Colunas c1 = new Colunas("ano","prova","tipoQuestao","idQuestao","objeto","acertosCurso","acertosRegiao","acertosBrasil","dif","gabarito","url");
        
        table.setItems(data);
        table.setRowFactory(tv -> {
            TableRow<Enade> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Enade rowData = row.getItem();
                    Stage dialog = new Stage();
                    
                    VBox vbox2 = new VBox();
                    HBox Hbox = new HBox();
                    vbox2.setSpacing(10);
                    Text newtext = new Text("DADOS E IMAGEM");
                    
                    TableView<Enade> table2 = new TableView<>();
                    Colunas c2 = new Colunas("ano","prova","tipoQuestao","idQuestao","objeto","acertosCurso","acertosRegiao","acertosBrasil","dif","gabarito","url");
                    table2.getColumns().addAll(c2.getAno(), c2.getProva(),c2.getTipoQuestao(),c2.getIdQuestao(),c2.getObjeto(),c2.getAcertosCurso(),c2.getAcertosRegiao(),c2.getAcertosBrasil(),c2.getDif(),c2.getGabarito(),c2.getUrl2());
                    table2.setPrefSize(800,60);
                    String nome_image = String.valueOf(row.getIndex()+1);
                    nome_image+=".png";
                     String firstFourChars;
                    if (records.get(row.getIndex()+1).get(17).length() > 24)
                    {
                        firstFourChars = records.get(row.getIndex()+1).get(17).substring(0, 24);
                    }
                    else
                    {
                        firstFourChars = records.get(row.getIndex()+1).get(17);
                    }
                    ImageView imageView;
                    if("https://drive.google.com".equals(firstFourChars)){
                    Arquivo image = new Arquivo(nome_image,records.get(row.getIndex()+1).get(17));
                    
                    
                    if(image.baixaArquivo()){
                        imageView = new ImageView(records.get(row.getIndex()+1).get(17));
                        imageView.setFitHeight(500);
                        imageView.setFitWidth(500);
                    }else{
                        imageView = new ImageView("https://img.itch.zone/aW1nLzE2NzMzMjIucG5n/original/ismdDS.png");
                        imageView.setFitHeight(500);
                        imageView.setFitWidth(500);
                    }
                    }else{
                        imageView = new ImageView("https://img.itch.zone/aW1nLzE2NzMzMjIucG5n/original/ismdDS.png");
                        imageView.setFitHeight(500);
                        imageView.setFitWidth(500);
                    }
                    
                    
                   
                    ObservableList<Enade> data2  =
               
                    FXCollections.observableArrayList(
                        new Enade(records.get(row.getIndex()+1).get(1),records.get(row.getIndex()+1).get(2),records.get(row.getIndex()+1).get(3),
                        records.get(row.getIndex()+1).get(4),records.get(row.getIndex()+1).get(5),records.get(row.getIndex()+1).get(8),records.get(row.getIndex()+1).get(9),records.get(row.getIndex()+1).get(10),
                        records.get(row.getIndex()+1).get(11),records.get(row.getIndex()+1).get(7),records.get(row.getIndex()+1).get(17))
            
            
                    );
        
                    table2.setItems(data2);
                    Button button2 = new Button("Grafico de acertos");
                    Hbox.getChildren().addAll(button2,imageView);
                    vbox2.getChildren().addAll(newtext,table2,Hbox);
                    Hbox.setAlignment(Pos.CENTER);
                    button2.setOnAction((ActionEvent e) -> {
                        Stage grafico = new Stage();
                        String acertosCurso = "Acertos do curso";
                        String acertosRegiao = "Regiao";
                        String acertosBrasil = "Brasil";
                        final CategoryAxis xAxis = new CategoryAxis();
                        final NumberAxis yAxis = new NumberAxis();
                        final BarChart<String,Number> bc = 
                        new BarChart<>(xAxis,yAxis);
                        bc.setTitle("Acertos na questão");
                        xAxis.setLabel("Acertos");       
                        yAxis.setLabel("Percentual de acerto(%)");
                        String s1 = records.get(row.getIndex()+1).get(8).replace(',','.');
                        String s2 = records.get(row.getIndex()+1).get(9).replace(',','.');
                        String s3 = records.get(row.getIndex()+1).get(10).replace(',','.');
                        float n1 = Float.parseFloat(s1);
                        float n2 = Float.parseFloat(s2);
                        float n3 = Float.parseFloat(s3);
 
                        XYChart.Series series1 = new XYChart.Series();
                        series1.setName("Quantidade de acertos");       
                        series1.getData().add(new XYChart.Data(acertosCurso, n1));
                        series1.getData().add(new XYChart.Data(acertosRegiao, n2));
                        series1.getData().add(new XYChart.Data(acertosBrasil, n3));      
  
                        grafico.initOwner(dialog);
                        grafico.initModality(Modality.APPLICATION_MODAL);
                        Scene scene3 = new Scene(bc,800 , 600);
                        bc.getData().addAll(series1);
                        grafico.setScene(scene3);
                        grafico.showAndWait();
                    });

                    dialog.initOwner(primaryStage);
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    
                    Scene scene2 = new Scene(vbox2,800 , 600);
                    dialog.setScene(scene2);
                    

                    dialog.showAndWait();
                    
                }
            });
            return row;
        });
        menuItemExit.setOnAction((ActionEvent e) -> {
            primaryStage.close();
        });
        menuItemReload.setOnAction((ActionEvent e) -> {
            Dados d = new Dados(url);
            Planilha p2 = new Planilha(d);
            if(!p2.reload()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("ERRO");
                alert.setHeaderText("Erro no carregamento do arquivo!");
                alert.setContentText("Verifique se a URL está correta ou se está conectado à internet e dê reload !");
                alert.showAndWait();
            }else{
                records = d.records;
                data = d.data;
                url = d.url;
                table.setItems(data);
            }
        
        });
        menuItemSource.setOnAction((ActionEvent e) -> {
            button.setDisable(false);
            textArea.setEditable(true);
        
        });
        button.setOnAction((ActionEvent e) -> {
            url = textArea.getText();
            button.setDisable(true);
            textArea.setEditable(false);
        
        });
         menuItemAbout.setOnAction((ActionEvent e) -> {
           about.setText("ENADE UFSM Explorer feito por Carlos Alberto Rosa dos Santos");
        
        });

        table.getColumns().addAll(c1.ano, c1.prova,c1.tipoQuestao,c1.idQuestao,c1.objeto,c1.acertosCurso,c1.acertosRegiao,c1.acertosBrasil,c1.dif);

        
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        
        menuFile.getItems().addAll(menuItemReload,menuItemSource,menuItemExit);
        menuHelp.getItems().add(menuItemAbout);
        menuBar.getMenus().addAll(menuFile, menuHelp);
        vbox.getChildren().addAll(menuBar,about,table,textArea,button);
        
        Scene scene = new Scene(vbox, 1024, 768);
        
        primaryStage.setTitle("EnadeUFSMExplorer");
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
