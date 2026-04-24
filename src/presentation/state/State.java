package presentation.state;

import presentation.controller.MachineController;

public abstract class State {
    protected MachineController controller;
    
    public State(MachineController controller) {
        this.controller = controller;
    }
    
    public abstract void insertMoney();
    public abstract void interactWithMenu();
    public abstract void dispenseProduct();
    public abstract void dispenseMoney();
}