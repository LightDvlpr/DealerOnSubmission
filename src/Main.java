import java.io.File;

public class Main {

    public static void main(String[] args ) {

       File text = new File(args[0]);
       Menu menu = new MenuImplementation(text);
       Basket bought = new Basket();
       UserView user = new UserView(menu);
       Controller controller = new Controller(user, menu, bought);

       controller.run();

    }
}