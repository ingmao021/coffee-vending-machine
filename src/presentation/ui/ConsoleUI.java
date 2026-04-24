package presentation.ui;

import domain.entity.Money;

public class ConsoleUI {
    
    public static void printHeader(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.println();
    }
    
    public static void printProduct(int index, String name, int price) {
        System.out.printf("  %d: %-12s $%d%n", index, name, price);
    }
    
    public static void printProductSoldOut(String name) {
        System.out.printf("  X: %-12s [AGOTADO]%n", name);
    }
    
    public static void printOption(int index, String text) {
        System.out.printf("  %d: %s%n", index, text);
    }
    
    public static void printBackOption() {
        System.out.println("  0: Volver al menu");
        System.out.println();
    }
    
    public static void printPaidStatus(int amount) {
        System.out.print("Pagado: ");
        if (amount > 0) {
            System.out.print(ConsoleColor.GREEN_BRIGHT);
        } else {
            System.out.print(ConsoleColor.RED_BRIGHT);
        }
        System.out.print(amount + " Pesos");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printEmptyLine() {
        System.out.println();
    }
    
    public static void printPrompt() {
        System.out.print("> ");
    }
    
    public static void printError(String message) {
        System.out.print(ConsoleColor.RED_BRIGHT);
        System.out.println(message);
        System.out.print(ConsoleColor.RESET);
    }
    
    public static void printSuccess(String message) {
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println(message);
        System.out.print(ConsoleColor.RESET);
    }
    
    public static void printMoneyAdded(int amount) {
        System.out.println();
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println("+ " + amount + " Pesos agregados");
        System.out.print(ConsoleColor.RESET);
    }
    
    public static void printMoneyAmount(int amount) {
        System.out.println(amount + " Pesos");
    }
    
    public static void printMoneyBreakdown(Money money) {
        String format = "  %s: %d%n";
        for (String type : Money.getTypes()) {
            int count = money.getCount(type);
            if (count > 0) {
                System.out.printf(format, type, count);
            }
        }
    }
    
    public static void printOrderSummary(String name, int price, int paid) {
        System.out.println();
        System.out.println("Producto: " + name);
        System.out.println("Precio: $" + price);
        System.out.println("Pagado:  $" + paid);
        System.out.println();
    }
    
    public static void printMissingMoney(int amount) {
        System.out.print(ConsoleColor.RED_BRIGHT);
        System.out.println("Faltan $" + amount + " Pesos");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printNoChangeError(int amount) {
        System.out.print(ConsoleColor.RED_BRIGHT);
        System.out.println("La maquina no tiene suficiente cambio para $" + amount);
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printOrderAccepted() {
        System.out.println();
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println("PEDIDO ACEPTADO");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printChange(int amount) {
        System.out.println("Cambio: $" + amount);
    }
    
    public static void printOrderCancelled() {
        System.out.println();
        System.out.print(ConsoleColor.RED_BRIGHT);
        System.out.println("PEDIDO CANCELADO");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printPreparing(String productName) {
        System.out.println("Preparando " + productName + "...");
        System.out.print(ConsoleColor.GREEN_BRIGHT);
    }
    
    public static void printProgressBar(int seconds) {
        for (int i = 0; i <= seconds; i++) {
            int porcentaje = (i * 100) / seconds;
            System.out.print("\r[" + "=".repeat(i * 20 / seconds) + ".".repeat(20 - i * 20 / seconds) + "] " + porcentaje + "%");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
        System.out.println();
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printDispenserAvailable() {
        System.out.print(ConsoleColor.GREEN_BRIGHT);
        System.out.println("[DISPONIBLE]");
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }
    
    public static void printTakeProduct(String productName) {
        System.out.print(ConsoleColor.CYAN_BOLD);
        System.out.println("Retire su " + productName);
        System.out.print(ConsoleColor.RESET);
    }
    
    public static void printThankYou() {
        System.out.println("Gracias por su compra!");
        System.out.println("=====================");
    }
}