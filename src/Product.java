abstract class Product {


    //Originally I decided to make a concreate Item class and that's it
    //the reason I decided to make an abstract class was because the pdf mentioned two different items
    //While I didn't extend this to two different classes I wanted to show you that I know how to make sure that
    //code can be reusable. This class will allow us to add different items to the application. All we need to do is
    //create the class, extend this class towards it and then be able to add or remove whatever we need
    public abstract int getChoiceNumber();

    public abstract  void setChoiceNumber(int choiceNumber);

    public abstract  String getName();

    public abstract void setName(String newName);

    public abstract Double getPrice();

    public abstract void setPrice(Double newPrice);

    public abstract int getQuantity();

    public abstract void setQuantity(int newQuantity);

}
