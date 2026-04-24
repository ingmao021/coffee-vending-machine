import presentation.controller.MachineController;

public class App {
    public static void main(String[] args) throws Exception {
        MachineController controller = MachineController.getInstance();
        controller.start();
    }
}