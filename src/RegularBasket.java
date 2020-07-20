import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RegularBasket implements BasketType{

    private List<Item> bask = new ArrayList<>();
    private double imprtTax = 5/100d;
    private double regTax = 10/100d;

    RegularBasket(){

    }

    @Override
    public List<Item> returnAllBasketItems() {
        return this.bask;
    }

    @Override
    public boolean contains(int ite){
        boolean answer = false;

        for(Item i: bask){
            answer = i.getChoiceNumber() == ite;
        }
        return answer;
    }

    @Override
    public void adjustBasketState(int i, int quantity, RegularBasket regularBasket, List<Item> MenuAvail, RegularMenu menu ){
        Item addOne;

        if (regularBasket.contains(i)) {
            addOne = regularBasket.getItemFromBasket(i);
            addOne.setQuantity(addOne.getQuantity() + quantity);
        }
        else {
            addOne = menu.getItemFromMenu(i,MenuAvail);
            addOne.setQuantity(quantity);
            bask.add(addOne);
        }
        System.out.println("You now have " + addOne.getQuantity() + " " + addOne.getName() + " in your cart!\n");
    }

    @Override
    public void addItemToBasket(Item item){
        bask.add(item);
    }

    @Override
    public Item getItemFromBasket(int ite){
        Item answer = null;

        for(Item i: bask){
            if(i.getChoiceNumber() == ite){ answer = i; }
        }
        return answer;
    }

    @Override
    public double SalesTax(Item i) {
        double tax = 0.0;

        if(i.isImport()){ tax = (i.getPrice() * i.getQuantity()) * imprtTax; }
        else if(i.isTaxable()){ tax = (i.getPrice() * i.getQuantity()) * regTax; }
        return tax;
    }

    @Override
    public double imprtTaxtotal(Item i) {
        double price = i.getPrice();
        double priceAfterTax = i.getPrice() + i.getPrice() * imprtTax;
        return Calculate(i, price, priceAfterTax);
    }

    @Override
    public double Calculate(Item i, double price, double priceAfterTax) {
        double total;
        int quantity = i.getQuantity();
        double totalPriceForItem = priceAfterTax * quantity;

        BigDecimal tPFI =  new BigDecimal(Math.ceil(totalPriceForItem * 20) / 20);
        tPFI = tPFI.setScale(2, RoundingMode.HALF_UP);

        if(quantity > 1){
            total = totalPriceForItem;
            System.out.println(i.getName() + ": " + tPFI + " (" + quantity + " @ " + price + " )");
        }
        else{
            total = totalPriceForItem;
            System.out.println(i.getName() + ": " + i.getPrice());
        }
        return total;
    }

    @Override
    public double taxTotal(Item i) {
        double priceAfterTax = i.getPrice() + i.getPrice() * regTax;
        double price = i.getPrice();
        return Calculate(i, price, priceAfterTax);
    }

    @Override
    public double nontaxTotal(Item i) {
        double priceAfterTax = i.getPrice() * 1;
        double price = i.getPrice();
        return Calculate(i, price, priceAfterTax);
    }


}
