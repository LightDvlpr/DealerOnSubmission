import java.util.List;

public interface MenuType {

    //This method will retrieve an item from the current menu
    Item getItemFromMenu(int n, List<Item> list);

    //This method returns us a list version of the menu that we currently have
    List<Item> returnAllMenuItems();

    //This method will add the new Item that the user may add to the menu + update the menu
    void addItemToMenu(Item item);

    //This method will read the text file of the various products
    void readFile();

    //This method is used to update the textfile if the user decides to add an item
    void writeToFile(List<Item> newList);

    //This method retrieves the last item that is on the menu.
    //The purpose of this method is so that when a user add's a new item, i can automatically assign it a choice number
    //without having to depend on the user to enter in a valid choice number
    Item getLastItem();
}
