import java.io.File;

public class Main {

    public static void main(String[] args ) {

        if(args.length != 1) {
            System.err.println("Usage: java Main <input text file> ");
            return;
        }

       File text = new File(args[0]);
       RegularMenu regularMenu = new RegularMenu(text);
       RegularBasket Cart = new RegularBasket();
       UserView user = new UserView(regularMenu, Cart);
       Controller controller = new Controller(user, regularMenu, Cart);

       //Lets shop.
       controller.run();

    }
}