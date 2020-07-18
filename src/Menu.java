import java.util.List;

public interface Menu {
     //Menu interface was built to make the code more stable.
     //In the case that we want to build a different menu we can just create a new menuimplemention class
     //implement this interface and start from there

     //This method will allow us to return all the items on the Menu as a list
     List<Item> returnAllItems();

     //This method will allow us to keep track of the menu's current state
     public List<Item> getCurrentMenu();

     //reads the text file
     void readFile();

     //Writes to the text file
     void writeToFile(List<Item> newList);

     //Grabs the item from the menu via it's respective choice number
     Item getItem(int i);

     //Grabs last item off the menu. I made this method to make adding a new item to the menu easier for the user
     Item getLastItem();



}
