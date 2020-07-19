import java.io.File;

public class Main {

    public static void main(String[] args ) {

       File text = new File(args[0]);
       RegularMenu regularMenu = new RegularMenu(text);
       RegularBasket Cart = new RegularBasket();
       UserView user = new UserView(regularMenu);
       Controller controller = new Controller(user, regularMenu, Cart);

       //Lets go shopping.
       controller.run();

    }
}