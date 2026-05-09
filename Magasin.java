import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Magasin {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "3103";

    private Magasin() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Article> stock = new ArrayList<>();

        authenticate(scanner);

        int choix;
        do {
            afficherMenu();
            choix = readInt(scanner, "Votre choix : ");

            switch (choix) {
                case 1 -> addLivre(scanner, stock);
                case 2 -> addJeuVideo(scanner, stock);
                case 3 -> addDvd(scanner, stock);
                case 4 -> afficherStock(stock);
                case 5 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 5);

        scanner.close();
        System.out.println("Programme terminé.");
    }

    private static void authenticate(Scanner scanner) {
        System.out.println("=== LOGIN ===");
        boolean connected = false;
        while (!connected) {
            String user = readNonEmpty(scanner, "User  : ");
            String pass = readNonEmpty(scanner, "Pass  : ");

            if (ADMIN_USER.equals(user) && ADMIN_PASS.equals(pass)) {
                connected = true;
                System.out.println(">> Connexion reussie !");
            } else {
                System.out.println(">> Erreur d'identifiants");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("\n--- La GESTION DE VOTRE MAGASIN ---");
        System.out.println("1. Ajouter un Livre");
        System.out.println("2. Ajouter un Jeu Video");
        System.out.println("3. Ajouter un DVD");
        System.out.println("4. Afficher le stock");
        System.out.println("5. Quitter");
    }

    private static void addLivre(Scanner scanner, List<Article> stock) {
        String titre = readNonEmpty(scanner, "Titre : ");
        String auteur = readNonEmpty(scanner, "Auteur : ");
        double prix = readPrice(scanner, "Prix : ");

        Livre livre = new Livre(titre, auteur, prix);
        stock.add(livre);
        MagasinDB.save(livre);
    }

    private static void addJeuVideo(Scanner scanner, List<Article> stock) {
        String titre = readNonEmpty(scanner, "Titre : ");
        String console = readNonEmpty(scanner, "Console : ");
        double prix = readPrice(scanner, "Prix : ");

        JeuVideo jeu = new JeuVideo(titre, console, prix);
        stock.add(jeu);
        MagasinDB.save(jeu);
    }

    private static void addDvd(Scanner scanner, List<Article> stock) {
        String titre = readNonEmpty(scanner, "Titre : ");
        double prix = readPrice(scanner, "Prix : ");

        DVD dvd = new DVD(titre, prix);
        stock.add(dvd);
        MagasinDB.save(dvd);
    }

    private static void afficherStock(List<Article> stock) {
        // L'affichage recharge depuis la DB pour refléter l'état persistant le plus récent.
        stock.clear();
        stock.addAll(MagasinDB.load());

        System.out.println("\n--- CONTENU DU STOCK ---");
        if (stock.isEmpty()) {
            System.out.println("Stock vide.");
            return;
        }
        for (Article article : stock) {
            System.out.println(article);
        }
    }

    private static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if (value != null) {
                String trimmed = value.trim();
                if (!trimmed.isEmpty()) {
                    return trimmed;
                }
            }
            System.out.println("Entrée invalide : valeur vide.");
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un entier valide.");
            }
        }
    }

    private static double readPrice(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                double parsed = Double.parseDouble(value.trim());
                if (parsed >= 0 && !Double.isInfinite(parsed) && !Double.isNaN(parsed)) {
                    return parsed;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Prix invalide. Entrez un nombre >= 0.");
        }
    }
}
