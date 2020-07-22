import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

class UserView {

    private Scanner sc = new Scanner(System.in);
    private RegularMenu Menu;
    private RegularBasket basket;
    private DecimalFormat df2 = new DecimalFormat("#.##");


    UserView(RegularMenu Menu, RegularBasket basket){
        this.Menu = Menu;
        this.basket = basket;
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
        int Quantity;

        int choiceNum = Menu.getLastItem().getChoiceNumber() + 1;

        do{
            System.out.println("Please enter in the name of your Item.");
            name = sc.nextLine();
        }
        while(name.isBlank());

        do{
            System.out.println("Please enter the price of your Item. It's not free! ");
            price = Double.parseDouble(df2.format(Double.parseDouble(sc.nextLine())));
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

        Quantity = (int) Double.parseDouble(this.getItemQuantity());
        Item custom = new Item(choiceNum, name, price, Tax, imprt, Quantity);

        custom.fixImprt();

        Menu.addItemToMenu(custom);

        Menu.writeToFile(Menu.returnAllMenuItems());

        System.out.println(custom.getQuantity() + " " + custom.getName() + " is in your cart and now our menu. Thank you!!\n");
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
        System.out.println();
        for(Item i: menu){
            System.out.println(i.getChoiceNumber() + " " + i.getName() + " " + i.getPrice());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //This method will be called when the user decides to check out. It will display the receipt.
    void displayReceipt(RegularBasket basket){
        List<Item> cart = this.basket.returnAllBasketItems();

        double total = 0.0;
        double totalSalesTax = 0.0;

        for(Item i: cart){
            totalSalesTax += basket.SalesTax(i);

            if(i.isTaxable() && i.isImport()){
                total += basket.imprtandTaxtotal(i);
            }
            else if(i.isTaxable()){
                total += basket.taxTotal(i);
            }
            else if(i.isImport()){
                total += basket.imprtTaxtotal(i);
            }
            else{
                total += basket.nontaxTotal(i);
            }
        }

        String solidTotal = Double.toString(total);

        BigDecimal result =  new BigDecimal(solidTotal);
        result = result.setScale(2, RoundingMode.DOWN);

        BigDecimal result2 =  new BigDecimal(Math.ceil(totalSalesTax * 20) / 20);
        result2 = result2.setScale(2, RoundingMode.HALF_UP);

        System.out.println("Sales Taxes: " + result2);
        System.out.println("Your total cost is: " + result);
    }
}
