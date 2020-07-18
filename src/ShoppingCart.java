import java.util.List;

public interface ShoppingCart {


    //This interface made shopping easier
    //I commented how I used each of the below methods in the Basket Class
    public boolean contains(int ite);

    public void addToBasket(Item i);

    public Item getItemFromBasket(int ite);

    public List<Item> returnBasketItems();




}
