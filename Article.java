import java.io.Serializable;
import java.util.Objects;

public abstract class Article implements Serializable {
    private final String titre;
    private final double prix;

    public Article(String titre, double prix) {
        String normalizedTitre = Objects.requireNonNull(titre, "Le titre ne peut pas être null").trim();
        if (normalizedTitre.isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (Double.isNaN(prix) || Double.isInfinite(prix) || prix < 0) {
            throw new IllegalArgumentException("Le prix doit être un nombre valide >= 0");
        }
        this.titre = normalizedTitre;
        this.prix = prix;
    }

    public String getTitre() {
        return titre;
    }

    public double getPrix() {
        return prix;
    }

    public abstract String getType();

    public String getDisplayType() {
        return getType();
    }

    public String getSpecificDetails() {
        return "-";
    }

    @Override
    public String toString() {
        return "Titre : " + titre + " | Prix : " + prix + " DH";
    }
}
