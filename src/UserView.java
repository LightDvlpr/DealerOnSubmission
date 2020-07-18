import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private Scanner sc = new Scanner(System.in);
    private Menu menu;

    private double imprtTax = 5/100d;
    private double regTax = 10/100d;

    UserView(Menu menu){
        this.menu = menu;
    }

    public int menuOptions(){

        System.out.print("1.) Purchase an Item.\n");
        System.out.print("2.) Complete transaction\n");
        System.out.print("3.) Enter in a specific product.\n");
        System.out.print("4.) Leave store (Items will be placed back on shelf)\n");
        System.out.println();
        return readUserChoice(1, 4);

    }

    public Item addCustomItemToCart(){

        String name;
        double price;
        boolean Tax;
        boolean imprt;
        int choiceNum = menu.getLastItem().getChoiceNumber() + 1;

        System.out.println("Please enter in the name of your Item.");
        name = sc.nextLine();

        System.out.println("Please enter the price of your Item.");
        price = Double.parseDouble(sc.nextLine());

        System.out.println("Type Yes if your item belongs in any of the following categories - > Food, Books or Medical Supplies?");
        System.out.println("Type No otherwise \n");
        String answer = sc.nextLine();

        if(answer.equalsIgnoreCase("Yes")){
            Tax = true;
        }else{
            Tax = false;
        }

        System.out.println("Type Yes if your item is imported. Type No if not.");
        String answer2 = sc.nextLine();

        if(answer2.equalsIgnoreCase("Yes")){
            imprt = true;
        }else{
            imprt = false;
        }

        Item custom = new Item(choiceNum, name, price, Tax, imprt);
        custom.fixImprt();

        List<Item> list = this.menu.getCurrentMenu();

        list.add(custom);
        menu.writeToFile(list);
        System.out.println("The item has been added to your cart as well as our menu. Thank you!!\n");
        return custom;
    }

    public int getItemNumberChoice(){
        int choice;

        System.out.println("Please enter your Item's Choice number: ");
        choice = Integer.parseInt(sc.nextLine());

        return choice;
    }

    public int readUserChoice(int min, int max){
        int value;

        do {
            System.out.print("Please Select a Menu Option:-> ");
            value = Integer.parseInt(sc.nextLine());

        }
        while(value < min || value > max);
        return value;

    }

    public void display(List<Item> menu){
        System.out.println("");
        for(Item i: menu){
            System.out.println(i.getChoiceNumber() + " " + i.getName() + " " + i.getPrice());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void displayReceipt(List<Item> cart){
        System.out.println("Printing your receipt");
        double total = 0.0;
        double totalSalesTax = 0.0;

        for(Item i: cart){
            totalSalesTax += SalesTax(i);

            if(i.isTaxable()){
               total = total + taxTotal(i);
            }
            else if (i.isImport()){
                total = total + imprtTaxtotal(i);

            } else{
                total = total + nontaxTotal(i);

            }

        }

        BigDecimal result =  new BigDecimal(Math.ceil(total * 20) / 20);
        result = result.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result2 =  new BigDecimal(Math.ceil(totalSalesTax * 20) / 20);
        result2 = result2.setScale(2, RoundingMode.HALF_UP);

        System.out.println("Sales Taxes: " + result2);
        System.out.println(" Your total cost is:  " + result);

    }

    private double SalesTax(Item i){
        double tax = 0.0;
        //multiply by quantity
        if(i.isImport()){
            tax = (i.getPrice() * i.getQuantity()) * imprtTax;
        }
        else if(i.isTaxable()){
            tax = (i.getPrice() * i.getQuantity()) * regTax;
        }

        return tax;
    }

    private double imprtTaxtotal(Item i){
        double total;


        double price = i.getPrice();
        double priceAfterTax = i.getPrice() + i.getPrice() * imprtTax;

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

    private double taxTotal(Item i){
        double total;
        double priceAfterTax = i.getPrice() + i.getPrice() * regTax;
        double price = i.getPrice();

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

    private double nontaxTotal(Item i){
        double total;
        double price = i.getPrice();
        int quantity = i.getQuantity();
        double Totalprice = i.getPrice() * quantity;

        BigDecimal tP =  new BigDecimal(Math.ceil(Totalprice * 20) / 20);
        tP = tP.setScale(2, RoundingMode.HALF_UP);

        if(i.getQuantity() > 1){
            total = Totalprice;
            System.out.println(i.getName() + ": " + tP + " (" + quantity + " @ " + price + " )");
        }
        else{
            total = Totalprice;
            System.out.println(i.getName() + ": " + price);
        }

        return total;


    }



}
