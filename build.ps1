# Maquina Expendedora de Cafe - Build Script
Write-Host "==================================" -ForegroundColor Cyan
Write-Host "   Compilando proyecto..." -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""

javac -d bin `
    src/domain/valueobject/ProductType.java `
    src/domain/entity/Money.java `
    src/domain/entity/MoneyReturn.java `
    src/domain/entity/Product.java `
    src/domain/repository/ProductRepository.java `
    src/domain/repository/MoneyRepository.java `
    src/domain/service/ChangeCalculator.java `
    src/infrastructure/persistence/FileProductRepository.java `
    src/infrastructure/persistence/FileMoneyRepository.java `
    src/presentation/ui/ConsoleUI.java `
    src/presentation/ui/ConsoleColor.java `
    src/presentation/controller/MachineController.java `
    src/presentation/state/State.java `
    src/presentation/state/PaymentState.java `
    src/presentation/state/OrderState.java `
    src/presentation/state/DispenseState.java `
    src/App.java

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "Compilacion exitosa!" -ForegroundColor Green
    Write-Host "Ejecutando..." -ForegroundColor Yellow
    Write-Host ""
    java -cp bin App
} else {
    Write-Host ""
    Write-Host "Error en compilacion." -ForegroundColor Red
}