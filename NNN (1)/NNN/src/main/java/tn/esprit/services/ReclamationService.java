package tn.esprit.services;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;
import tn.esprit.entities.Reclamation;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Reponse;


public class ReclamationService {


	//att
	Connection cnx= MaConnexion.getInstance().getCnx();

	//methodes

	public void add(Reclamation Rec){
		String req= "INSERT INTO `reclamation`(`object`, `description_rec`, `categorie`, `etat`,`user_id`) VALUES ('"+Rec.getObject()+"','"+Rec.getDescription()+"','"+Rec.getCategorie()+"','"+Rec.getEtat()+"','"+Rec.getId_user()+"')" ;
		try{
			Statement st = cnx.createStatement();
			st.executeUpdate(req);
			System.out.println("Reclamation added successfuly");
		}catch(SQLException e ){
			throw new RuntimeException(e);
		}

	}
	public void insert (Reclamation Rec){
		String req= "INSERT INTO `reclamation`(`object`, `description_rec`, `categorie`, `etat`) VALUES (?,?,?,?)" ;
		try{
			PreparedStatement ps = cnx.prepareStatement(req);

			ps.setString(1,Rec.getObject());
			ps.setString(2,Rec.getDescription());
			ps.setString(3,Rec.getCategorie());
			ps.setString(4,Rec.getEtat());

			ps.executeUpdate();

		}catch(SQLException e ){
			throw new RuntimeException(e);
		}

	}

	public void update(Reclamation rec, int id) {
		String updateQuery = "UPDATE reclamation SET object = ?, description_rec = ?, categorie = ?, etat = ? WHERE id = ?";
		try {
			PreparedStatement ps = cnx.prepareStatement(updateQuery);
			ps.setString(1, rec.getObject());
			ps.setString(2, rec.getDescription());
			ps.setString(3, rec.getCategorie());
			ps.setString(4, rec.getEtat());
			ps.setInt(5, id);

			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Reclamation with ID " + id + " updated successfully");
			} else {
				System.out.println("No reclamation found with ID: " + id);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public List<Reclamation> getAll(){
		List<Reclamation> recs = new ArrayList<>() ;
		String req = "SELECT * FROM reclamation";

		try{
			Statement st = cnx.createStatement();
			ResultSet res = st.executeQuery(req);
			while (res.next()){

				Reclamation rec = new Reclamation();
				rec.setId(res.getInt("id"));
				rec.setObject(res.getString(2));
				rec.setDescription(res.getString(3));
				rec.setCategorie(res.getString(4));
				rec.setEtat(res.getString(5));
				rec.setId_user(res.getInt("user_id"));

				recs.add(rec);
			}

		}catch(SQLException e ){
			throw new RuntimeException(e);
		}

		return recs;
	}
	public List<Reclamation> getAllByUserId(int user_id){
		List<Reclamation> recs = new ArrayList<>() ;
		String req = "SELECT * FROM reclamation WHERE user_id=?";

		try{
			PreparedStatement ps = cnx.prepareStatement(req);
			ps.setInt(1, user_id);
			ResultSet res = ps.executeQuery();
			while (res.next()){

				Reclamation rec = new Reclamation();
				rec.setId(res.getInt("id"));
				rec.setObject(res.getString(2));
				rec.setDescription(res.getString(3));
				rec.setCategorie(res.getString(4));
				rec.setEtat(res.getString(5));
				rec.setId_user(res.getInt("user_id"));

				recs.add(rec);
			}

		}catch(SQLException e ){
			throw new RuntimeException(e);
		}

		return recs;
	}

	public Reclamation getOne(int id) {
		String req = "SELECT * FROM reclamation WHERE id = ?";
		Reclamation rec = null;

		try {
			PreparedStatement ps = cnx.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet res = ps.executeQuery();

			if (res.next()) {
				rec = new Reclamation();
				rec.setId(res.getInt("id"));
				rec.setObject(res.getString("object"));
				rec.setDescription(res.getString("description_rec"));
				rec.setCategorie(res.getString("categorie"));
				rec.setEtat(res.getString("etat"));
				rec.setId_user(res.getInt("user_id"));

			} else {
				System.out.println("No reclamation found with ID: " + id);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return rec;
	}


	public void delete(int id){
		String deleteReponseQuery= "DELETE FROM `reponse` WHERE `rep_rec_id` = ?";
		String deleteReclamationQuery= "DELETE FROM `reclamation` WHERE `id` = ?";
		try{
			// Supprimer d'abord les réponses associées
			PreparedStatement deleteReponseStatement = cnx.prepareStatement(deleteReponseQuery);
			deleteReponseStatement.setInt(1, id);
			int rowsDeletedReponse = deleteReponseStatement.executeUpdate();

			// Ensuite, supprimer la réclamation
			PreparedStatement deleteReclamationStatement = cnx.prepareStatement(deleteReclamationQuery);
			deleteReclamationStatement.setInt(1, id);
			int rowsDeletedReclamation = deleteReclamationStatement.executeUpdate();

			if (rowsDeletedReclamation > 0) {
				System.out.println("Reclamation with ID " + id + " deleted successfully");
			} else {
				System.out.println("No reclamation found with ID: " + id);
			}

		} catch(SQLException e ){
			throw new RuntimeException(e);
		}
	}

	public List<Reponse> getReponsesForReclamation(int reclamationId) {
		List<Reponse> reponses = new ArrayList<>();
		String query = "SELECT * FROM reponse WHERE rep_rec_id = ?";
		try (PreparedStatement statement = cnx.prepareStatement(query)) {
			statement.setInt(1, reclamationId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Reponse reponse = new Reponse();
					reponse.setId(resultSet.getInt("id"));
					reponse.setDescription_rep(resultSet.getString("description_rep"));
					reponses.add(reponse);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return reponses;
	}
	public Map<String, Integer> getReclamationStatisticsByCategory() throws SQLException {
		Map<String, Integer> statistics = new HashMap<>();
		String query = "SELECT categorie, COUNT(*) AS count FROM reclamation GROUP BY categorie";
		try (PreparedStatement statement = cnx.prepareStatement(query);
		     ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				String categorie = resultSet.getString("categorie");
				int count = resultSet.getInt("count");
				statistics.put(categorie, count);
			}
		}
		return statistics;
	}



}
