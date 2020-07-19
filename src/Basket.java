import java.util.ArrayList;
import java.util.List;

public class Basket implements ShopInterface{

    //Basket extends the ShoppingCart interface
    //We created a list variable to hold all the items the user selects
    private List<Item> bask = new ArrayList<Item>();

    Basket(){

    }

    @Override
    public Item getItem(int n, List<Item> list) {
        Item answer = null;

        for(Item i: list){
            if(i.getChoiceNumber() == n){
                answer = i;
            }
        }
        return answer;
    }

    @Override
    public List<Item> returnAllItems() {
        return this.bask;
    }

    //if we call this method on the Basket Object then it allow us to know if the item the user choose
    //is in the menu or not
    //originally i tried just adding the item to an arraylist but since all objects have different addresses
    //I wasn't able to increment the quantity since the list.contains method only works for specific objects
    //I made this contains method to check, not the object address, but the object's choicenumber
    public boolean contains(int ite){
        boolean answer = false;

        for(Item i: bask){
            answer = i.getChoiceNumber() == ite;
        }
        return answer;
    }

    //This method allows the user to add the item to their basket
    public void adjustBasketState(int i, Basket basket, List<Item> MenuAvail, Menu object ){
        Item addOne;

        if (basket.contains(i)) {
            addOne = basket.getItemFromBasket(i);
            addOne.setQuantity(addOne.getQuantity() + 1);
        }
        else {
            addOne = object.getItem(i,MenuAvail);
            addOne.setQuantity(addOne.getQuantity() + 1);
            bask.add(addOne);
        }

        System.out.println(addOne.getName() + " added to your cart!\n");
    }

    public void addItemToBasket(Item item){
        item.setQuantity(item.getQuantity() + 1);

        bask.add(item);
    }

    //this will allow us to grab the item from the basket. I used this to be able to increment the quantity of an item
    public Item getItemFromBasket(int ite){
        Item answer = null;

        for(Item i: bask){
            if(i.getChoiceNumber() == ite){ answer = i; }
        }
        return answer;
    }
}
