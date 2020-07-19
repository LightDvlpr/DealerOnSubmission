public class Item extends Product {
    private int choiceNumber;
    private String Name;
    private Double Price;
    private boolean Taxable;
    private int Quantity = 0;
    private boolean imported;

    //The Item object is what maintains the menu as well as the User's basket's organization.
    //I've given each item a specific choice number so that the user only needs to pick a number
    Item(){

    }

    public Item(int choiceNum, String name, double price, boolean tax, boolean imprt, int Quantity) {
        this.choiceNumber = choiceNum;
        this.Name = name;
        this.Price = price;
        this.Taxable = tax;
        this.imported = imprt;
        this.Quantity = Quantity;
    }

    @Override
    public int getChoiceNumber() {
        return choiceNumber;
    }

    @Override
    public void setChoiceNumber(int choiceNumber) { this.choiceNumber = choiceNumber; }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String newName) { Name = newName; }

    @Override
    public Double getPrice() {
        return Price;
    }

    @Override
    public void setPrice(Double newPrice) {
        Price = newPrice;
    }

    @Override
    public int getQuantity() {
        return Quantity;
    }

    @Override
    public void setQuantity(int newQuantity) {
        Quantity = newQuantity;
    }

    boolean isTaxable() {
        return Taxable;
    }

    void setTaxable(boolean taxable) { this.Taxable = taxable; }

    boolean isImport(){
        return imported;
    }

    void setImported(boolean imported) {
        this.imported = imported;
    }

    String TaxState(){
        String answer;
        if(this.isTaxable()){ answer = "Tax"; }
        else{ answer = "Nontax"; }
        return answer;
    }

    String ImprtState(){
        String answer;
        if(this.isImport()){ answer = "Imprt"; }
        else{ answer = "nonimprt"; }
        return answer;
    }

    void fixImprt(){
        if(this.isImport()){ setName("Imported " + getName()); }
    }

}
