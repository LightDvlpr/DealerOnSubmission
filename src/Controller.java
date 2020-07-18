import java.util.List;

public class Controller {

    UserView view;
    Menu menu;
    Basket basket;

    public Controller(UserView view, Menu menu, Basket basket){
        this.view = view;
        this.menu = menu;
        this.basket = basket;

    }

    public void run(){

        List<Item> menu = this.menu.displayAllItems();
        while(true){
            try{
                int choice = view.menuOptions();

                switch(choice){
                    case 1:
                        view.display(menu);
                        int item = view.getItemNumberChoice();

                        if(basket.contains(item)){
                            Item addOne = basket.getItemFromBasket(item);
                            addOne.setQuantity(addOne.getQuantity() + 1);
                        }
                        else{
                            basket.addToBasket(this.menu.getItem(item));
                            Item temp = basket.getItemFromBasket(item);
                            temp.setQuantity(temp.getQuantity() + 1);
                        }
                        break;
                    case 2:
                        view.displayReceipt(basket.returnBasketItems());
                        System.exit(0);
                        break;

                    case 3:
                        try{
                            Item custom = view.addCustomItemToCart();
                            basket.addToBasket(custom);
                        }catch(Exception e){
                            System.out.println("Try one more time. I didn't quite get that.\n");
                        }
                        break;
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
