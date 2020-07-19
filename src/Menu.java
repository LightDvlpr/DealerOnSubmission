import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements ShopInterface {

    //We have textfile which will be products.txt, our Delimitter to parse through the text file
    //menu will be the list to read the text file
    //currentMenu will be the list to hold the current state of the menu list so that we can call it
    //instead of calling for menu which will cause the file to be read twice
    private File textfile;
    private static final String DELIMITER = ",";
    private List<Item> menu;

    Menu(File text){
        this.textfile = text;
        this.menu = new ArrayList<>();
        readFile();
    }

    @Override
    public Item getItem(int n, List<Item> list) {
        Item answer = null;

        for(Item i: list){
            if(i.getChoiceNumber() == n){ answer = i; }
        }
        return answer;
    }

    @Override
    public List<Item> returnAllItems() {
            return menu;
    }

    private void readFile(){
        Scanner sc = null;
        try{
            sc = new Scanner(textfile);
        } catch (FileNotFoundException ex){
            System.out.println("File not found");
        }

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);

            if(parts.length == 5){
                Item i = new Item();

                i.setChoiceNumber(Integer.parseInt(parts[0]));
                i.setName(parts[1]);
                i.setPrice((Double.parseDouble(parts[2])));

                if(parts[3].equalsIgnoreCase("Tax")){ i.setTaxable(true); }
                else{ i.setTaxable(false); }

                if(parts[4].equalsIgnoreCase("imprt")){ i.setImported(true); }
                else{ i.setImported(false); }
                menu.add(i);
            }
        }
    }

    void writeToFile(List<Item> newList) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(textfile, false));
        } catch (Exception e){
            System.out.println("Can't write to file.");
        }

        for(Item i: newList){
            assert pw != null;
            pw.append(i.getChoiceNumber() + DELIMITER + i.getName() + DELIMITER + i.getPrice() + DELIMITER
                    + i.TaxState() + DELIMITER + i.ImprtState() + "\n"
            );
        }
        assert pw != null;
        pw.flush();
        pw.close();
    }

    //get the last item off the menu so that we can give the user's custom item the correct number
    Item getLastItem(){ return menu.get(menu.size()-1); }

    void addToMenu(Item item){ menu.add(item); }

}
