import java.util.List;

public interface BasketType {

    Product getItemFromBasket(int ite);

    List<Item> returnAllBasketItems();

    void addItemToBasket(Item item);

    boolean contains(int ite);

    void adjustBasketState(int i, int quantity, RegularBasket regularBasket, List<Item> MenuAvail, RegularMenu object );
}
