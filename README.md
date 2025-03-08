# Drassanes

## Descripción
Drassanes es una herramienta para simplificar la gestión y configuración de Helm Charts y Subcharts en entornos Kubernetes, especialmente en arquitecturas multi-tenant.

## Características clave
- **Generación automatizada de Helm Charts** utilizando `helm create`.
- **Gestión de subcharts** desde una interfaz centralizada.
- **Definición de valores globales** aplicables a todas las aplicaciones.
- **Exportación de configuraciones** para integrarlas en flujos GitOps.
- **Edición visual de `values.yaml`**.

## Instalación y requisitos
### Requisitos previos
- **Helm** instalado en el sistema.
- **Java 21+** para ejecutar la aplicación Spring Boot.
- **Gradle** para la gestión del proyecto.

### Instalación
1. Clonar el repositorio:
   ```sh
   git clone https://github.com/fikua/fikua-drassanes-poc.git
   cd drassanes
   ```
2. Construir y ejecutar el proyecto:
   ```sh
   ./gradlew bootRun
   ```

## Uso básico
1. Acceder a la interfaz web en `http://localhost:8080`.
2. Crear un nuevo Helm Chart desde la UI.
3. Definir los subcharts y configuraciones.
4. Exportar los archivos generados para su uso en GitOps.

## Licencia
Este proyecto está licenciado bajo la **Apache 2.0 License** - ver el archivo [LICENSE](LICENSE) para más detalles.
