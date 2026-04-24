# ☕ Maquina Expendedora de Cafe


---

Un programa simulador de maquina expendedora de cafe, implementado para mostrar en accion el **Patron de Diseño State**. La maquina acepta billetes en Pesos Colombianos, permite seleccionar bebidas, devuelve cambio y gestiona el inventario de productos.

---

## 📋 Problema

Debes implementar una maquina expendedora de cafe. La maquina de cafe funciona de la siguiente manera:

| Requisito | Descripcion |
|----------|-------------|
| Billetes aceptados | 50, 100, 200, 500 y 1000 Pesos |
| Bebidas disponibles | **Coffee**, **Cappuccino**, **Expresso** |
| Precios | Coffee: $1.200 / Cappuccino: $1.500 / Expresso: $1.800 |
| Funcionamiento | Insertar dinero, seleccionar bebida, devuelve cambio, prepara bebida |

---

## 🔄 Flujo de la Maquina

```
[Payment] --(Insertar dinero)--> [Order] --(Seleccionar producto)--> [Dispense] --(Dispensar producto)
```

---


## 🎯 Estados Implementados

| Estado | Descripcion |
|--------|-------------|
| **PaymentState** | La maquina espera a que el cliente inserte dinero |
| **OrderState** | El cliente selecciona una bebida del menu |
| **DispenseState** | La maquina prepara y dispensa la bebida seleccionada |

---

## ▶️ Como Ejecutar

### Requisitos

- **Java Development Kit (JDK)** 17 o superior

Elige en project structure el SDK de Java 17 para compilar y ejecutar el proyecto.

### Compilacion y Ejecucion rapida

```bash
# Ejecutar el script de compilacion y ejecucion
./build.ps1
```

Se recomienda ampliar la terminal para ver el flujo completo de la maquina expendedora.


---

*Diseñado con el Patron de Diseño State*
**Autor:** Anderson Mauricio Ordoñez Zuñiga
