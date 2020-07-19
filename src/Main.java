import java.io.File;

public class Main {

    public static void main(String[] args ) {

        //I wanted to make the code terminal friendly
        //input parameters on this project take product.txt as the one and only parameter
        //it is passed into the text variable and then passed into menu for parsing

       File text = new File(args[0]);
       Menu menu = new Menu(text);
       Basket Cart = new Basket();
       UserView user = new UserView(menu);
       Controller controller = new Controller(user, menu, Cart);

       //Lets go shopping.
       controller.run();

    }
}