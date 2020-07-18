public class Main {

    public static void main(String[] args) {


       Menu menu = new MenuImplementation();
       Basket bought = new Basket();
       UserView user = new UserView(menu);
       Controller controller = new Controller(user, menu, bought);

       controller.run();

    }
}