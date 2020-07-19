import java.util.List;

public interface MenuType {

    Item getItemFromMenu(int n, List<Item> list);

    List<Item> returnAllMenuItems();

    void addItemToMenu(Item item);

    void readFile();

    void writeToFile(List<Item> newList);

    Item getLastItem();
}
