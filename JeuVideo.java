public class JeuVideo extends Article {
    private String console;

    
    public JeuVideo(String titre, String console, double prix) {
        super(titre, prix);
        this.console = console;
    }

    @Override
    public String toString() {
        return "[Jeu] " + super.toString() + " | Console : " + this.console;
    }
}