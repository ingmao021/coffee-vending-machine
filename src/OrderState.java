import java.util.ArrayList;
import java.util.Scanner;

public class OrderState extends State {
    
    private Machine machine;
    private Scanner scanner = new Scanner(System.in);
    
    public OrderState(Machine machine) {
        this.machine = machine;

        System.out.println();
        System.out.println("=== SELECCIONAR PRODUCTO ===");
        System.out.println("=======================");
        System.out.println();
        
        this.interactWithMenu();
    }
    
    @Override
    public void insertMoney() {
        this.machine.setState(new PaymentState(machine));
    }

    @Override
    public void interactWithMenu() {
        Money paidMoney = this.machine.getPaidMoney();
        
        System.out.println("Productos disponibles:");
        System.out.println("----------------");
        
        int numberOfProducts = this.machine.getNumberOfProducts();
        String stringFormat = "  %d: %-12s $%d%n";
        int productSelector = 1;
        
        for(Product product : this.machine.getInventory()) {
            if(product.getAmountLeft() > 0) {
                System.out.printf(stringFormat, productSelector++, 
                    product.getName(), product.getPrice());
            } else {
                System.out.printf("  X: %-12s [AGOTADO]%n", product.getName());
                productSelector++;
            }
        }
        
        System.out.println("  0: Volver al menú");
        System.out.println();
        
        System.out.print("Pagado: ");
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.print(paidMoney.getAmount() + " Pesos");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        System.out.println();
        
        System.out.print("> ");
        productSelector = scanner.nextInt();

        if(productSelector == 0) {
            this.insertMoney();
        }
        else if(productSelector >= 1 && productSelector <= numberOfProducts) {
            this.placeOrder(productSelector - 1);
        }
    }

    @Override
    public void dispenseProduct() {
    }

    @Override
    public void dispenseMoney() {
        Money paidMoney = this.machine.getPaidMoney();

        if(paidMoney.getAmount() > 0) {
            System.out.println();
            System.out.println("Dinero devuelto:");
            System.out.println("--------------");
            System.out.print(ConsoleColor.GREEN_BRIGHT);
            System.out.println(paidMoney.getAmount() + " Pesos");
            System.out.print(ConsoleColor.RESET);
            
            String stringFormat = "  %s: %d%n";
            for(String type : Money.getTypes()) {
                int count = paidMoney.getCount(type);
                if(count > 0) {
                    System.out.printf(stringFormat, type, count);
                }
            }
        }
    }

    private void placeOrder(int productIndex) {
        Product product = this.machine.getInventory().get(productIndex);
        Money paidMoney = this.machine.getPaidMoney();
        
        if(product.getAmountLeft() <= 0) {
            System.out.println();
            System.out.print(ConsoleColor.RED_BRIGHT);
            System.out.println("Producto agotado.");
            System.out.print(ConsoleColor.RESET);
            System.out.println();
            this.interactWithMenu();
            return;
        }
        
        int paid = paidMoney.getAmount();
        int price = product.getPrice();
        
        System.out.println();
        System.out.println("Producto: " + product.getName());
        System.out.println("Precio: $" + price);
        System.out.println("Pagado:  $" + paid);
        System.out.println();
        
        if(paid < price) {
            int falta = price - paid;
            System.out.print(ConsoleColor.RED_BRIGHT);
            System.out.println("Faltan $" + falta + " Pesos");
            System.out.print(ConsoleColor.RESET);
            System.out.println();
            
            System.out.println("OPCIONES:");
            System.out.println("-------");
            System.out.println("  1: Insertar más dinero");
            System.out.println("  2: Cancelar y devolver dinero");
            System.out.println();
            System.out.print("> ");
            
            int choice = scanner.nextInt();
            
            if(choice == 1) {
                this.machine.setState(new PaymentState(machine));
            } else {
                this.cancelOrder("InsufficientMoney");
            }
            return;
        }
        
        int changeNeeded = paid - price;
        
        MoneyReturn returnMoney = Money.calculateReturn(paidMoney, price, this.machine.getMachineMoney());
        
        if(returnMoney.getStatus()) {
            this.acceptOrder(product, returnMoney);
        } else {
            System.out.print(ConsoleColor.RED_BRIGHT);
            System.out.println("La máquina no tiene suficiente cambio para $" + changeNeeded);
            System.out.print(ConsoleColor.RESET);
            System.out.println();
            
            System.out.println("OPCIONES:");
            System.out.println("-------");
            System.out.println("  1: Insertar más dinero");
            System.out.println("  2: Cancelar y devolver dinero");
            System.out.println();
            System.out.print("> ");
            
            int choice = scanner.nextInt();
            
            if(choice == 1) {
                this.machine.setState(new PaymentState(machine));
            } else {
                this.cancelOrder("NoChange");
            }
        }
    }

    private void acceptOrder(Product product, MoneyReturn returnMoney) {
        int cambio = returnMoney.getMoney().getAmount();
        
        System.out.println();
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println("PEDIDO ACEPTADO");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        
        if(cambio > 0) {
            System.out.println("Cambio: $" + cambio);
        }
        
        this.machine.updateMachineMoney("payment");
        this.machine.setReturnMoney(returnMoney);
        this.machine.setChosenProduct(product);
        
        this.machine.getInventory().get(this.machine.getInventory().indexOf(product)).decrementAmountLeft();
        
        this.machine.setState(new DispenseState(machine));
    }
    
    private void cancelOrder(String reason) {
        System.out.println();
        System.out.print(ConsoleColor.RED_BRIGHT);
        System.out.println("PEDIDO CANCELADO");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
        
        this.machine.clearPaidMoney();
        this.machine.setState(new PaymentState(machine));
    }
}