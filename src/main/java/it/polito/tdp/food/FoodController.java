/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	String portionName = boxPorzioni.getValue();
    	if(portionName == null) {
    		txtResult.setText("Selezionare un tipo di porzione tra quelle elencate per poter procedere "
    				+ "con la ricorsione.\n");
    		return;
    	}
    	Integer N;
    	try {
    		N = Integer.parseInt(txtPassi.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero intero che indichi il numero di passi del cammino!\n");
    		return;
    	}
    	if(N < 0) {
    		txtResult.setText("Inserire un valore positivo che indichi il numero di passi!\n");
    		return;
    	}
    	List<String> path = this.model.bestPath(portionName, N);
    	if(path.isEmpty()) {
    		txtResult.setText("Nessun cammino trovato.\n");
    		return;
    	}
    	txtResult.appendText("Percorso di peso " + this.model.bestWeight() + " trovato a partire "
    			+ "dal vertice " + portionName + "\n");
    	for(String s : path) {
    		txtResult.appendText("\n" + s);
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String portionName = boxPorzioni.getValue();
    	if(portionName == null) {
    		txtResult.setText("Selezionare un tipo di porzione tra quelle elencate per poter procedere "
    				+ "con l'analisi dei vicini.\n");
    		return;
    	}
    	List<String> neighbors = this.model.getNeighborsOf(portionName);
    	if(neighbors.isEmpty()) {
    		txtResult.appendText(String.format("La porzione %s non ha nessun vicino.\n", portionName));
    		return;
    	}
    	txtResult.appendText(String.format("Vicini della porzione %s:\n\n", portionName));
    	for(String n : neighbors) {
    		txtResult.appendText(n + "\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer calories;
    	try {
    		calories = Integer.parseInt(txtCalorie.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero intero che indichi le calorie massime!\n");
    		return;
    	}
    	if(calories < 0) {
    		txtResult.setText("Inserire un valore positivo che indichi le calorie massime!\n");
    		return;
    	}
    	this.model.createGraph(calories);
    	if(this.model.vertexSize() == null || this.model.edgeSize() == null) {
    		txtResult.setText("Problema nella creazione del grafo.");
    		return;
    	}
    	txtResult.appendText("Grafo creato (" + this.model.vertexSize() + " vertici,  " + this.model.edgeSize() + " archi) "
    			+ "con le specifiche inviate.\n"
    			+ "Sono disponibili nuove azioni.");
    	boxPorzioni.setDisable(false);
    	boxPorzioni.getItems().addAll(this.model.getAllVertices());
    	btnCorrelate.setDisable(false);   
    	btnCammino.setDisable(false);
    	txtPassi.setDisable(false);

    }
    
    @FXML
    void doBloccaAzioni(ActionEvent event) {
    	boxPorzioni.setDisable(true);
    	btnCorrelate.setDisable(true);
    	btnCammino.setDisable(true);
    	txtPassi.setDisable(true);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxPorzioni.setDisable(true);
    	btnCorrelate.setDisable(true);
    	btnCammino.setDisable(true);
    	txtPassi.setDisable(true);
    	txtResult.setText("Inserire il valore delle calorie massime per i tipi di porzione che si vogliono "
    			+ "visualizzare. I valori delle calorie sono compresi in un range tra 0 e 1670 circa, con una "
    			+ "maggiore densità nelle prime centinaia (valori consigliati).");
    }
}
