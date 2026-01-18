public class Livre extends Article {
    private String auteur;

    
    public Livre(String titre, String auteur, double prix) {
        super(titre, prix); 
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return "[Livre] " + super.toString() + " | Auteur : " + this.auteur;
    }
}