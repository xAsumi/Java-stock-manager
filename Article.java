import java.io.Serializable;

public abstract class Article implements Serializable {
    protected String titre;
    protected double prix; 

    public Article(String titre, double prix) {
        this.titre = titre;
        this.prix = prix;
    }


    public double getPrixLocation() {
        return this.prix;
    }

    @Override
    public String toString() {
        return "Titre : " + this.titre + " | Prix : " + this.prix + " DH";
    }
}