package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Iscrizione;
import it.polito.tdp.lab04.model.Studente;

public class IscrizioneDAO {
	
	public boolean verificaIscrizione(Iscrizione i) {
		
		Iscrizione iscrizione = null;
		
		final String sql = "SELECT * " + 
				"FROM iscrizione i " + 
				"WHERE i.codins= ? AND i.matricola = ? ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, i.getCodins());
			st.setInt(2, i.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int matricola = rs.getInt("matricola");
				
				iscrizione = new Iscrizione(matricola, codins);
			}
			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		if(i.equals(iscrizione))
			return true;
		else
			return false;
	}

	public boolean iscrivi(Iscrizione i) {
//		if(!this.verificaIscrizione(i)) {
//			
			boolean eseguito=false;
			final String sql = "INSERT IGNORE INTO `iscritticorsi`.`iscrizione` "
					+ "(`matricola`, `codins`) VALUES(?,?)";
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, i.getMatricola());
				st.setString(2,  i.getCodins());
				
				int rs = st.executeUpdate();
				if(rs==1)
					eseguito=true;

			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}
			
			return eseguito;
//		}
//		else
//			return false;
	}

}
