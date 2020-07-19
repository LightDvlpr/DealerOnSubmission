abstract class Product {

    //I made a abstract product class so that in case we decide to add different types of Items
    //We can extend this class and adjust the methods accordingly

    public abstract int getChoiceNumber();

    public abstract  void setChoiceNumber(int choiceNumber);

    public abstract  String getName();

    public abstract void setName(String newName);

    public abstract Double getPrice();

    public abstract void setPrice(Double newPrice);

    public abstract int getQuantity();

    public abstract void setQuantity(int newQuantity);
}
