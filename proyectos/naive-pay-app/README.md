# Naive-pay-App
Aplicación Naive-pay para gestionar pagos utilizando Angular 18 y Spring Boot
### Resumen
Naive-pay-App es una aplicación diseñada para gestionar pagos. La aplicación se compone de dos componentes:
* Un componente UI construido con Angular 18, responsable de manejar las interacciones del usuario y mostrar datos.
* Un componente API construido con Spring Boot, responsable de procesar solicitudes y devolver respuestas.

## Arquitectura
La aplicación se compone de los siguientes componentes:

### UI (Angular 18)
El componente Angular 18 maneja las interacciones del usuario y comunica con la API para recuperar o modificar datos.

### API (Spring Boot)
El componente API de Spring Boot procesa solicitudes desde la UI y devuelve respuestas en formato JSON.

## Pruebas
Para probar la API, puede utilizar Insomnia o Postman importando el directorio `Naive-pay-api` (ubicado en la carpeta `test`) en su herramienta preferida de pruebas.

# Contenedores Locales

## Probar contenedores de manera individual

Creación contenedor API en la raíz del proyecto:

```
docker build -t Naive-pay-api ./Naive-pay-api
docker run -p 8080:8080 -d Naive-pay-api

docker build -t Naive-pay-ui ./Naive-pay-ui
docker run -p 8080:8080 -d Naive-pay-ui
```

## Probar stack con docker compose
```
docker-compose up -d
```

# Acceder a url despues del despliegue

- [UI](http://200.13.4.224:4200/)
- [API](http://200.13.4.224:8080/)
