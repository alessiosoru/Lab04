package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Iscrizione;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	private Model model;
	List<Corso> corsi;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorsi;

    @FXML
    private Button buttonCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button checkButton;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button buttonCercaCorsi;

    @FXML
    private Button buttonoIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button resetButton;
    
    @FXML
    private Button buttonVerificaIscrizione;
    
    @FXML
    void handleNuovaSelezioneCorso(ActionEvent event) {
    	
    	if((this.comboCorsi.getValue())!=null) {
    		this.txtCognome.clear();
    		this.txtMatricola.clear();
    		this.txtNome.clear();
    		this.txtResult.clear();
    		this.buttonCercaIscrittiCorso.setDisable(false);
    		this.txtCognome.setDisable(false);
    		this.txtNome.setDisable(false);
    		this.txtMatricola.setDisable(false);	
    	} else {
    		this.txtCognome.setDisable(true);
    		this.txtMatricola.setDisable(true);
    		this.txtNome.setDisable(true);
    		this.buttonCercaIscrittiCorso.setDisable(true);
    	}
    }
    
    @FXML
    void handleRicercaIscrittiCorso(ActionEvent event) {
    	
    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	try{
    		Corso c = this.comboCorsi.getValue();
    		List <Studente> sIscritti = model.getStudentiIscrittiACorso(c);

        	for(Studente s : sIscritti){
        		this.txtResult.appendText(s.toString()+"\n");    	
        	}

    	}catch(RuntimeException e) {
    		txtResult.appendText("Seleziona un corso\nSe l'errore  persiste, potrebbe"
    				+ "essere presente un errore di connessione al database\n");
    	}
    }
    
    @FXML
    void handleCompletamentoAutomatico(ActionEvent event) {
    	txtNome.clear();
    	txtCognome.clear();
    	txtResult.clear();
    	int matricola=-1;
    	try{
    		matricola = Integer.parseInt(this.txtMatricola.getText());
    	} catch(NumberFormatException nfe) {
    		txtResult.appendText("Matricola sbagliata, inserire un valore numerico");
    	}
    	
    	if(matricola!=-1) {
        	this.txtNome.appendText(model.getStudenteByMatricola(matricola).getNome().toString());
        	this.txtCognome.appendText(model.getStudenteByMatricola(matricola).getCognome().toString());
    		
    	}
    }

    @FXML
    void handleRicercaCorsi(ActionEvent event) {
    	
    	txtResult.clear();
    	try{
    		Studente s = model.getStudenteByMatricola(Integer.parseInt(this.txtMatricola.getText()));
        	handleCompletamentoAutomatico(event);
        	
    		List <Corso> corsi = model.getCorsiDiStudente(s);

        	for(Corso c : corsi){
        		this.txtResult.appendText(c.toString()+"\n");    	
        	}

    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire una matricola nel formato corretto\n");
    	}catch(RuntimeException e) {
    		txtResult.appendText("Errore connessione database\n");
    	}

    }

    @FXML
    void handleVerificaIscrizione(ActionEvent event) {
    	txtResult.clear();
    	try{
    		Studente s = model.getStudenteByMatricola(Integer.parseInt(this.txtMatricola.getText()));
    		Corso c = this.comboCorsi.getValue();
    		Iscrizione i = new Iscrizione(s.getMatricola(), c.getCodins());
    		this.handleCompletamentoAutomatico(event);
    		
    		if(model.verficaIscrizione(i)) {
    			txtResult.appendText("Lo studente selezionato\n"+s.toString()+
    					"\nFA PARTE del corso\n"+c.toString());
        	}
    		else{
    			txtResult.appendText("Lo studente selezionato\n"+s.toString()+
    					"\nNON FA PARTE del corso\n"+c.toString());
        	}

    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire una matricola nel formato corretto\n");
    	}catch(RuntimeException e) {
    		txtResult.appendText("Seleziona un corso\nSe l'errore persiste, potrebbe" + 
    				"essere presente\nun errore di connessione al database\n");
    	}

    }

    @FXML
    void handleIscrizione(ActionEvent event) {
    	
    	try{
    		Studente s = model.getStudenteByMatricola(Integer.parseInt(this.txtMatricola.getText()));
    		Corso c = this.comboCorsi.getValue();
    		Iscrizione i = new Iscrizione(s.getMatricola(), c.getCodins());
    		this.handleCompletamentoAutomatico(event);
    		
    		if(model.iscriviStudenteACorso(i)) {
    			txtResult.appendText("ISCRIZIONE RIUSCITA");
    		}
    		else {
    			txtResult.appendText("ISCRIZIONE GIA' PRESENTE");
    		}

    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire una matricola nel formato corretto\n");
    	}catch(RuntimeException e) {
    		txtResult.appendText("Inserimento non riuscito\nVerifica il corretto "+
    					"inserimento della matricola o la selezione del corso\n"+""
    							+ "Se l'errore persiste, potrebbe " + 
    				"essere presente un errore di connessione al database\n");
    	}

    }
    
    @FXML
    void handleReset(ActionEvent event) {

    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtResult.clear();
    	comboCorsi.getSelectionModel().clearSelection();
    	txtMatricola.setDisable(false);
    }

    

    @FXML
    void initialize() {
        assert comboCorsi != null : "fx:id=\"comboCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert buttonCercaIscrittiCorso != null : "fx:id=\"buttonCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert checkButton != null : "fx:id=\"checkButton\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert buttonCercaCorsi != null : "fx:id=\"buttonCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert buttonoIscrivi != null : "fx:id=\"buttonoIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

    }
    
    public void setModel(Model model) {
    	
//    	txtInserito.setText("Selezionare una lingua");
//    	
       
//    	
//    	labelErrori.setText("");
//		labelStato.setText("");
		this.model = model;
		corsi = model.getTuttiICorsi();
		Collections.sort(corsi);
		this.comboCorsi.getItems().addAll(corsi);
//		for(int i=0; i<model.getTuttiICorsi().size();i++) {
////			System.out.println(model.getTuttiICorsi().get(i).getNome().toString());
//			this.comboCorsi.set(model.getTuttiICorsi().get(i));
//			this.comboCorsi.getItems().add(model.getTuttiICorsi().get(i).getNome().toString());
//			
//		}
	}
}