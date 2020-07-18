public class Item extends Product {
    private int choiceNumber;
    private String Name;
    private Double Price;
    private boolean Taxable;
    private int Quantity = 0;
    private boolean imported;

    //This is our item class. The Item object keeps the whole application organized
    //We are able to use this object to be able to call different options via the assigned parameters

    Item(){

    }

    public Item(int choiceNum, String name, double price, boolean tax, boolean imprt) {
        this.choiceNumber = choiceNum;
        this.Name = name;
        this.Price = price;
        this.Taxable = tax;
        this.imported = imprt;
    }

    //I assigned each option a choice number. This made calling the Item easier for me and for the user
    //Instead of writing the whole name you just need to input the item's assigned number and you're good to go
    @Override
    public int getChoiceNumber() {
        return choiceNumber;
    }

    @Override
    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;

    }

    //This method allows us to print the name of each item
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String newName) {
        Name = newName;

    }

    //grab the price of each item
    @Override
    public Double getPrice() {
        return Price;
    }

    @Override
    public void setPrice(Double newPrice) {
        Price = newPrice;
    }

    //How much of each item is in the cart
    @Override
    public int getQuantity() {
        return Quantity;
    }

    @Override
    public void setQuantity(int newQuantity) {
        Quantity = newQuantity;
    }

    //IF it is taxable or not
    public boolean isTaxable() {
        return Taxable;
    }

    //If it is taxable then we add the Tax String to the text file
    public String TaxState(){
        String answer;
        if(this.isTaxable()){
            answer = "Tax";
        }
        else{
            answer = "Nontax";
        }
        return answer;
    }

    public void setTaxable(boolean taxable) {

        this.Taxable = taxable; }

    //If the item is imported
    public boolean isImport(){
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    //If the item is imported then we add "Imported " to the beginning of the name
    public void fixImprt(){

        if(this.isImport()){
            setName("Imported " + getName());
        }
    }



    //This will be added to the text file to classify it as an imported item
    public String ImprtState(){
        String answer;
        if(this.isImport()){
            answer = "Imprt";
        }
        else{
            answer = "nonimprt";
        }
        return answer;
    }


}
