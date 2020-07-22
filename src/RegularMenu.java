import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// Linter
public class RegularMenu implements MenuType {

    private File textfile;
    private static final String DELIMITER = ",";
    private List<Item> menu;
    private DecimalFormat df2 = new DecimalFormat("#.##");

    RegularMenu(File text){
        this.textfile = text;
        this.menu = new ArrayList<>();
        readFile();
    }

    @Override
    public Item getItemFromMenu(int n, List<Item> list) {
        Item answer = null;

        for(Item i: list){
            if(i.getChoiceNumber() == n){ answer = i; }
        }
        return answer;
    }

    @Override
    public List<Item> returnAllMenuItems() {
            return menu;
    }

    @Override
    public void readFile(){
        Scanner sc = null;
        try{
            sc = new Scanner(textfile);
        } catch (FileNotFoundException ex){
            System.out.println("File not found");
        }

        assert sc != null;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);

            if(parts.length == 5){
                Item i = new Item();

                i.setChoiceNumber(Integer.parseInt(parts[0]));
                i.setName(parts[1]);
                i.setPrice(Double.parseDouble(df2.format(Double.parseDouble(parts[2]))));

                if(parts[3].equalsIgnoreCase("Tax")){ i.setTaxable(true); }
                else{ i.setTaxable(false); }

                if(parts[4].equalsIgnoreCase("imprt")){ i.setImported(true); }
                else{ i.setImported(false); }
                menu.add(i);
            }
        }
    }

    @Override
    public void writeToFile(List<Item> newList) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(textfile, false));
        } catch (Exception e){
            System.out.println("Can't write to file.");
        }

        for(Item i: newList){
            assert pw != null;
            pw.append(i.getChoiceNumber() + DELIMITER + i.getName() + DELIMITER + Double.parseDouble(df2.format(Double.parseDouble(i.getPrice().toString()))) + DELIMITER
                    + i.TaxState() + DELIMITER + i.ImprtState() + "\n"
            );
        }
        assert pw != null;
        pw.flush();
        pw.close();
    }

    @Override
    public Item getLastItem(){ return menu.get(menu.size()-1); }

    @Override
    public void addItemToMenu(Item item){ menu.add(item); }

}
