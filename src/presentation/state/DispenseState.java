package presentation.state;

import domain.entity.Money;
import domain.entity.Product;
import presentation.controller.MachineController;
import presentation.ui.ConsoleUI;

public class DispenseState extends State {
    
    public DispenseState(MachineController controller) {
        super(controller);
        
        ConsoleUI.printHeader("PREPARANDO");
        this.prepareProduct();
    }
    
    @Override
    public void insertMoney() {
        this.controller.clearPaidMoney();
        this.controller.setState(new PaymentState(this.controller));
    }
    
    @Override
    public void interactWithMenu() {
    }
    
    @Override
    public void dispenseProduct() {
    }
    
    @Override
    public void dispenseMoney() {
    }
    
    private void prepareProduct() {
        Money returnMoney = this.controller.getReturnMoney().getMoney();
        
        if (returnMoney.getAmount() > 0) {
            ConsoleUI.printHeader("Devolucion");
            ConsoleUI.printMoneyAmount(returnMoney.getAmount());
            ConsoleUI.printMoneyBreakdown(returnMoney);
            ConsoleUI.printEmptyLine();
            
            this.controller.updateMachineMoney("return");
            this.controller.clearReturnMoney();
        }
        
        Product product = this.controller.getChosenProduct();
        
        ConsoleUI.printPreparing(product.getName());
        ConsoleUI.printProgressBar(product.getPreparationTime());
        
        ConsoleUI.printDispenserAvailable();
        ConsoleUI.printEmptyLine();
        ConsoleUI.printTakeProduct(product.getName());
        ConsoleUI.printEmptyLine();
        
        this.finish();
    }
    
    private void finish() {
        ConsoleUI.printThankYou();
        ConsoleUI.printEmptyLine();
        
        this.insertMoney();
    }
}