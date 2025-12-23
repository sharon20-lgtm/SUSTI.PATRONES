# Backend - SUSTI.PATRONES

Proyecto backend basado en Spring Boot (esqueleto).

Cómo correr:
1. Requiere Java 17 y Maven.
2. mvn clean install
3. mvn spring-boot:run
4. Acceder a H2 Console: http://localhost:8080/h2-console (jdbc:h2:mem:devdb)

Endpoints temprales:
- POST /auth/register
- POST /auth/login
- POST /payments/order/{orderId}
- POST /payments/config/{gateway}/enable
- POST /payments/config/{gateway}/disable


- Implementación inicial de patrones Adapter (pagos) y Observer (inventario).
- Autenticación aún con token dummy; se implementará JWT en la próxima iteración.

