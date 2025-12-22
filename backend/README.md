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

Notas:
- Implementación inicial de patrones Adapter (pagos) y Observer (inventario).
- Autenticación aún con token dummy; se implementará JWT en la próxima iteración.

CI / Ejecución de tests
- He añadido un flujo de GitHub Actions en `.github/workflows/ci.yml` que ejecuta `mvn test` en el backend en cada push/PR (usa JDK 17).
- Si prefieres ejecutar pruebas localmente, instala Maven y ejecuta desde `backend/`:
  1. `mvn clean test`
  2. Si no tienes Maven, puedes usar una imagen docker o configurar `mvn` en tu sistema.
