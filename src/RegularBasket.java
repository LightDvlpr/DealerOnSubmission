import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RegularBasket implements BasketType{

    private List<Item> bask = new ArrayList<>();
    File menu;

    private String i = Double.toString(5/100d);
    private String rT= Double.toString(10/100d);
    private String iART = Double.toString(15/100d);

    private BigDecimal importTax = new BigDecimal(i).setScale(2,RoundingMode.DOWN);
    private BigDecimal regularTax = new BigDecimal(rT).setScale(2,RoundingMode.DOWN);
    private BigDecimal importAndRegTax = new BigDecimal(iART).setScale(2,RoundingMode.DOWN);
    private BigDecimal noTax = new BigDecimal(0).setScale(2,RoundingMode.DOWN);

    RegularBasket(){

    }

    RegularBasket(File menu){
        this.menu = menu;
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
    public double imprtandTaxtotal(Item i) {
        return m(i, importAndRegTax);
    }

    @Override
    public double imprtTaxtotal(Item i) {
        return m(i, importTax);
    }

    @Override
    public double taxTotal(Item i) {
        return m(i, regularTax);
    }

    @Override
    public double nontaxTotal(Item i) {
        return m(i,noTax);
    }

    @Override
    public double SalesTax(Item i) {
        double tax = 0.0;
        double roundedTax;
        double importedAndTaxable = importAndRegTax.doubleValue();
        double imported = importTax.doubleValue();
        double Taxable = regularTax.doubleValue();

        if(i.isTaxable() && i.isImport()){
            roundedTax = i.getPrice() * importedAndTaxable;
            BigDecimal result2 =  new BigDecimal(Math.ceil(roundedTax * 20) / 20);
            roundedTax = result2.setScale(2, RoundingMode.HALF_UP).doubleValue();

            tax = roundedTax * i.getQuantity();
        }
        else if(i.isImport()){
            roundedTax = i.getPrice() * imported;
            BigDecimal result2 =  new BigDecimal(Math.ceil(roundedTax * 20) / 20);
            roundedTax = result2.setScale(2, RoundingMode.HALF_UP).doubleValue();

            tax = roundedTax * i.getQuantity();
        }
        else if(i.isTaxable()){
            roundedTax = i.getPrice() * Taxable;
            BigDecimal result2 =  new BigDecimal(Math.ceil(roundedTax * 20) / 20);
            roundedTax = result2.setScale(2, RoundingMode.HALF_UP).doubleValue();

            tax = roundedTax * i.getQuantity();
        }
        return tax;
    }

    @Override
    public double Calculate(Item i, double price, double priceAfterTax) {
        double total;
        int quantity = i.getQuantity();
        double totalPriceForItem = priceAfterTax * quantity;

        String solidPrice = Double.toString(totalPriceForItem);

        BigDecimal totalPriceForItemCorrect = new BigDecimal(solidPrice);
        totalPriceForItemCorrect = totalPriceForItemCorrect.setScale(2,RoundingMode.DOWN);

        if(quantity > 1){
            total = totalPriceForItem;
            System.out.println(i.getName() + ": " + totalPriceForItemCorrect + " (" + quantity + " @ " + priceAfterTax + ")");
        }
        else{
            total = totalPriceForItem;
            System.out.println(i.getName() + ": " + totalPriceForItemCorrect);
        }
        return total;
    }

    private double m(Item i, BigDecimal Tax) {
        double price = i.getPrice();
        double salesTax = price * Tax.doubleValue();

        BigDecimal roundedTax =  new BigDecimal(Math.ceil(salesTax * 20) / 20);
        roundedTax = roundedTax.setScale(2, RoundingMode.HALF_UP);

        double priceAfterTax = new BigDecimal(roundedTax.doubleValue() + price).doubleValue();

        DecimalFormat df = new DecimalFormat("#.##");
        priceAfterTax = Double.parseDouble(df.format(priceAfterTax));

        return Calculate(i, price, priceAfterTax);
    }
}
