import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Objects;

public class MagasinDB {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
    private static final String USER = "root";
    private static final String PASS = "3103";

    private MagasinDB() {
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void save(Article article) {
        Objects.requireNonNull(article, "Article invalide: null");
        String sql = "INSERT INTO articles (type, titre, prix, auteur, console) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, article.getType());
            pstmt.setString(2, article.getTitre());
            pstmt.setDouble(3, article.getPrix());

            if (article instanceof Livre livre) {
                pstmt.setString(4, livre.getAuteur());
                pstmt.setNull(5, Types.VARCHAR);
            } else if (article instanceof JeuVideo jeuVideo) {
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setString(5, jeuVideo.getConsole());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setNull(5, Types.VARCHAR);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur DB (save): " + e.getMessage());
        }
    }

    public static ArrayList<Article> load() {
        ArrayList<Article> list = new ArrayList<>();
        String sql = "SELECT type, titre, prix, auteur, console FROM articles ORDER BY id";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                String titre = rs.getString("titre");
                double prix = rs.getDouble("prix");
                String auteur = rs.getString("auteur");
                String console = rs.getString("console");

                if ("Livre".equalsIgnoreCase(type)) {
                    list.add(new Livre(titre, auteur, prix));
                } else if ("Jeu".equalsIgnoreCase(type)) {
                    list.add(new JeuVideo(titre, console, prix));
                } else if ("DVD".equalsIgnoreCase(type)) {
                    list.add(new DVD(titre, prix));
                } else {
                    System.err.println("Type d'article inconnu ignoré: " + type);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur DB (load): " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Donnée invalide ignorée: " + e.getMessage());
        }
        return list;
    }

    public static void deleteByTitre(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            System.err.println("Suppression ignorée: titre vide.");
            return;
        }
        String normalizedTitre = titre.trim();
        String sql = "DELETE FROM articles WHERE titre = ? OR titre LIKE ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, normalizedTitre);
            pstmt.setString(2, "%" + normalizedTitre + "%");
            int affected = pstmt.executeUpdate();

            if (affected > 0) {
                System.out.println(">> Success! Deleted: " + normalizedTitre);
            } else {
                System.out.println(">> Failed. Could not find: " + normalizedTitre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur DB (deleteByTitre): " + e.getMessage());
        }
    }
}
