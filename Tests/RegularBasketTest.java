import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class RegularBasketTest {

    private File text = new File("Products.txt");
    private RegularMenu menu = new RegularMenu(text);
    private List<Item> menuavail = menu.returnAllMenuItems();
    private Item one = new Item(1, "Book", 12.49, false, false, 2);
    private Item two = new Item(2, "Music CD", 14.99, true, false, 1);
    private Item three = new Item(3, "Chocolate Bar", 0.85, false, false, 1);
    private Item four = new Item(4, "Imported Box of Chocolates", 10.00, false, true, 1);
    private Item five = new Item(5, "Imported Bottle of Perfume", 47.50, true, true, 1);
    private Item six = new Item(6, "Imported Bottle of Perfume", 27.99, true, true, 1);
    private Item seven = new Item(7, "Bottle of Perfume", 18.99, true, false, 1);
    private Item eight = new Item(8, "Packet of Headache Pills", 9.75, false, false, 1);
    private Item nine = new Item(9, "Imported Box of Chocolates", 11.25, false, true, 2);

    private RegularBasket basket = new RegularBasket();


    @Test
    void returnAllBasketItems() {
        basket.addItemToBasket(one);
        basket.addItemToBasket(two);
        basket.addItemToBasket(three);
        basket.addItemToBasket(four);

        List<Item> items = basket.returnAllBasketItems();
        assertEquals(4, items.size());
    }

    @Test
    void contains() {
        basket.addItemToBasket(two);
        assertTrue(basket.contains(27));
    }

    @Test
    void adjustBasketState() {
        basket.addItemToBasket(one);
        basket.addItemToBasket(two);
        List<Item> cart = basket.returnAllBasketItems();

        assertEquals(2,cart.size());

        basket.adjustBasketState(2,4,basket,menuavail,menu);

        assertEquals(5,basket.getItemFromBasket(2).getQuantity());
    }

    @Test
    void addItemToBasket() {
        basket.addItemToBasket(two);
        assertEquals(2,basket.getItemFromBasket(2).getChoiceNumber());
    }

    @Test
    void getItemFromBasket() {
        basket.addItemToBasket(two);
        Item get = basket.getItemFromBasket(2);

        assertEquals(two.getChoiceNumber(),get.getChoiceNumber());
    }

    @Test
    void salesTax() {

        //Test an imported Item
        double tax1 = basket.SalesTax(four);
        BigDecimal expectedTax1 = new BigDecimal(.50);
        expectedTax1 = expectedTax1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result1 =  new BigDecimal(Math.ceil(tax1 * 20) / 20);
        result1 = result1.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax1, result1);

        //Test a Taxable item
        double tax2 = basket.SalesTax(two);
        BigDecimal expectedTax2 = new BigDecimal(1.50);
        expectedTax2 = expectedTax2.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result2 =  new BigDecimal(Math.ceil(tax2 * 20) / 20);
        result2 = result2.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax2, result2);

        //Test a nonTaxable Item
        double tax3 = basket.SalesTax(one);
        BigDecimal expectedTax3 = new BigDecimal(0);
        expectedTax3 = expectedTax3.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result3 =  new BigDecimal(Math.ceil(tax3 * 20) / 20);
        result3 = result3.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax3, result3);

        //Test an imported and Taxable Item
        double tax4 = basket.SalesTax(six);
        BigDecimal expectedTax4 = new BigDecimal(4.20);
        expectedTax4 = expectedTax4.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result4 =  new BigDecimal(Math.ceil(tax4 * 20) / 20);
        result4 = result4.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax4, result4);
    }

    @Test
    void imprtTaxtotal() {
        String tax1 = Double.toString(basket.imprtTaxtotal(four));
        String expectedValue = Double.toString(10.50);

        BigDecimal expectedTax1 = new BigDecimal(expectedValue).setScale(2,RoundingMode.DOWN);
        BigDecimal result1 =  new BigDecimal(tax1).setScale(2,RoundingMode.DOWN);

        assertEquals(expectedTax1, result1);
    }

    @Test
    void calculate() {
        BigDecimal pricetotal = new BigDecimal(4.90);
        pricetotal = pricetotal.setScale(2, RoundingMode.HALF_UP);

        BigDecimal actual = new BigDecimal(basket.Calculate(one,2.33,2.45));
        actual = actual.setScale(2, RoundingMode.HALF_UP);

        assertEquals(pricetotal,actual);
    }

    @Test
    void taxTotal() {
        String tax1 = Double.toString(basket.taxTotal(two));
        String expectedValue = Double.toString(16.49);

        BigDecimal expectedTax1 = new BigDecimal(expectedValue).setScale(2,RoundingMode.DOWN);
        BigDecimal result1 =  new BigDecimal(tax1).setScale(2,RoundingMode.DOWN);

        assertEquals(expectedTax1, result1);
    }

    @Test
    void nontaxTotal() {
        String tax1 = Double.toString(basket.nontaxTotal(one));
        String expectedValue = Double.toString(24.98);

        BigDecimal expectedTax1 =new BigDecimal(expectedValue).setScale(2,RoundingMode.DOWN);
        BigDecimal result1 =  new BigDecimal(tax1).setScale(2,RoundingMode.DOWN);

        assertEquals(expectedTax1, result1);
    }
}