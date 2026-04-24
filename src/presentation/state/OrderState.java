package presentation.state;

import domain.entity.Money;
import domain.entity.Product;
import domain.entity.MoneyReturn;
import presentation.controller.MachineController;
import presentation.ui.ConsoleUI;
import java.util.List;
import java.util.Scanner;

public class OrderState extends State {
    
    private Scanner scanner = new Scanner(System.in);
    
    public OrderState(MachineController controller) {
        super(controller);
        
        ConsoleUI.printHeader("SELECCIONAR PRODUCTO");
        this.interactWithMenu();
    }
    
    @Override
    public void insertMoney() {
        this.controller.setState(new PaymentState(this.controller));
    }
    
    @Override
    public void interactWithMenu() {
        Money paidMoney = this.controller.getPaidMoney();
        List<Product> inventory = this.controller.getInventory();
        
        ConsoleUI.printHeader("Productos disponibles");
        int productSelector = 1;
        
        for (Product product : inventory) {
            if (product.getAmountLeft() > 0) {
                ConsoleUI.printProduct(productSelector++, product.getName(), product.getPrice());
            } else {
                ConsoleUI.printProductSoldOut(product.getName());
                productSelector++;
            }
        }
        
        ConsoleUI.printBackOption();
        ConsoleUI.printPaidStatus(paidMoney.getAmount());
        ConsoleUI.printEmptyLine();
        ConsoleUI.printPrompt();
        
        productSelector = scanner.nextInt();
        
        if (productSelector == 0) {
            this.insertMoney();
        } else if (productSelector >= 1 && productSelector <= this.controller.getNumberOfProducts()) {
            this.placeOrder(productSelector - 1);
        }
    }
    
    @Override
    public void dispenseProduct() {
    }
    
    @Override
    public void dispenseMoney() {
        Money paidMoney = this.controller.getPaidMoney();
        
        if (paidMoney.getAmount() > 0) {
            ConsoleUI.printHeader("Dinero devuelto");
            ConsoleUI.printMoneyAmount(paidMoney.getAmount());
            ConsoleUI.printMoneyBreakdown(paidMoney);
        }
    }
    
    private void placeOrder(int productIndex) {
        Product product = this.controller.getInventory().get(productIndex);
        Money paidMoney = this.controller.getPaidMoney();
        
        if (product.getAmountLeft() <= 0) {
            ConsoleUI.printError("Producto agotado.");
            ConsoleUI.printEmptyLine();
            this.interactWithMenu();
            return;
        }
        
        int paid = paidMoney.getAmount();
        int price = product.getPrice();
        
        ConsoleUI.printOrderSummary(product.getName(), price, paid);
        
        if (paid < price) {
            int falta = price - paid;
            ConsoleUI.printMissingMoney(falta);
            ConsoleUI.printHeader("OPCIONES");
            ConsoleUI.printOption(1, "Insertar mas dinero");
            ConsoleUI.printOption(2, "Cancelar y devolver dinero");
            ConsoleUI.printEmptyLine();
            ConsoleUI.printPrompt();
            
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                this.controller.setState(new PaymentState(this.controller));
            } else {
                this.cancelOrder("InsufficientMoney");
            }
            return;
        }
        
        int changeNeeded = paid - price;
        MoneyReturn returnMoney = this.controller.calculateChange(paidMoney, price);
        
        if (returnMoney.getStatus()) {
            this.acceptOrder(product, returnMoney);
        } else {
            ConsoleUI.printNoChangeError(changeNeeded);
            ConsoleUI.printHeader("OPCIONES");
            ConsoleUI.printOption(1, "Insertar mas dinero");
            ConsoleUI.printOption(2, "Cancelar y devolver dinero");
            ConsoleUI.printEmptyLine();
            ConsoleUI.printPrompt();
            
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                this.controller.setState(new PaymentState(this.controller));
            } else {
                this.cancelOrder("NoChange");
            }
        }
    }
    
    private void acceptOrder(Product product, MoneyReturn returnMoney) {
        int cambio = returnMoney.getMoney().getAmount();
        
        ConsoleUI.printOrderAccepted();
        
        if (cambio > 0) {
            ConsoleUI.printChange(cambio);
        }
        
        this.controller.updateMachineMoney("payment");
        this.controller.setReturnMoney(returnMoney);
        this.controller.setChosenProduct(product);
        this.controller.decreaseProductStock(product);
        
        this.controller.setState(new DispenseState(this.controller));
    }
    
    private void cancelOrder(String reason) {
        ConsoleUI.printOrderCancelled();
        this.controller.clearPaidMoney();
        this.controller.setState(new PaymentState(this.controller));
    }
}