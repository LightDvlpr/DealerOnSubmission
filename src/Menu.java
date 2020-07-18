import java.util.List;

public interface Menu {


     List<Item> displayAllItems();

     public List<Item> getCurrentMenu();

     void readFile();

     void writeToFile(List<Item> newList);

     Item getItem(int i);

     Item getLastItem();



}
