import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

class UserView {

    private Scanner sc = new Scanner(System.in);
    private Menu menu;
    private double imprtTax = 5/100d;
    private double regTax = 10/100d;

    UserView(Menu menu){
        this.menu = menu;
    }

    int menuOptions(){
        System.out.print("1.) Purchase an Item.\n");
        System.out.print("2.) Complete transaction\n");
        System.out.print("3.) Enter in a specific product.\n");
        System.out.print("4.) Leave store (Items will be placed back on shelf)\n");
        System.out.println();
        return readUserChoice();
    }

    Item addCustomItemToCart(){

        String name;
        double price;
        boolean Tax;
        boolean imprt;
        String taxOrNot;
        String imprtOrNot;

        //choiceNum gets the last choicenumber that is present on the list
        //Increments it by one so the user doesn't need to check what the last number is
        int choiceNum = menu.getLastItem().getChoiceNumber() + 1;


        //Item name can be anything. No need to set restrictions
        do{
            System.out.println("Please enter in the name of your Item.");
            name = sc.nextLine();
        }
        while(name.isEmpty());

        //We do want the user to only enter in a double value hence this do / while loop
        do{
            System.out.println("Please enter the price of your Item. It's not free! ");
            price = Double.parseDouble(sc.nextLine());
        }
        while(price <= 0 );

        //We also want the user to enter in either Yes or No. Nothing else.
        do{
            System.out.println("Type Yes if your item belongs in any of the following categories - > Food, Books or Medical Supplies?");
            System.out.println("Type No otherwise \n");
            taxOrNot = sc.nextLine();

        } while (!taxOrNot.equalsIgnoreCase("Yes") && !taxOrNot.equalsIgnoreCase("No"));

        Tax = taxOrNot.equalsIgnoreCase("Yes");

        //Same for import. No answer besides Yes or No.
        do{
            System.out.println("Type Yes if your item is imported. Type No if not.");
            imprtOrNot = sc.nextLine();
        }while(!imprtOrNot.equalsIgnoreCase("Yes") && !imprtOrNot.equalsIgnoreCase("No"));

        imprt = imprtOrNot.equalsIgnoreCase("Yes");


        //custom is our new Item, we assign the parameters that the user gave us
        Item custom = new Item(choiceNum, name, price, Tax, imprt);
        //If the item was imported then custom.fixImport() will add "Imported " to the Item's name
        custom.fixImprt();

        menu.addToMenu(custom);

        //writeToFile will erase what is currently on the text file and add in our new list with the new item
        menu.writeToFile(menu.returnAllItems());

        System.out.println( custom.getName() + " has been added to your cart as well as our menu. Thank you!!\n");
        //We return the item so that we can add it to the user's basket in the Controller class
        return custom;
    }

    //This method will allow the user to pick the item via the assigned choice number
    int getItemNumberChoice(int max){
        int choice;
        do {
            System.out.println("Please enter your Item's Choice number: ");
            choice = Integer.parseInt(sc.nextLine());
        }
        while(choice < 1 || choice > max);
        return choice;
    }

    //This method is what we use a means to make sure the user chooses a number within range
    private int readUserChoice(){
        int value;
        do {
            System.out.print("Please Select your option's number :-> ");
            value = Integer.parseInt(sc.nextLine());
        }
        while(value < 1 || value > 4);
        return value;
    }

    //This will allow us to display the menu for the user
    void display(List<Item> menu){
        System.out.println("");
        for(Item i: menu){
            System.out.println(i.getChoiceNumber() + " " + i.getName() + " " + i.getPrice());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //Calculation for the user's selected Items will be performed here
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

    //SalesTax only returns a double of the SalesTax on a particular item
    private double SalesTax(Item i){
        double tax = 0.0;

        if(i.isImport()){ tax = (i.getPrice() * i.getQuantity()) * imprtTax; }
        else if(i.isTaxable()){ tax = (i.getPrice() * i.getQuantity()) * regTax; }
        return tax;
    }

    //Calculation for getting the total price for an imported item
    private double imprtTaxtotal(Item i){
        double price = i.getPrice();
        double priceAfterTax = i.getPrice() + i.getPrice() * imprtTax;
        return Calculate(i, price, priceAfterTax);
    }

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

    //Calculation for getting the total price for an item that is taxable
    private double taxTotal(Item i){
        double priceAfterTax = i.getPrice() + i.getPrice() * regTax;
        double price = i.getPrice();
        return Calculate(i, price, priceAfterTax);

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
