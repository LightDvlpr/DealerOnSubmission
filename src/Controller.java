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

        List<Item> menu = this.menu.returnAllItems();
        while(true){
            int choice = view.menuOptions();

            switch(choice) {
                case 1:
                    view.display(menu);
                    int item = view.getItemNumberChoice(menu.size());
                    basket.adjustBasketState(item, this.basket, menu, this.menu);
                    break;
                case 2:
                    view.displayReceipt(basket.returnAllItems());
                    System.exit(0);
                    break;
                case 3:
                    try {
                        Item custom = view.addCustomItemToCart();
                        basket.addItemToBasket(custom);
                    } catch (Exception e) {
                        System.out.println("Try one more time. I didn't quite get that.\n");
                    }
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }
}
