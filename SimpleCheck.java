import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class SimpleCheck {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/rendezvous_db";
        String user = "root";
        String password = "";
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            
            System.out.println("=== UTILISATEURS DANS LA BASE DE DONNEES ===");
            
            ResultSet rs = stmt.executeQuery("SELECT id, nom, prenom, email FROM utilisateurs ORDER BY id");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                
                System.out.println("ID: " + id + " | Nom: " + nom + " | Prénom: " + prenom + " | Email: " + email);
                System.out.println("   Mot de passe: " + email.toLowerCase());
                System.out.println();
            }
            
            System.out.println("=== MOTS DE PASSE POUR LA CONNEXION ===");
            System.out.println("Admin: admin@rendezvous.com / admin");
            System.out.println("User: user@rendezvous.com / user");
            System.out.println("Alice: alice.durand@rendezvous.com / alice.durand@rendezvous.com");
            System.out.println("Bob: bob.lefebvre@rendezvous.com / bob.lefebvre@rendezvous.com");
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
