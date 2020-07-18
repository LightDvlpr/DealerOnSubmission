import java.util.ArrayList;
import java.util.List;

public class Basket implements ShoppingCart{


    //Basket extends the ShoppingCart interface
    //We created a list variable to hold all the items the user selects
    List<Item> bask = new ArrayList<Item>();


    Basket(){

    }

    //if we call this method on the Basket Object then it allow us to know if the item the user choose
    //is in the menu or not
    //originally i tried just adding the item to an arraylist but since all objects have different addresses
    //I wasn't able to increment the quantity since the list.contains method only works for specific objects
    //I made this contains method to check, not the object address, but the object's choicenumber
    public boolean contains(int ite){
        boolean answer = false;
        for(Item i: bask){
            if(i.getChoiceNumber() == ite){
                answer = true;
            }
            else{
                answer = false;
            }
        }
        return answer;
    }

    //This method allows the user to add the item to their basket
    public void addToBasket(Item i){

        bask.add(i);
    }

    //this will allow us to grab the item from the basket. I used this to be able to increment the quantity of an item
    public Item getItemFromBasket(int ite){
        Item answer = null;

        for(Item i: bask){
            if(i.getChoiceNumber() == ite){
                answer = i;
            }

        }
        return answer;
    }

    //This will return a list of the items in the basket
    public List<Item> returnBasketItems(){

        return bask;
    }


}
