import java.util.ArrayList;
import java.util.List;

public class RegularBasket implements BasketType{

    private List<Item> bask = new ArrayList<Item>();

    RegularBasket(){

    }

    @Override
    public List<Item> returnAllBasketItems() {
        return this.bask;
    }

    @Override
    public boolean contains(int ite){
        boolean answer = false;

        for(Item i: bask){
            answer = i.getChoiceNumber() == ite;
        }
        return answer;
    }

    @Override
    public void adjustBasketState(int i, int quantity, RegularBasket regularBasket, List<Item> MenuAvail, RegularMenu menu ){
        Item addOne;

        if (regularBasket.contains(i)) {
            addOne = regularBasket.getItemFromBasket(i);
            addOne.setQuantity(addOne.getQuantity() + quantity);
        }
        else {
            addOne = menu.getItemFromMenu(i,MenuAvail);
            addOne.setQuantity(quantity);
            bask.add(addOne);
        }
        System.out.println(addOne.getQuantity() + " " + addOne.getName() + " added to your cart!\n");
    }

    @Override
    public void addItemToBasket(Item item){
        item.setQuantity(item.getQuantity() + 1);

        bask.add(item);
    }

    @Override
    public Item getItemFromBasket(int ite){
        Item answer = null;

        for(Item i: bask){
            if(i.getChoiceNumber() == ite){ answer = i; }
        }
        return answer;
    }
}
