# Diseño técnico — SUSTI.PATRONES

## Resumen ejecutivo ✅
El proyecto implementa una plataforma de gestión de ventas e inventario para pymes. Se aplican patrones estructurales y de comportamiento (Adapter, Proxy, Observer, Command, Memento, Strategy, Iterator) y los principios GRASP (Controller, Creator, Information Expert, Low Coupling, High Cohesion, Polymorphism, Indirection, Protected Variations, Pure Fabrication) para asignar responsabilidades con claridad, escalabilidad y mantenibilidad.

---

## Mapeo RF → Patrón → Justificación (GRASP)

- RF1 (Pagos) → Adapter
  - Justificación: *Protected Variations* para aislar diferencias entre pasarelas; *Polymorphism* para intercambiar adaptadores sin cambiar la lógica del dominio. `PaymentService` actúa como **Controller** / **Information Expert** para procesar pagos.

- RF2 (Habilitar/Deshabilitar pasarelas) → PaymentConfig + Persistence
  - Justificación: **Creator** para `PaymentConfigService` que conoce la persistencia; persistencia reduce errores y facilita toggles desde UI.

- RF3–RF4 (Reportes) → Proxy
  - Justificación: **Indirection** para separar validación de credenciales/roles del cálculo del reporte; Proxy hace la verificación y delega a `FinancialReportService`.

- RF5–RF6 (Inventario) → Observer
  - Justificación: **Information Expert**: `Product` y `ProductSubject` conocen el stock; **Low Coupling**: notificación desacoplada de acciones de UI o de negocio; `StockObserverService` persiste alertas (Pure Fabrication).

- RF7–RF8 (Pedidos) → Command + Memento
  - Justificación: **Pure Fabrication** `CommandInvoker` guarda historial de comandos; **Creator** `OrderService` crea y gestiona órdenes; `Caretaker` y `Originator` implementan memento para undo.

- RF9–RF10 (Políticas de precios) → Strategy
  - Justificación: **Polymorphism** para cambiar la estrategia de precios en tiempo de ejecución; `PricingContext` aplica la *Strategy*.

- RF11–RF12 (Catálogo) → Iterator
  - Justificación: **Low Coupling** y **High Cohesion**; `ProductIterator` oculta estructura interna y mejora paginación/filtrado.

---

## Diagrama de clases (Mermaid)

```mermaid
classDiagram
    class PaymentService{ +setGateway(g) +processPayment(amount) }
    class PaymentGateway{ <<interface>> +pay(amount) }
    class PayPalAdapter{ +pay(amount) }
    class YapeAdapter{ +pay(amount) }
    class PlinAdapter{ +pay(amount) }

    PaymentService --> PaymentGateway
    PaymentGateway <|-- PayPalAdapter
    PaymentGateway <|-- YapeAdapter
    PaymentGateway <|-- PlinAdapter

    class Product{
      -id
      -producto
      -stock
      -stockMinimo
      +getStock()
    }
    class ProductSubject{ +attach(o) +detach(o) +notifyObservers(p) }
    class InventoryObserver{ <<interface>> +update(p) }
    class ManagerObserver{ +update(p) }
    class PurchaseObserver{ +update(p) }

    ProductSubject ..> InventoryObserver
    InventoryObserver <|-- ManagerObserver
    InventoryObserver <|-- PurchaseObserver

    class Order{ -id -status -totalAmount }
    class OrderMemento{ -status -totalAmount }
    class OrderOriginator{ +saveState() +restoreState(m) }
    class Caretaker{ +addMemento(m) +undo() }
    class Command{ <<interface>> +execute() }
    class ApplyDiscountCommand{ +execute() }

    OrderOriginator ..> OrderMemento
    Caretaker --> OrderMemento
    ApplyDiscountCommand ..|> Command
    ApplyDiscountCommand --> OrderService

    OrderService --> Caretaker
    OrderService --> OrderOriginator

```

---

## Secuencia (Pago con pasarela habilitada)

```mermaid
sequenceDiagram
    participant Client
    participant PaymentController
    participant PaymentConfigService
    participant PayService as PaymentService
    participant Adapter

    Client->>PaymentController: POST /payments/order/{orderId} {monto, metodo}
    PaymentController->>PaymentConfigService: getConfig()
    alt gateway enabled
      PaymentController->>PayService: setGateway(adapter)
      PayService->>Adapter: pay(monto)
      Adapter-->>PayService: true
      PayService-->>PaymentController: success
      PaymentController-->>Client: 200 OK
    else disabled
      PaymentController-->>Client: 400 Gateway disabled
    end
```

---

## Responsabilidades y GRASP por componente (tabla corta)

- `PaymentService` — Controller/Information Expert; delega a adaptadores (Low Coupling).
- `PaymentConfigService` — Creator/Information Expert para persistencia de toggles.
- `ProductSubject` — Pure Fabrication/Indirection que notifica observers.
- `StockAlertService` — Pure Fabrication para registrar alertas (separa persistencia de la lógica de notificación).
- `OrderService` — Creator/Controller; implementa Memento (Originator/Caretaker) para restauración de estado.

---

## Pruebas y validación
- Integración: Payment integration testing (disable+enable), StockAlert integration test (crear producto con stock < stockMinimo genera alerta).
- Unit: AuthService, JwtUtil, PaymentService (mocks), ProductService (observer checks), OrderService (Memento flow).

---

## Próximos pasos sugeridos
1. Pulir autenticación (hash de passwords, roles, tests de seguridad).  
2. Implementar la UI (login, panel admin para toggles de pasarelas, alertas).  
3. Añadir CI y badge de estado (ya configurado).  
4. Preparar presentación técnica (slides) y un PDF con diagramas.

---

*Documento generado automáticamente — se puede ampliar con diagramas exportados y documentación detallada por RFC.*
