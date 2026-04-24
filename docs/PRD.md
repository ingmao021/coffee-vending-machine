# Product Requirements Document (PRD)

## Maquina Expendedora de Cafe

---

## 1. Descripcion del Proyecto

### Nombre del Proyecto
Maquina Expendedora de Cafe

### Tipo de Aplicacion
Simulador de maquina expendedora de cafe con Patron de Diseño State

### Descripcion General
Programa simulador de una maquina expendedora de cafe que acepta billetes en Pesos Colombianos, permite seleccionar beverages (Coffee, Cappuccino, Expresso), devuelve cambio y gestiona el inventario de productos.

### Usuario Final
Usuarios que interactuan con una maquina expendedora virtual para comprar bebidas calientes.

---

## 2. Requisitos Funcionales

### 2.1 Gestion de Pagos

| Requisito | Descripcion |
|----------|-------------|
| RF-01 | La maquina debe aceptar billetes de $50, $100, $200, $500 y $1.000 pesos. |
| RF-02 | La maquina debe aceptar botones de pago rapido: $1.000, $2.000, $5.000, $10.000, $20.000 y $50.000. |
| RF-03 | El usuario puede cancelar y recuperar el dinero insertado. |
| RF-04 | Se debe mostrar el saldo actual.insertado. |

### 2.2 Gestion de Productos

| Requisito | Descripcion |
|----------|-------------|
| RF-05 | La maquina debe ofrecer tres tipos de bebidas: Coffee ($1.200), Cappuccino ($1.500), Expresso ($1.800). |
| RF-06 | Cada bebida tiene un tiempo de preparacion diferente. |
| RF-07 | Coffee: 10 segundos, Cappuccino: 20 segundos, Expresso: 20 segundos. |
| RF-08 | Solo se pueden seleccionar bebidas con inventario disponible. |

### 2.3 Proceso de Pedido

| Requisito | Descripcion |
|----------|-------------|
| RF-09 | El usuario selecciona una bebida del menu. |
| RF-10 | Si no hay suficiente dinero, mostrar mensaje de error. |
| RF-11 | Si hay suficiente dinero, calcular y devolver cambio. |
| RF-12 | La bebida debe ser preparada automaticamente. |

### 2.4 Dispensacion

| Requisito | Descripcion |
|----------|-------------|
| RF-13 | Quando la bebida esta lista, se dispensa automaticamente. |
| RF-14 | Mostrar mensaje de preparacion completada. |
| RF-15 | Reiniciar el estado de la maquina para el siguiente cliente. |

---

## 3. Requisitos No Funcionales

### 3.1 Rendimiento

| Requisito | Descripcion |
|----------|-------------|
| RNF-01 | La respuesta de la interfaz debe ser inmediata (< 100ms). |
| RNF-02 | El tiempo de preparacion de bebidas debe ser simulable. |

### 3.2 Escalabilidad

| Requisito | Descripcion |
|----------|-------------|
| RNF-03 | La arquitectura debe permitir agregar nuevos productos facilmente. |
| RNF-04 | El sistema debe gestionar múltiples transacciones secuenciales. |

### 3.3 Mantenibilidad

| Requisito | Descripcion |
|----------|-------------|
| RNF-05 | Codigo implementado con patron State para gestionar estados. |
| RNF-06 | Separation clara de responsabilidades entre clases. |

### 3.4 Persistencia

| Requisito | Descripcion |
|----------|-------------|
| RNF-07 | El inventario se lee desde el archivo `data/inventory.txt`. |
| RNF-08 | El dinero disponible se lee desde el archivo `data/money.txt`. |
| RNF-09 | Los cambios en inventario y dinero no persisten entre ejecuciones. |

---

## 4. Arquitectura del Sistema

### 4.1 Diagrama de Estados

```
 PaymentState (Pago)  -->  OrderState (Pedido)  -->  DispenseState (Dispensar)
       ^                                           |
       |___________________________________________|
              (Cancelar / Completar)
```

### 4.2 Clases Principales

| Clase | Responsabilidad |
|-------|-----------------|
| Machine | Clase singleton que gestiona la maquina expendedora |
| State | Clase abstracta base del patron State |
| PaymentState | Estado: esperando pago del cliente |
| OrderState | Estado: seleccionando bebida |
| DispenseState | Estado: dispensando la bebida |
| Money | Gestiona el dinero (billetes y monedas) |
| MoneyReturn | Gestiona la devolucion de cambio |
| Product | Representa un producto (bebida) |
| ProductType | Enum: Coffee, Cappuccino, Expresso |
| App | Punto de entrada de la aplicacion |

---

## 5. Definicion de Datos

### 5.1 Formato Inventario

Archivo: `data/inventory.txt`

```
nombre,precio_en_pesos,tiempo_de_preparacion_en_segundos,cantidad
```

Ejemplo:
```
Coffee,1200,10,5
Cappuccino,1500,20,5
Expresso,1800,20,0
```

### 5.2 Formato Dinero

Archivo: `data/money.txt`

```
denominacion,cantidad
```

Ejemplo:
```
50,10
100,10
200,10
500,10
1000,10
```

---

## 6. Casos de Uso

### UC-01: Comprar una Bebida

1. El usuario inserta dinero (billetes o botones rapidos).
2. La maquina muestra el saldo actual.
3. El usuario selecciona una bebida.
4. La maquina verifica si hay suficiente dinero y disponibilidad.
5. Si es valido, la maquina devuelve cambio y prepara la bebida.
6. La bebida se dispensa automaticamente.

### UC-02: Cancelar Transaccion

1. El usuario inserta dinero.
2. El usuario decide cancelar.
3. La maquina devuelve todo el dinero insertado.
4. La maquina vuelve al estado inicial.

---

## 7. Criterios de Aceptacion

| Criterio | Descripcion |
|----------|-------------|
| CA-01 | La maquina acepta los 5 billete definidos. |
| CA-02 | Los 3 productos se muestran en el menu. |
| CA-03 | El cambio se calcula correctamente. |
| CA-04 | Mensaje de error si no hay suficiente dinero. |
| CA-05 | Mensaje de error si el producto no esta en inventario. |
| CA-06 | La dispensacion ocurre automaticamente. |
| CA-07 | La cancelacion devuelve el dinero insertado. |

---

## 8. Stack Tecnologico

| Tecnologia | Version |
|------------|---------|
| Java | JDK 8+ |
| Compilador | javac |

---

## 9. Historial de Versiones

| Version | Fecha | Descripcion |
|---------|-------|-------------|
| 1.0.0 | 24/04/2026 | Version inicial del proyecto |

---
