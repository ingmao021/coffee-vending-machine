import java.util.ArrayList;
import java.util.Scanner;

public class PaymentState extends State {
    
    private Machine machine;
    private Scanner scanner = new Scanner(System.in);
    private Money payment;

    public PaymentState(Machine machine) {
        this.machine = machine;
        this.payment = machine.getPaidMoney();
        
        this.showMenu();
    }

    private void showMenu() {
        this.showProducts();
        this.showPaidStatus();
        this.showPaymentOptions();
    }

    private void showProducts() {
        System.out.println("PRODUCTOS DISPONIBLES:");
        System.out.println("------------------");
        String stringFormat = "  %d: %-12s $%d%n";
        int i = 1;
        for(Product product : this.machine.getInventory()) {
            if(product.getAmountLeft() > 0) {
                System.out.printf(stringFormat, i++, product.getName(), product.getPrice());
            } else {
                System.out.printf("  X: %-12s [AGOTADO]%n", product.getName());
                i++;
            }
        }
        System.out.println();
    }

    private void showPaidStatus() {
        System.out.print("Pagado: ");
        if(this.payment.getAmount() > 0) {
            System.out.print(ConsoleColor.GREEN_BRIGHT);
        } else {
            System.out.print(ConsoleColor.RED_BRIGHT);
        }
        System.out.print(Integer.toString(this.payment.getAmount()) + " Pesos");
        System.out.print(ConsoleColor.RESET);
        System.out.println("\n");
    }

    private void showPaymentOptions() {
        System.out.println("OPCIONES:");
        System.out.println("-------");
        System.out.println("  1: Insertar dinero");
        System.out.println("  2: Elegir producto");
        if(this.payment.getAmount() > 0) {
            System.out.println("  3: Cancelar y devolver");
        }
        System.out.println();
        System.out.print("> ");
        
        int choice = scanner.nextInt();
        
        if(choice == 1) {
            this.insertarDinero();
        } else if(choice == 2) {
            this.proceedToOrder();
        } else if(choice == 3 && this.payment.getAmount() > 0) {
            this.cancel();
        } else {
            System.out.println("Opción inválida.");
            System.out.println();
            this.showMenu();
        }
    }

    private void insertarDinero() {
        System.out.println();
        System.out.println("Seleccione monto:");
        System.out.println("-------------");
        System.out.println("  1: $1.000");
        System.out.println("  2: $2.000");
        System.out.println("  3: $5.000");
        System.out.println("  4: $10.000");
        System.out.println("  5: $20.000");
        System.out.println("  6: $50.000");
        System.out.println("  0: Volver");
        System.out.println();
        System.out.print("> ");
        
        int choice = scanner.nextInt();
        
        if(choice == 0) {
            System.out.println();
            this.showMenu();
            return;
        }
        
        int amount = 0;
        int bills = 0;
        
        switch(choice) {
            case 1: amount = 1000; bills = 1; break;
            case 2: amount = 2000; bills = 2; break;
            case 3: amount = 5000; bills = 5; break;
            case 4: amount = 10000; bills = 10; break;
            case 5: amount = 20000; bills = 20; break;
            case 6: amount = 50000; bills = 50; break;
            default:
                System.out.println("Opción inválida.");
                this.insertarDinero();
                return;
        }
        
        this.payment.add("1000 Pesos", bills);
        
        System.out.println();
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println("+ " + amount + " Pesos agregados");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        
        this.showMenu();
    }

    private void proceedToOrder() {
        if(this.payment.getAmount() == 0) {
            System.out.println();
            System.out.print(ConsoleColor.RED_BRIGHT);
            System.out.println("Debe insertar dinero primero.");
            System.out.print(ConsoleColor.RESET);
            System.out.println();
            this.showMenu();
            return;
        }
        
        machine.setPaidMoney(payment);
        machine.setState(new OrderState(machine));
    }
    
    private void cancel() {
        this.dispenseMoney();
    }

    private void reset() {
        this.payment.reset();
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
        if(this.payment.getAmount() > 0) {
            System.out.println();
            System.out.println("Dinero devuelto:");
            System.out.println("--------------");
            System.out.print(ConsoleColor.GREEN_BRIGHT);
            System.out.println(Integer.toString(this.payment.getAmount()) + " Pesos");
            System.out.print(ConsoleColor.RESET);
            
            String stringFormat = "  %s: %d%n";
            for(String type : Money.getTypes()) {
                int count = this.payment.getCount(type);
                if(count > 0) {
                    System.out.printf(stringFormat, type, count);
                }
            }
            System.out.println();
        }
        
        this.reset();
    }
}