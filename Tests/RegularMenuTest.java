import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

class RegularMenuTest {
    File text = new File("Products.txt");
    RegularMenu menu = new RegularMenu(text);
    List<Item> menuavail = menu.returnAllMenuItems();

    @Test
    void getItemFromMenu() {
        Item bought = menu.getItemFromMenu(1,menuavail);
        assertEquals(1, bought.getChoiceNumber());
    }

    @Test
    //Adjust the integer to the size of the actual menu in case you add an item
    void returnAllMenuItems() {
        assertEquals(26, menuavail.size());
    }

    @Test
    void getLastItem() {
        Item last = menu.getLastItem();
        int lastis = menu.getLastItem().getChoiceNumber();
        assertEquals(lastis,last.getChoiceNumber());
    }

    @Test
    void addItemToMenu() {
        Item newItem =  new Item(26, "Cream", 2.33, false, true, 1);
        menu.addItemToMenu(newItem);

        assertEquals(26, newItem.getChoiceNumber());
    }
}