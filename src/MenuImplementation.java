import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuImplementation implements Menu {

    private static String FILENAME = "Products.txt";
    private static final String DELIMITER = ",";
    private List<Item> menu = new ArrayList<Item>();
    private List<Item> currentMenu = new ArrayList<>();

    MenuImplementation(){

    }

    @Override
    public List<Item> displayAllItems() {
            readFile();
            currentMenu = menu;
            return menu;
    }

    @Override
    public List<Item> getCurrentMenu(){
        return currentMenu;
    }


    @Override
    public void readFile(){

        Scanner sc = null;
        try{
            sc = new Scanner(new File(FILENAME));
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

                menu.add(i);
            }
        }
    }

    @Override
    public void writeToFile(List<Item>newList) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(new File(FILENAME), false));
        } catch (Exception e){
            System.out.println("Can't write to file.");
        }

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

    @Override
    public Item getLastItem(){

        return menu.get(menu.size()-1);

    }
}
