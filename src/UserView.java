import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class UserView {

    // Creating a Scanner to read/Write to text file
    private Scanner sc = new Scanner(System.in);
    //menu object will be used in order to get the items scanned by our scanner
    private Menu menu;

    //Tax rates are created here so that if we decide to change we can just change these variables
    private double imprtTax = 5/100d;
    private double regTax = 10/100d;

    //UserView class takes menu as a parameter in order to have the list of items available in scope
    UserView(Menu menu){
        this.menu = menu;
    }

    //This will be the default list of choices available to the user.
    // We will be calling this in our Controller.run method
    public int menuOptions(){

        System.out.print("1.) Purchase an Item.\n");
        System.out.print("2.) Complete transaction\n");
        System.out.print("3.) Enter in a specific product.\n");
        System.out.print("4.) Leave store (Items will be placed back on shelf)\n");
        System.out.println();
        //readUserChoice allows us to keep this menu in loop in case
        // the user chooses something other than the 4 numbers
        return readUserChoice(1, 4);

    }

    //As per the pdf, this method will allow a user to input their own item
    //The item will be added to their cart as well as the menu
    public Item addCustomItemToCart(){

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
        System.out.println("Please enter in the name of your Item.");
        name = sc.nextLine();

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

        //Grab the current version of the menu
        List<Item> list = this.menu.getCurrentMenu();

        //add custom the the latest version of the menu
        list.add(custom);
        //writeToFile will erase what is currently on the text file and add in our new list with the new item
        menu.writeToFile(list);
        System.out.println( custom.getName() + " has been added to your cart as well as our menu. Thank you!!\n");
        //We return the item so that we can add it to the user's basket in the Controller class
        return custom;
    }

    //This method will allow the user to pick the item via the assigned choice number
    public int getItemNumberChoice(int min, int max){
        int choice;

        do {
            System.out.println("Please enter your Item's Choice number: ");
            choice = Integer.parseInt(sc.nextLine());
        }
        while(choice < min || choice > max);
        return choice;
    }

    //This method is what we use a means to make sure the user chooses a number within range
    public int readUserChoice(int min, int max){
        int value;

        do {
            System.out.print("Please Select your option's number :-> ");
            value = Integer.parseInt(sc.nextLine());

        }
        while(value < min || value > max);
        return value;

    }

    //This will allow us to display the menu for the user
    public void display(List<Item> menu){
        System.out.println("");
        for(Item i: menu){
            System.out.println(i.getChoiceNumber() + " " + i.getName() + " " + i.getPrice());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //Calculation for the user's selected Items will be performed here
    public void displayReceipt(List<Item> cart){
        System.out.println("Printing your receipt\n");
        //total will hold the entire value of the cart
        //totalSalesTax will hold the value of the sales Tax
        double total = 0.0;
        double totalSalesTax = 0.0;

        for(Item i: cart){
            //For each item, we'll call the SalesTax method to calculate the total tax an item will have
            //each loop will allow us to keep adding to the totalSalesTax variable
            totalSalesTax += SalesTax(i);

            //If an item is taxable then it will be put into the taxTotal() method
            //This will return the whole value of the item after it is taxed and multiplied by it's quantity
            if(i.isTaxable()){
               total = total + taxTotal(i);
            }
            //If an item is imported then the same will be done via the imprtTaxtotal() method
            else if (i.isImport()){
                total = total + imprtTaxtotal(i);

            } else{
                //otherwise it's a regular item
                total = total + nontaxTotal(i);

            }

        }

        //Both result2 and result allow us to take the value of total and totalSalesTax and round to the nearest 5 cents
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
        //import tax
        if(i.isImport()){
            tax = (i.getPrice() * i.getQuantity()) * imprtTax;
        }
        //regular tax
        else if(i.isTaxable()){
            tax = (i.getPrice() * i.getQuantity()) * regTax;
        }

        return tax;
    }

    //Calculation for getting the total price for an imported item
    private double imprtTaxtotal(Item i){
        //we assign variables to get a more neater understanding of what is being outputted
        double total;
        double price = i.getPrice();
        double priceAfterTax = i.getPrice() + i.getPrice() * imprtTax;
        int quantity = i.getQuantity();
        double totalPriceForItem = priceAfterTax * quantity;

        //rounding to the nearest 5 cents here as well since we are outputting each item by itself as well
        BigDecimal tPFI =  new BigDecimal(Math.ceil(totalPriceForItem * 20) / 20);
        tPFI = tPFI.setScale(2, RoundingMode.HALF_UP);

        //I noticed in the pdf that a item will a quantity greater than 1 gets a different output.
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
        //we assign variables to get a more neater understanding of what is being outputted
        double total;
        double priceAfterTax = i.getPrice() + i.getPrice() * regTax;
        double price = i.getPrice();
        int quantity = i.getQuantity();
        double totalPriceForItem = priceAfterTax * quantity;

        //rounding to the nearest 5 cents here as well since we are outputting each item by itself as well
        BigDecimal tPFI =  new BigDecimal(Math.ceil(totalPriceForItem * 20) / 20);
        tPFI = tPFI.setScale(2, RoundingMode.HALF_UP);

        //Quantity greater than 1 gets a unique output
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
