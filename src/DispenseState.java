import java.util.ArrayList;

public class DispenseState extends State {

    private Machine machine;

    public DispenseState(Machine machine) {
        this.machine = machine;

        System.out.println();
        System.out.println("=== PREPARANDO ===");
        System.out.println("=================");
        System.out.println();
        
        this.prepareProduct();
    }
    
    @Override
    public void insertMoney() {
        this.machine.clearPaidMoney();
        this.machine.setState(new PaymentState(machine));
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
        Money returnMoney = this.machine.getReturnMoney().getMoney();
        
        if(returnMoney.getAmount() > 0) {
            System.out.println("Devolución:");
            System.out.println("----------");
            System.out.println(returnMoney.getAmount() + " Pesos");
            
            String stringFormat = "  %s: %d%n";
            for(String type : Money.getTypes()) {
                int count = returnMoney.getCount(type);
                if(count > 0) {
                    System.out.printf(stringFormat, type, count);
                }
            }
            System.out.println();
            
            this.machine.updateMachineMoney("return");
            this.machine.clearReturnMoney();
        }
        
        Product product = this.machine.getChosenProduct();
        
        System.out.println("Preparando " + product.getName() + "...");
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        
        int tiempo = product.getPreparationTime();
        for(int i = 0; i <= tiempo; i++) {
            int porcentaje = (i * 100) / tiempo;
            System.out.print("\r[" + "=".repeat(i * 20 / tiempo) + ".".repeat(20 - i * 20 / tiempo) + "] " + porcentaje + "%");
            try {
                Thread.sleep(500);
            } catch(Exception e) {}
        }
        
        System.out.println();
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println("[DISPONIBLE]");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        
        System.out.print(ConsoleColor.CYAN_BOLD);
        System.out.println("Retire su " + product.getName());
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        
        this.finish();
    }
    
    private void finish() {
        System.out.println("Gracias por su compra!");
        System.out.println("=====================");
        System.out.println();
        
        this.insertMoney();
    }
}