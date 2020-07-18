import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuImplementation implements Menu {

    //We have textfile which will be products.txt, our Delimitter to parse through the text file
    //menu will be the list to read the text file
    //currentMenu will be the list to hold the current state of the menu list so that we can call it
    //instead of calling for menu which will cause the file to be read twice
    private File textfile;
    private static final String DELIMITER = ",";
    private List<Item> menu = new ArrayList<Item>();
    private List<Item> currentMenu = new ArrayList<>();

    //pass in the text file as a parameter
    MenuImplementation(File text){
        this.textfile = text;
    }

    //return all the items that are in the text file as a list
    @Override
    public List<Item> returnAllItems() {
            readFile();
            currentMenu = menu;
            return menu;
    }

    //get current state of menu
    @Override
    public List<Item> getCurrentMenu(){
        return currentMenu;
    }

    //read from the text file
    @Override
    public void readFile(){
        //we instantiate a scanner variable
        Scanner sc = null;
        try{
            //check to see if the file exists
            sc = new Scanner(textfile);
        } catch (FileNotFoundException ex){
            System.out.println("File not found");

        }
        //While scanning, we check each line
        while(sc.hasNextLine()){

            //assign each line to a variable, parse the variable via the delimitter
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);
            //Current length is 5 but we can make this better by not assigning a specifc length but making it
            //more dynamic in case some items have more info than others
            if(parts.length == 5){
                Item i = new Item();

                i.setChoiceNumber(Integer.parseInt(parts[0]));
                i.setName(parts[1]);
                i.setPrice((Double.parseDouble(parts[2])));
                if(parts[3].equalsIgnoreCase("Tax")){
                    i.setTaxable(true);
                } else{
                    i.setTaxable(false);
                }
                if(parts[4].equalsIgnoreCase("imprt")){
                    i.setImported(true);
                } else{
                    i.setImported(false);
                }
                //add each item to our menu list
                menu.add(i);
            }
        }
    }

    //this method will allow us to take the user's unique checkout item and write to the menu list
    @Override
    public void writeToFile(List<Item>newList) {
        //create a printwriter variable
        PrintWriter pw = null;
        try {
            //make sure we can write to file. the false parameter tells FileWriter to erase whatever is in the file
            //so we don't write the same things twice
            pw = new PrintWriter(new FileWriter(textfile, false));
        } catch (Exception e){
            System.out.println("Can't write to file.");
        }

        //for each item, parse like such
        for(Item i: newList){
            assert pw != null;
            pw.append(i.getChoiceNumber() + DELIMITER
                    + i.getName() + DELIMITER
                    + i.getPrice() + DELIMITER +
                    i.TaxState() + DELIMITER +
                    i.ImprtState() + "\n");
        }

        pw.flush();
        pw.close();
    }


    //this method allows us to get an item from the menu via the choice number
    @Override
    public Item getItem(int it) {

        Item answer = null;

        for(Item i: menu){
            if(i.getChoiceNumber() == it){
                answer = i;
            }
        }
        return answer;
    }

    //get the last item off the menu so that we can give the user's custom item the correct number
    @Override
    public Item getLastItem(){

        return menu.get(menu.size()-1);

    }
}
