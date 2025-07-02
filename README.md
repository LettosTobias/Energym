# Energym - Backend API (Spring Boot + Docker + PostgreSQL)

Este proyecto es el backend de Energym, desarrollado con Spring Boot y PostgreSQL. Expone una API REST que se puede consultar y probar mediante Swagger.

---

## Requisitos

Para ejecutar esta aplicación necesitás tener instalado:

- **Docker** → [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
- **Docker Compose** → [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

No es necesario tener Java ni PostgreSQL instalados localmente, ya que todo corre dentro de contenedores Docker.

---

## Configuración

  ###Desde la raiz del proyecto:
    -./mvnw clean package -DskipTests
    - docker-compose up --build

- La aplicación ya viene preconfigurada para conectarse a una base de datos **PostgreSQL en la nube mediante Neon**, por lo que al levantarla ya tendrás datos disponibles para interactuar.
- **app**: Aplicación Spring Boot que expone la API en el puerto `9090` de tu máquina.
- Los datos que modifiques a través de la API se almacenan directamente en la base Neon, por lo que estarán disponibles desde cualquier dispositivo que consuma la API.
  
La aplicación se conecta automáticamente a la base de datos gracias a las variables de entorno configuradas en `docker-compose.yml`.

---

## Instrucciones de uso

#  ACCEDER A LA API

Podés explorar y probar los endpoints de la API desde el siguiente enlace:

👉 [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

Desde ahí podés explorar y probar todos los endpoints de la API mediante la interfaz de Swagger.
