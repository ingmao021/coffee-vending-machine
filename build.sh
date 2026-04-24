#!/bin/bash

# Compilar todos los archivos Java
javac -d bin \
    src/domain/valueobject/ProductType.java \
    src/domain/entity/Money.java \
    src/domain/entity/MoneyReturn.java \
    src/domain/entity/Product.java \
    src/domain/repository/ProductRepository.java \
    src/domain/repository/MoneyRepository.java \
    src/domain/service/ChangeCalculator.java \
    src/infrastructure/persistence/FileProductRepository.java \
    src/infrastructure/persistence/FileMoneyRepository.java \
    src/presentation/ui/ConsoleUI.java \
    src/presentation/ui/ConsoleColor.java \
    src/presentation/controller/MachineController.java \
    src/presentation/state/State.java \
    src/presentation/state/PaymentState.java \
    src/presentation/state/OrderState.java \
    src/presentation/state/DispenseState.java \
    src/App.java

# Ejecutar si compilacion exitosa
if [ $? -eq 0 ]; then
    echo ""
    echo "Compilacion exitosa. Ejecutando..."
    echo ""
    java -cp bin App
fi
