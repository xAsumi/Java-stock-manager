public class JeuVideo extends Article {
    private final String console;

    
    public JeuVideo(String titre, String console, double prix) {
        super(titre, prix);
        this.console = (console == null || console.trim().isEmpty()) ? "PC" : console.trim();
    }

    public String getConsole() {
        return console;
    }

    @Override
    public String getType() {
        return "Jeu";
    }

    @Override
    public String getSpecificDetails() {
        return console;
    }

    @Override
    public String toString() {
        return "[Jeu] " + super.toString() + " | Console : " + console;
    }
}
