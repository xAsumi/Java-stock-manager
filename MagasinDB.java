import java.sql.*;
import java.util.ArrayList;

public class MagasinDB {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
    private static final String USER = "root";
    private static final String PASS = "3103";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void save(Article art) {
        String sql = "INSERT INTO articles (type, titre, prix, auteur, console) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (art instanceof Livre) {
                pstmt.setString(1, "Livre");
                pstmt.setString(2, cleanTitle(art.toString()));
                pstmt.setDouble(3, art.getPrixLocation());
                String detail = art.toString();
                String auteur = detail.contains("Auteur :") ? detail.split("Auteur : ")[1] : "Inconnu";
                pstmt.setString(4, auteur);
                pstmt.setString(5, null);
            }
            else if (art instanceof JeuVideo) {
                pstmt.setString(1, "Jeu");
                pstmt.setString(2, cleanTitle(art.toString()));
                pstmt.setDouble(3, art.getPrixLocation());
                pstmt.setString(4, null);
                String detail = art.toString();
                String console = detail.contains("Console :") ? detail.split("Console : ")[1] : "PC";
                pstmt.setString(5, console);
            }
            else if (art instanceof DVD) {
                pstmt.setString(1, "DVD");
                pstmt.setString(2, cleanTitle(art.toString()));
                pstmt.setDouble(3, art.getPrixLocation());
                pstmt.setString(4, null);
                pstmt.setString(5, null);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Article> load() {
        ArrayList<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles";

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
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    private static String cleanTitle(String raw) {
        try {
            String[] parts = raw.split(" \\| ");
            String prefixPart = parts[0];
            if(prefixPart.contains("] ")) {
                return prefixPart.split("] ")[1];
            }
            return prefixPart.replace("Titre : ", "");
        } catch (Exception e) { return raw; }
    }

    public static void deleteByTitre(String titre) {

        String sql = "DELETE FROM articles WHERE titre = ? OR titre LIKE ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titre);
            pstmt.setString(2, "%" + titre + "%");

            int affected = pstmt.executeUpdate();

            if (affected > 0) {
                System.out.println(">> Success! Deleted: " + titre);
            } else {
                System.out.println(">> Failed. Could not find: " + titre);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        }

