import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

class UserView {

    private Scanner sc = new Scanner(System.in);
    private RegularMenu regularMenu;
    private double imprtTax = 5/100d;
    private double regTax = 10/100d;

    UserView(RegularMenu regularMenu){
        this.regularMenu = regularMenu;
    }

    //Shows the user the menu options
    String menuOptions(){
        System.out.print("1.) Purchase an Item.\n");
        System.out.print("2.) Complete transaction\n");
        System.out.print("3.) Enter in a specific product.\n");
        System.out.print("4.) Leave store (Items will be placed back on shelf)\n");
        System.out.println();
        return readUserChoice();
    }

    //If a user would like to enter in a custom item, this method will be called
    Item addCustomItemToCart(){
        String name;
        double price;
        boolean Tax;
        boolean imprt;
        String taxOrNot;
        String imprtOrNot;

        int choiceNum = regularMenu.getLastItem().getChoiceNumber() + 1;

        do{
            System.out.println("Please enter in the name of your Item.");
            name = sc.nextLine();
        }
        while(name.isEmpty());

        do{
            System.out.println("Please enter the price of your Item. It's not free! ");
            price = Double.parseDouble(sc.nextLine());
        }
        while(price <= 0 );

        do{
            System.out.println("Type Yes if your item belongs in any of the following categories - > Food, Books or Medical Supplies?");
            System.out.println("Type No otherwise \n");
            taxOrNot = sc.nextLine();

        } while (!taxOrNot.equalsIgnoreCase("Yes") && !taxOrNot.equalsIgnoreCase("No"));

        Tax = taxOrNot.equalsIgnoreCase("Yes");

        do{
            System.out.println("Type Yes if your item is imported. Type No if not.");
            imprtOrNot = sc.nextLine();
        }while(!imprtOrNot.equalsIgnoreCase("Yes") && !imprtOrNot.equalsIgnoreCase("No"));

        imprt = imprtOrNot.equalsIgnoreCase("Yes");

        Item custom = new Item(choiceNum, name, price, Tax, imprt);

        custom.fixImprt();

        regularMenu.addItemToMenu(custom);

        regularMenu.writeToFile(regularMenu.returnAllMenuItems());

        System.out.println( custom.getName() + " has been added to your cart as well as our menu. Thank you!!\n");
        return custom;
    }

    //When a user chooses an item of the menu, this method wll be called
    String getItemNumberChoice(int max){
        String choice;
        do {
            System.out.println("Please enter your Item's Choice number: ");
            choice = sc.nextLine();
        }
        while(choice.trim().matches("[a-zA-Z]+") || choice.isBlank() || (int) Double.parseDouble(choice) < 1 || (int) Double.parseDouble(choice) > max);
        return choice;
    }

    //This method will prompt the user to enter a quantity for the item they chose
    String getItemQuantity(){
        String quantity;

        do{
            System.out.println("How much of this item would you like? Please enter a number: ");
            quantity = sc.nextLine();
        }
        while(quantity.trim().matches("[a-zA-Z]+") || quantity.isBlank() || (int) Double.parseDouble(quantity) <=0);
        return quantity;
    }

    //This method allows us to read what the user input
    private String readUserChoice(){
        String value;
        do {
            System.out.print("Please Select your option's number :-> ");
            value = sc.nextLine();
        }
        while(value.trim().matches("[a-zA-Z]+") || value.isBlank() || (int) Double.parseDouble(value) > 4 || (int) Double.parseDouble(value) < 1);
        return value;
    }

    //This method will display the current menu for the user
    void displayMenu(List<Item> menu){
        System.out.println("");
        for(Item i: menu){
            System.out.println(i.getChoiceNumber() + " " + i.getName() + " " + i.getPrice());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //This method will be called when the user decides to check out. It will display the receipt.
    void displayReceipt(List<Item> cart){
        System.out.println("Printing your receipt\n");
        double total = 0.0;
        double totalSalesTax = 0.0;

        for(Item i: cart){
            totalSalesTax += SalesTax(i);

            if(i.isTaxable()){ total += taxTotal(i); }
            else if (i.isImport()){ total += imprtTaxtotal(i); }
            else{ total += nontaxTotal(i); }
        }

        BigDecimal result =  new BigDecimal(Math.ceil(total * 20) / 20);
        result = result.setScale(2, RoundingMode.HALF_UP);

        BigDecimal result2 =  new BigDecimal(Math.ceil(totalSalesTax * 20) / 20);
        result2 = result2.setScale(2, RoundingMode.HALF_UP);

        System.out.println("Sales Taxes: " + result2);
        System.out.println("Your total cost is:  " + result);
    }

    //This method calculates the sales tax
    private double SalesTax(Item i){
        double tax = 0.0;

        if(i.isImport()){ tax = (i.getPrice() * i.getQuantity()) * imprtTax; }
        else if(i.isTaxable()){ tax = (i.getPrice() * i.getQuantity()) * regTax; }
        return tax;
    }

    //This method calculates the price of an imported item
    private double imprtTaxtotal(Item i){
        double price = i.getPrice();
        double priceAfterTax = i.getPrice() + i.getPrice() * imprtTax;
        return Calculate(i, price, priceAfterTax);
    }

    //This method calculates general pricing
    private double Calculate(Item i, double price, double priceAfterTax) {
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

    //Calculates the price of a taxable item
    private double taxTotal(Item i){
        double priceAfterTax = i.getPrice() + i.getPrice() * regTax;
        double price = i.getPrice();
        return Calculate(i, price, priceAfterTax);

    }

    //Calculates the price of a general item
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
