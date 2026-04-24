package domain.repository;

import domain.entity.Money;

public interface MoneyRepository {
    Money findMachineMoney();
    void update(Money money);
    void save(Money money);
}