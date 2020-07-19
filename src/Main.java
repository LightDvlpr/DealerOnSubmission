import java.io.File;

public class Main {

    public static void main(String[] args ) {

       File text = new File(args[0]);
//       File text = new File("Products.txt");
       RegularMenu regularMenu = new RegularMenu(text);
       RegularBasket Cart = new RegularBasket();
       UserView user = new UserView(regularMenu, Cart);
       Controller controller = new Controller(user, regularMenu, Cart);

       //Lets go shopping.
       controller.run();

    }
}