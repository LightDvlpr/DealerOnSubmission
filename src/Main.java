import java.io.File;

public class Main {

    public static void main(String[] args ) {

        //I wanted to make the code terminal friendly
        //input parameters on this project take product.txt as the one and only parameter
        //it is passed into the text variable and then passed into menu for parsing
       File text = new File(args[0]);
       Menu menu = new MenuImplementation(text);
       //we instantiate Cart as the users cart for shopping
       Basket Cart = new Basket();
       //view will be what we use to interact with the user
       UserView user = new UserView(menu);
       //Controller will allow us to run the program
       Controller controller = new Controller(user, menu, Cart);

       //Lets go shopping.
       controller.run();

    }
}