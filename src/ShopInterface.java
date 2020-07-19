import java.util.List;

public interface ShopInterface {

    Item getItem(int n, List<Item> list);
    List<Item> returnAllItems();

}
