public class Livre extends Article {
    private final String auteur;

    
    public Livre(String titre, String auteur, double prix) {
        super(titre, prix);
        this.auteur = (auteur == null || auteur.trim().isEmpty()) ? "Inconnu" : auteur.trim();
    }

    public String getAuteur() {
        return auteur;
    }

    @Override
    public String getType() {
        return "Livre";
    }

    @Override
    public String getSpecificDetails() {
        return auteur;
    }

    @Override
    public String toString() {
        return "[Livre] " + super.toString() + " | Auteur : " + auteur;
    }
}
