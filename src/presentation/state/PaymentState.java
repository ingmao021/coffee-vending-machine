package presentation.state;

import domain.entity.Money;
import domain.entity.Product;
import presentation.controller.MachineController;
import presentation.ui.ConsoleUI;
import java.util.List;
import java.util.Scanner;

public class PaymentState extends State {
    
    private Scanner scanner = new Scanner(System.in);
    private Money payment;
    
    public PaymentState(MachineController controller) {
        super(controller);
        this.payment = controller.getPaidMoney();
        showMenu();
    }
    
    private void showMenu() {
        this.showProducts();
        this.showPaidStatus();
        this.showPaymentOptions();
    }
    
    private void showProducts() {
        ConsoleUI.printHeader("PRODUCTOS DISPONIBLES");
        List<Product> inventory = this.controller.getInventory();
        int i = 1;
        for (Product product : inventory) {
            if (product.getAmountLeft() > 0) {
                ConsoleUI.printProduct(i++, product.getName(), product.getPrice());
            } else {
                ConsoleUI.printProductSoldOut(product.getName());
                i++;
            }
        }
        ConsoleUI.printEmptyLine();
    }
    
    private void showPaidStatus() {
        ConsoleUI.printPaidStatus(this.payment.getAmount());
    }
    
    private void showPaymentOptions() {
        ConsoleUI.printHeader("OPCIONES");
        ConsoleUI.printOption(1, "Insertar dinero");
        ConsoleUI.printOption(2, "Elegir producto");
        if (this.payment.getAmount() > 0) {
            ConsoleUI.printOption(3, "Cancelar y devolver");
        }
        ConsoleUI.printEmptyLine();
        ConsoleUI.printPrompt();
        
        int choice = scanner.nextInt();
        
if (choice == 1) {
            this.showInsertMoneyDialog();
        } else if (choice == 2) {
            this.proceedToOrder();
        } else if (choice == 3 && this.payment.getAmount() > 0) {
            this.cancel();
        } else {
            ConsoleUI.printError("Opcion invalida.");
            ConsoleUI.printEmptyLine();
            this.showMenu();
        }
    }
    
    private void showInsertMoneyDialog() {
        ConsoleUI.printHeader("Seleccione monto");
        ConsoleUI.printOption(1, "$1.000");
        ConsoleUI.printOption(2, "$2.000");
        ConsoleUI.printOption(3, "$5.000");
        ConsoleUI.printOption(4, "$10.000");
        ConsoleUI.printOption(5, "$20.000");
        ConsoleUI.printOption(6, "$50.000");
        ConsoleUI.printOption(0, "Volver");
        ConsoleUI.printEmptyLine();
        ConsoleUI.printPrompt();
        
        int choice = scanner.nextInt();
        
        if (choice == 0) {
            ConsoleUI.printEmptyLine();
            this.showMenu();
            return;
        }
        
        int amount = 0;
        int bills = 0;
        
        switch (choice) {
            case 1: amount = 1000; bills = 1; break;
            case 2: amount = 2000; bills = 2; break;
            case 3: amount = 5000; bills = 5; break;
            case 4: amount = 10000; bills = 10; break;
            case 5: amount = 20000; bills = 20; break;
            case 6: amount = 50000; bills = 50; break;
            default:
                ConsoleUI.printError("Opcion invalida.");
                this.showInsertMoneyDialog();
                return;
        }
        
        this.payment.add("1000 Pesos", bills);
        ConsoleUI.printMoneyAdded(amount);
        ConsoleUI.printEmptyLine();
        
        this.showMenu();
    }
    
    private void proceedToOrder() {
        if (this.payment.getAmount() == 0) {
            ConsoleUI.printError("Debe insertar dinero primero.");
            ConsoleUI.printEmptyLine();
            this.showMenu();
            return;
        }
        
        this.controller.setPaidMoney(payment);
        this.controller.setState(new OrderState(this.controller));
    }
    
    private void cancel() {
        this.dispenseMoney();
    }
    
    private void reset() {
        this.payment = new Money();
        this.showMenu();
    }
    
    @Override
    public void insertMoney() {
        this.showMenu();
    }
    
    @Override
    public void interactWithMenu() {
        this.showMenu();
    }
    
    @Override
    public void dispenseProduct() {
    }
    
    @Override
    public void dispenseMoney() {
        if (this.payment.getAmount() > 0) {
            ConsoleUI.printHeader("Dinero devuelto");
            ConsoleUI.printMoneyAmount(this.payment.getAmount());
            ConsoleUI.printMoneyBreakdown(this.payment);
            ConsoleUI.printEmptyLine();
        }
        
        this.reset();
    }
}