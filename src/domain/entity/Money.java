package domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Money {
    private int fiftyPesosCount;
    private int oneHundredPesosCount;
    private int twoHundredPesosCount;
    private int fiveHundredPesosCount;
    private int oneThousandPesosCount;
    private static ArrayList<String> types = new ArrayList<>(List.of("50 Pesos", "100 Pesos", "200 Pesos", "500 Pesos", "1000 Pesos"));

    public Money() {
        this.reset();
    }

    Money(int fiftyPesosCount, int oneHundredPesosCount, int twoHundredPesosCount, int fiveHundredPesosCount, int oneThousandPesosCount) {
        this.fiftyPesosCount = fiftyPesosCount;
        this.oneHundredPesosCount = oneHundredPesosCount;
        this.twoHundredPesosCount = twoHundredPesosCount;
        this.fiveHundredPesosCount = fiveHundredPesosCount;
        this.oneThousandPesosCount = oneThousandPesosCount;
    }

    public static ArrayList<String> getTypes() {
        return types;
    }

    public void add(String type, int numberOfCoinsOrNotes) {
        switch(type) {
            case "50 Pesos":
                this.fiftyPesosCount += numberOfCoinsOrNotes;
                break;
            case "100 Pesos":
                this.oneHundredPesosCount += numberOfCoinsOrNotes;
                break;
            case "200 Pesos":
                this.twoHundredPesosCount += numberOfCoinsOrNotes;
                break;
            case "500 Pesos":
                this.fiveHundredPesosCount += numberOfCoinsOrNotes;
                break;
            case "1000 Pesos":
                this.oneThousandPesosCount += numberOfCoinsOrNotes;
                break;
            default:
                break;
        }
    }

    public void remove(String type, int numberOfCoinsOrNotes) {
        switch(type) {
            case "50 Pesos":
                this.fiftyPesosCount -= numberOfCoinsOrNotes;
                break;
            case "100 Pesos":
                this.oneHundredPesosCount -= numberOfCoinsOrNotes;
                break;
            case "200 Pesos":
                this.twoHundredPesosCount -= numberOfCoinsOrNotes;
                break;
            case "500 Pesos":
                this.fiveHundredPesosCount -= numberOfCoinsOrNotes;
                break;
            case "1000 Pesos":
                this.oneThousandPesosCount -= numberOfCoinsOrNotes;
                break;
            default:
                break;
        }
    }

    public int getCount(String type) {
        switch(type) {
            case "50 Pesos":
                return this.fiftyPesosCount;
            case "100 Pesos":
                return this.oneHundredPesosCount;
            case "200 Pesos":
                return this.twoHundredPesosCount;
            case "500 Pesos":
                return this.fiveHundredPesosCount;
            case "1000 Pesos":
                return this.oneThousandPesosCount;
            default:
                break;
        }
        return 0;
    }

    public int getAmount() {
        int amount = 0;
        amount += (fiftyPesosCount * 50);
        amount += (oneHundredPesosCount * 100);
        amount += (twoHundredPesosCount * 200);
        amount += (fiveHundredPesosCount * 500);
        amount += (oneThousandPesosCount * 1000);
        return amount;
    }

    public void reset() {
        this.fiftyPesosCount = 0;
        this.oneHundredPesosCount = 0;
        this.twoHundredPesosCount = 0;
        this.fiveHundredPesosCount = 0;
        this.oneThousandPesosCount = 0;
    }
}