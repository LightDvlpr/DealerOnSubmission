import java.util.List;

public class Controller {
    //We instantiate our variables for the controller method
    //view will be used for user display features, menu will be used to get items available
    //basket will be used to hold the user's items
    private UserView view;
    private Menu menu;
    private Basket basket;

    //This constructor allows us to pass in the variables we instantiated in the Main class
    public Controller(UserView view, Menu menu, Basket basket){
        this.view = view;
        this.menu = menu;
        this.basket = basket;

    }

    //run will execute program
    public void run(){

        //menu will be assigned all the items in the text file as a list
        List<Item> menu = this.menu.returnAllItems();
        //While we run this program, display the following
        while(true){
            try{
                //choice will hold the user's menu option selection
                int choice = view.menuOptions();

                //depending on what the user chooses we implement a switch statement
                switch(choice){
                    //case 1 is if a user would like to add an item to the cart
                    case 1:
                        //the user will be shown the current menu
                        view.display(menu);
                        //their choice will then be assigned to an int variable called item
                        int item = view.getItemNumberChoice(menu.get(0).getChoiceNumber(),menu.size());

                        //if the basket contains the item they selected already then it won't be added
                        //instead the quantity will be incremented
                        if(basket.contains(item)){
                            Item addOne = basket.getItemFromBasket(item);
                            addOne.setQuantity(addOne.getQuantity() + 1);
                        }
                        //otherwise the item will be added to the basket if it is not already present
                        else{
                            basket.addToBasket(this.menu.getItem(item));
                            Item temp = basket.getItemFromBasket(item);
                            temp.setQuantity(temp.getQuantity() + 1);
                        }
                        System.out.println( basket.getItemFromBasket(item).getName() + " added to your cart!\n");
                        break;
                    //case 2 is chosen if the user would like to check out whatever items they have
                    case 2:
                        //once selected they will be given a receipt and the program will terminate
                        view.displayReceipt(basket.returnBasketItems());
                        System.exit(0);
                        break;
                    //case 3 is chosen if the user would like to check out a specific item not on the menu
                    case 3:
                        try{
                            Item custom = view.addCustomItemToCart();
                            basket.addToBasket(custom);
                        }catch(Exception e){
                            System.out.println("Try one more time. I didn't quite get that.\n");
                        }
                        break;
                    //case 4 will allow the user to exit the program
                    case 4:
                        System.exit(0);
                }
            }
            catch(Exception e){
                System.out.println("Something went wrong. Try again.\n");
            }
        }
    }
}
