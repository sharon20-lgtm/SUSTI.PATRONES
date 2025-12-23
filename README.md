# SUSTI.PATRONES

![CI](https://github.com/sharon20-lgtm/SUSTI.PATRONES/actions/workflows/ci.yml/badge.svg)

Proyecto de ejemplo que implementa patrones de diseño (Adapter, Observer, Strategy, Command/Memento, Iterator, Proxy) 
Estructura:
- backend/ (Spring Boot)
- frontend/ (React, placeholder)

Siguientes pasos:
- Implementar JWT real y pruebas.
- Completar frontend con rutas de login y panel admin.
- Añadir tests unitarios e integración.

Plataforma integral para la gestión de ventas, inventario y reportes financieros en pymes peruanas. El proyecto aplica patrones de diseño (Adapter, Observer, Command/Memento, Strategy, Iterator, Proxy) y principios GRASP para lograr un código mantenible, escalable y seguro

Objetivos
Facilitar la administración de productos, pedidos y pagos.
Automatizar alertas de inventario bajo.
Permitir integración flexible de pasarelas de pago.
Proveer reportes financieros seguros.
Servir como ejemplo educativo de buenas prácticas y patrones de diseño.

Tecnologías principales
Backend: Java 17, Spring Boot, Maven, Swagger
Frontend: React + Vite 
Testing: JUnit 5,  Spring Boot Test

Patrones implementados
Adapter (pagos)
Observer (inventario)
Command + Memento (pedidos)
Strategy (precios)
Iterator (catálogo)
Proxy (reportes)
Estructura del proyecto
backend/ (API REST, lógica de negocio, patrones y tests)
frontend/ (UI React, extensible)
docs/ (diagramas UML, documentación técnica, colección Postman)

Ejemplo de endpoints principales
Autenticación: /auth/register, /auth/login
Productos: /products, /products/{id}
Pagos: /payments/order/{orderId}, /payments/config/{gateway}/enable|disable
Pedidos: /orders, /orders/{orderId}/descuento|restaurar|cancelar
Alertas: /alerts, /alerts/product/{productId}


Instalación y ejecución
Backend (Spring Boot)
Instala Java 17 y Maven.
Ve a la carpeta backend y ejecuta:
Accede a la API y Swagger UI en:
http://localhost:8080/swagger-ui/index.html
Frontend (React)
Instala Node.js y npm.
Ve a la carpeta frontend y ejecuta:
Accede a la app en:
http://localhost:5173


Repositorio principal: https://github.com/sharon20-lgtm/SUSTI.PATRONES


