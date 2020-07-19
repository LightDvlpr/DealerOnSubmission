import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.assertEquals;

class RegularBasketTest {
    File text = new File("Products.txt");
    RegularMenu menu = new RegularMenu(text);
    List<Item> menuavail = menu.returnAllMenuItems();
    Item one = new Item(26, "Cream", 2.33, false, true, 1);
    Item two = new Item(27, "cheese", 22.13, true, false, 1);
    Item three = new Item(28, "onions", 3.33, true, false, 1);
    Item four = new Item(29, "peppers", 8.33, false, false, 1);
    RegularBasket basket = new RegularBasket();


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

        assertEquals(true,basket.contains(27));

    }

    @Test
    void adjustBasketState() {
        basket.addItemToBasket(one);
        basket.addItemToBasket(two);
        List<Item> cart = basket.returnAllBasketItems();

        assertEquals(2,cart.size());

        basket.adjustBasketState(26,4,basket,menuavail,menu);

        assertEquals(4,basket.getItemFromBasket(26).getQuantity());


    }

    @Test
    void addItemToBasket() {

        basket.addItemToBasket(two);

        assertEquals(27,basket.getItemFromBasket(27).getChoiceNumber());

    }

    @Test
    void getItemFromBasket() {
        basket.addItemToBasket(two);
        Item get = basket.getItemFromBasket(27);

        assertEquals(two.getChoiceNumber(),get.getChoiceNumber());
    }

    @Test
    void salesTax() {
        Double tax1 = basket.SalesTax(one);
        BigDecimal expectedTax1 = new BigDecimal(.15);
        expectedTax1 = expectedTax1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result1 =  new BigDecimal(Math.ceil(tax1 * 20) / 20);
        result1 = result1.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax1, result1);

        Double tax2 = basket.SalesTax(two);
        BigDecimal expectedTax2 = new BigDecimal(2.25);
        expectedTax2 = expectedTax2.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result2 =  new BigDecimal(Math.ceil(tax2 * 20) / 20);
        result2 = result2.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax2, result2);

        Double tax3 = basket.SalesTax(four);
        BigDecimal expectedTax3 = new BigDecimal(0);
        expectedTax3 = expectedTax3.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result3 =  new BigDecimal(Math.ceil(tax3 * 20) / 20);
        result3 = result3.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax3, result3);






    }

    @Test
    void imprtTaxtotal() {
        Double tax1 = basket.imprtTaxtotal(one);
        BigDecimal expectedTax1 = new BigDecimal(2.45);
        expectedTax1 = expectedTax1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result1 =  new BigDecimal(Math.ceil(tax1 * 20) / 20);
        result1 = result1.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax1, result1);
    }

    @Test
    void calculate() {

        BigDecimal pricetotal = new BigDecimal(2.45);
        pricetotal = pricetotal.setScale(2, RoundingMode.HALF_UP);

        BigDecimal actual = new BigDecimal(basket.Calculate(one,2.33,2.45));
        actual = actual.setScale(2, RoundingMode.HALF_UP);


        assertEquals(pricetotal,actual);
    }

    @Test
    void taxTotal() {

        Double tax1 = basket.imprtTaxtotal(one);

        BigDecimal expectedTax1 = new BigDecimal(2.45);
        expectedTax1 = expectedTax1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result1 =  new BigDecimal(Math.ceil(tax1 * 20) / 20);
        result1 = result1.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax1, result1);


    }

    @Test
    void nontaxTotal() {
        Double tax1 = basket.nontaxTotal(four);

        BigDecimal expectedTax1 = new BigDecimal(8.35);
        expectedTax1 = expectedTax1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result1 =  new BigDecimal(Math.ceil(tax1 * 20) / 20);
        result1 = result1.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTax1, result1);

    }
}