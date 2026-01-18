import java.util.ArrayList;
import java.util.Scanner;

public class Magasin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Article> stock = new ArrayList<>();

        System.out.println("=== LOGIN ===");
        boolean connecte = false;
        while (!connecte) {
            System.out.print("User  : ");
            String user = sc.nextLine();
            System.out.print("Pass  : ");
            String pass = sc.nextLine();

            if (user.equals("admin") && pass.equals("3103")) {
                connecte = true;
                System.out.println(">> Connexion reussie !");
            } else {
                System.out.println(">> Erreur d'identifiants");
            }
        }

        int choix = 0;
        while (choix != 5) {
            System.out.println("\n--- La GESTION DE VOTRE MAGASIN ---");
            System.out.println("1. Ajouter un Livre");
            System.out.println("2. Ajouter un Jeu Video");
            System.out.println("3. Ajouter un DVD");
            System.out.println("4. Afficher le stock");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Titre : ");
                    String tL = sc.nextLine();
                    System.out.print("Auteur : ");
                    String aL = sc.nextLine();
                    System.out.print("Prix : ");
                    double pL = sc.nextDouble(); sc.nextLine();

                    Livre livre = new Livre(tL, aL, pL);
                    stock.add(livre);
                    MagasinDB.save(livre);
                    break;

                case 2:
                    System.out.print("Titre : ");
                    String tJ = sc.nextLine();
                    System.out.print("Console : ");
                    String cJ = sc.nextLine();
                    System.out.print("Prix : ");
                    double pJ = sc.nextDouble(); sc.nextLine();

                    JeuVideo jeu = new JeuVideo(tJ, cJ, pJ);
                    stock.add(jeu);
                    MagasinDB.save(jeu);
                    break;

                case 3:
                    System.out.print("Titre : ");
                    String tD = sc.nextLine();
                    System.out.print("Prix : ");
                    double pD = sc.nextDouble(); sc.nextLine();

                    DVD dvd = new DVD(tD, pD);
                    stock.add(dvd);
                    MagasinDB.save(dvd);
                    break;

                case 4:
                    stock = MagasinDB.load();
                    System.out.println("\n--- CONTENU DU STOCK ---");
                    for (Article a : stock) {
                        System.out.println(a);
                    }
                    break;

                case 5:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
        sc.close();
        System.out.println("Programme terminé.");
    }
}