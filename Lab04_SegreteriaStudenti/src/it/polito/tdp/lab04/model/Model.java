package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.IscrizioneDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	public List<Corso> getTuttiICorsi() {
		CorsoDAO dao = new CorsoDAO();
		return dao.getTuttiICorsi();
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		CorsoDAO dao = new CorsoDAO();
		return dao.getCorso(corso);
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiACorso(Corso corso) {
		CorsoDAO dao = new CorsoDAO();
		return dao.getStudentiIscrittiAlCorso(corso);
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean isccrizioneStudenteACorso(Studente studente, Corso corso) {
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}
	
	public Studente getStudenteByMatricola(int matricola) { //??
		Studente s = new Studente(matricola, null, null, null);
		StudenteDAO dao = new StudenteDAO();
		return dao.getStudente(s);
	}

	public List<Corso> getCorsiDiStudente(Studente s) {

		StudenteDAO dao = new StudenteDAO();
		return dao.getCorsiDiIscrizione(s);
	}

	public boolean verficaIscrizione(Iscrizione i) {
		IscrizioneDAO dao = new IscrizioneDAO();
		return dao.verificaIscrizione(i);
	}

	public boolean iscriviStudenteACorso(Iscrizione i) {

		IscrizioneDAO dao = new IscrizioneDAO();
		return dao.iscrivi(i);
	}

}
