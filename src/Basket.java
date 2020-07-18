import java.util.ArrayList;
import java.util.List;

public class Basket implements ShoppingCart{

    List<Item> bask = new ArrayList<Item>();

    Basket(){

    }


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

    public List<Item> addToBasket(Item i){

        bask.add(i);
        return bask;
    }

    public Item getItemFromBasket(int ite){
        Item answer = null;

        for(Item i: bask){
            if(i.getChoiceNumber() == ite){
                answer = i;
            }

        }
        return answer;
    }

    public List<Item> returnBasketItems(){

        return bask;
    }


}
