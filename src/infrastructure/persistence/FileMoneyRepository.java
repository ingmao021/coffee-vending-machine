package infrastructure.persistence;

import domain.entity.Money;
import domain.repository.MoneyRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileMoneyRepository implements MoneyRepository {
    
    private static final String FILE_PATH = Paths.get("", "data", "money.txt").toString();
    private Money machineMoney;
    
    public FileMoneyRepository() {
        this.machineMoney = loadFromFile();
    }
    
    private Money loadFromFile() {
        Money money = new Money();
        File file = new File(FILE_PATH);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] coinOrNoteDetails = scanner.nextLine().split(",");
                String coinOrNoteType = coinOrNoteDetails[0] + " Pesos";
                int coinOrNoteCount = Integer.parseInt(coinOrNoteDetails[1]);
                money.add(coinOrNoteType, coinOrNoteCount);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return money;
    }
    
    @Override
    public Money findMachineMoney() {
        return machineMoney;
    }
    
    @Override
    public void update(Money money) {
        this.machineMoney = money;
    }
    
    @Override
    public void save(Money money) {
        this.machineMoney = money;
        saveToFile();
    }
    
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new File(FILE_PATH))) {
            for (String type : Money.getTypes()) {
                int count = machineMoney.getCount(type);
                if (count > 0) {
                    String denomination = type.split(" ")[0];
                    writer.println(denomination + "," + count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}