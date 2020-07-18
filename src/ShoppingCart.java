import java.util.List;

public interface ShoppingCart {


    public boolean contains(int ite);

    public List<Item> addToBasket(Item i);

    public Item getItemFromBasket(int ite);

    public List<Item> returnBasketItems();



}
