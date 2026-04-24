package presentation.controller;

import domain.entity.Money;
import domain.entity.Product;
import domain.entity.MoneyReturn;
import domain.repository.ProductRepository;
import domain.repository.MoneyRepository;
import domain.service.ChangeCalculator;
import infrastructure.persistence.FileProductRepository;
import infrastructure.persistence.FileMoneyRepository;
import presentation.state.State;
import presentation.state.PaymentState;
import java.util.ArrayList;
import java.util.List;

public class MachineController {
    
    private static MachineController instance;
    
    private State state;
    private Money paidMoney;
    private Money machineMoney;
    private MoneyReturn returnMoney;
    private List<Product> inventory;
    private Product chosenProduct;
    private ProductRepository productRepository;
    private MoneyRepository moneyRepository;
    
    private MachineController() {
        this.paidMoney = new Money();
        this.returnMoney = new MoneyReturn(false, new Money());
        this.productRepository = new FileProductRepository();
        this.moneyRepository = new FileMoneyRepository();
        loadInventory();
        loadMachineMoney();
    }
    
    public static MachineController getInstance() {
        if (instance == null) {
            instance = new MachineController();
        }
        return instance;
    }
    
    private void loadInventory() {
        this.inventory = new ArrayList<>(productRepository.findAll());
    }
    
    private void loadMachineMoney() {
        this.machineMoney = moneyRepository.findMachineMoney();
    }
    
    public void start() {
        System.out.println();
        System.out.println("==================================");
        System.out.println("   MAQUINA EXPENDEDORA DE CAFE    ");
        System.out.println("==================================");
        System.out.println();
        
        this.setState(new PaymentState(this));
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public State getState() {
        return this.state;
    }
    
    public Money getPaidMoney() {
        return this.paidMoney;
    }
    
    public void setPaidMoney(Money money) {
        this.paidMoney = money;
    }
    
    public void clearPaidMoney() {
        this.paidMoney = new Money();
    }
    
    public MoneyReturn getReturnMoney() {
        return this.returnMoney;
    }
    
    public void setReturnMoney(MoneyReturn moneyReturn) {
        this.returnMoney = moneyReturn;
    }
    
    public void clearReturnMoney() {
        this.returnMoney = new MoneyReturn(false, new Money());
    }
    
    public List<Product> getInventory() {
        return inventory;
    }
    
    public int getNumberOfProducts() {
        return inventory.size();
    }
    
    public Product getChosenProduct() {
        return chosenProduct;
    }
    
    public void setChosenProduct(Product product) {
        this.chosenProduct = product;
    }
    
    public Money getMachineMoney() {
        return machineMoney;
    }
    
    public void updateMachineMoney(String scenario) {
        if (scenario.equals("payment")) {
            for (String type : Money.getTypes()) {
                this.machineMoney.add(type, this.paidMoney.getCount(type));
            }
            this.moneyRepository.save(this.machineMoney);
        } else if (scenario.equals("return")) {
            Money returnMon = this.returnMoney.getMoney();
            for (String type : Money.getTypes()) {
                this.machineMoney.remove(type, returnMon.getCount(type));
            }
            this.moneyRepository.save(this.machineMoney);
        }
    }
    
    public void decreaseProductStock(Product product) {
        for (Product p : this.inventory) {
            if (p.getName().equals(product.getName())) {
                p.decrementAmountLeft();
                break;
            }
        }
        this.productRepository.saveAll(this.inventory);
    }
    
    public MoneyReturn calculateChange(Money payment, int price) {
        return ChangeCalculator.calculate(payment, price, this.machineMoney);
    }
}