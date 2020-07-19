import java.util.List;

public class Controller {

    private UserView view;
    private RegularMenu regularMenu;
    private RegularBasket regularBasket;

    public Controller(UserView view, RegularMenu regularMenu, RegularBasket regularBasket){
        this.view = view;
        this.regularMenu = regularMenu;
        this.regularBasket = regularBasket;

    }
    //This will execute the program by beginning to interact with the user
    void run(){
        List<Item> menu = this.regularMenu.returnAllMenuItems();
        while(true){
            int choice = (int) Double.parseDouble(view.menuOptions());

            switch(choice) {
                case 1:
                    view.displayMenu(menu);
                    int item = (int) Double.parseDouble(view.getItemNumberChoice(menu.size()));
                    int quantity = (int) Double.parseDouble(view.getItemQuantity());
                    regularBasket.adjustBasketState(item, quantity, this.regularBasket, menu, this.regularMenu);
                    break;
                case 2:
                    view.displayReceipt(regularBasket.returnAllBasketItems());
                    System.exit(0);
                    break;
                case 3:
                    try {
                        Item custom = view.addCustomItemToCart();
                        regularBasket.addItemToBasket(custom);
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
