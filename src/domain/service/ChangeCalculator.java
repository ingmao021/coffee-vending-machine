package domain.service;

import domain.entity.Money;
import domain.entity.MoneyReturn;
import java.util.ArrayList;

public class ChangeCalculator {
    
    public static MoneyReturn calculate(Money payment, int price, Money machineMoney) {
        boolean status = false;
        Money money = new Money();
        
        ArrayList<String> types = Money.getTypes();
        int returnMoneyAmount = payment.getAmount() - price;
        int remainder = returnMoneyAmount;
        
        for (int i = types.size() - 1; i >= 0; i--) {
            String type = types.get(i);
            int coinOrNote = Integer.parseInt(type.split(" ")[0]);
            int coinOrNoteCount = 0;
            int quotient = 0;
            
            do {
                if (remainder < coinOrNote) {
                    break;
                }
                
                quotient = remainder / coinOrNote;
                int availableCoinOrNoteCount = machineMoney.getCount(type);
                
                if (availableCoinOrNoteCount >= (coinOrNoteCount + quotient)) {
                    remainder %= coinOrNote;
                    coinOrNoteCount += quotient;
                    machineMoney.remove(type, quotient);
                } else {
                    remainder = remainder - (coinOrNote * availableCoinOrNoteCount);
                    coinOrNoteCount += availableCoinOrNoteCount;
                    machineMoney.remove(type, availableCoinOrNoteCount);
                    break;
                }
                
            } while (quotient > 0);
            
            money.add(type, coinOrNoteCount);
        }
        
        if (remainder == 0) {
            status = true;
        } else {
            money = new Money();
        }
        
        return new MoneyReturn(status, money);
    }
}