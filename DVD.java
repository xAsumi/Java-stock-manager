public class DVD extends Article {
    
   
    public DVD(String titre, double prix) {
        super(titre, prix);
    }

    @Override
    public String getType() {
        return "DVD";
    }

    @Override
    public String getSpecificDetails() {
        return "-";
    }

    @Override
    public String toString() {
        return "[DVD] " + super.toString();
    }
}
