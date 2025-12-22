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

CI y despliegue
- Se añadió un workflow de GitHub Actions en `.github/workflows/ci.yml` que ejecuta pruebas en el backend (mvn test) en cada push/PR.
- Cuando subas el repo a GitHub, verás el estado del build en la pestaña de Actions.

