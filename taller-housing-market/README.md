# Housing-App
Aplicación CRUD para gestionar casas utilizando Angular 17 y Spring Boot
### Resumen
Housing-App es una aplicación CRUD (Crear, Leer, Actualizar, Eliminar) diseñada para gestionar casas. La aplicación se compone de dos componentes:
* Un componente UI construido con Angular 17, responsable de manejar las interacciones del usuario y mostrar datos de casas.
* Un componente API construido con Spring Boot, responsable de procesar solicitudes y devolver respuestas.

## Características
* Crear: permite a los usuarios crear nuevas casas con título, descripción y fecha límite.
* Leer: muestra una lista de todas las casas, incluyendo detalles de casa.
* Eliminar: permite a los usuarios eliminar casas completadas.

## Arquitectura
La aplicación se compone de los siguientes componentes:

### UI (Angular 17)
El componente Angular 17 maneja las interacciones del usuario y comunica con la API para recuperar o modificar datos de casa. La UI incluye:
* Una lista de casas, mostrando título, descripción, fecha límite y estado.
* Formularios para crear, actualizar y eliminar casas.

### API (Spring Boot)
El componente API de Spring Boot procesa solicitudes desde la UI y devuelve respuestas en formato JSON. Incluye:
* Puntos de conexión para crear, leer, actualizar y eliminar casas.
* Lógica de validación y manipulación de datos.

## Pruebas
Para probar la API, puede utilizar Insomnia o Postman importando el directorio `housing-api` (ubicado en la carpeta `test`) en su herramienta preferida de pruebas.

### Inicio rápido
1. Realice un fork de  https://gitlab.com/dci-project/ingsoft-dci/ejemplos/angular/housing-market.git
2. Ejecutar `npm install && ng serve` y `mvn clean package` para instalar dependencias y construir la aplicación.
3. Iniciar el API de Spring Boot ejecutando `java -jar housing-api.jar`.
4. Abrir su navegador y navegar a `http://localhost:4200` para acceder a la UI de Angular.

