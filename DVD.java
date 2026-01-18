public class DVD extends Article {
    
   
    public DVD(String titre, double prix) {
        super(titre, prix);
    }


    @Override
    public String toString() {
        return "[DVD] " + super.toString();
    }
}