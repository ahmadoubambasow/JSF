/**
 * 
 */
package com.gesstudent.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * 
 */
@RequestScoped
@Named
public class StudentBean {
	private Etudiant etudiant;
	private List<Etudiant> listeEtudiant;
	
	public StudentBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void chargerListeEtudiant() {
		listeEtudiant = new ArrayList<Etudiant>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbetudiant?useSSL=t", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from etudiant");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Etudiant e = new Etudiant();
				e.setId(rs.getInt("id"));
				e.setNom(rs.getString("nom"));
				e.setPrenom(rs.getString("prenom"));
				e.setVille(rs.getString("ville"));
				
				listeEtudiant.add(e);
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ajouterEtudiant() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdetudiant?useSSL=false", "root", "root");
			String query = "INSERT INTO etudiant (nom, prenom, ville) VALUES (?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, etudiant.getNom());
			pstmt.setString(2, etudiant.getPrenom());
			pstmt.setString(3, etudiant.getVille());
			
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modifierEtudiant(int etudiantId) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdetudiant?useSSL=false", "root", "root");
			String query = "UPDATE etudiant SET nom=?, prenom=?, ville=? WHERE id=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, etudiant.getNom());
			pstmt.setString(2, etudiant.getPrenom());
			pstmt.setString(3, etudiant.getVille());
			pstmt.setInt(4, etudiant.getId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			con.close();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void supprimerEtudiant(int etudiantId) {
	    try {
	        // Établir une connexion à la base de données (à remplacer avec vos paramètres)
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbetudiant?useSSL=false", "utilisateur", "motdepasse");

	        // Préparer la requête de suppression
	        String query = "DELETE FROM etudiant WHERE id=?";
	        PreparedStatement pstmt = con.prepareStatement(query);
	        pstmt.setInt(1, etudiantId);

	        // Exécuter la requête de suppression
	        pstmt.executeUpdate();

	        // Fermer la connexion et le statement
	        pstmt.close();
	        con.close();

	        // Recharger la liste des étudiants après la suppression
	        chargerListeEtudiant();

	    } catch (SQLException e) {
	        // Gérer les erreurs liées à la base de données
	        e.printStackTrace();
	    }
	}

	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/**
	 * @return the listeEtudiant
	 */
	public List<Etudiant> getListeEtudiant() {
		return listeEtudiant;
	}

	/**
	 * @param listeEtudiant the listeEtudiant to set
	 */
	public void setListeEtudiant(List<Etudiant> listeEtudiant) {
		this.listeEtudiant = listeEtudiant;
	}
	
	
}
