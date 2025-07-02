# Energym - Backend API (Spring Boot + Docker + PostgreSQL)

Este proyecto es el backend de Energym, desarrollado con Spring Boot y PostgreSQL. Expone una API REST que se puede consultar y probar mediante Swagger.

> ⚠️ Este proyecto no incluye interfaz gráfica (frontend). Solo expone endpoints de backend. Si se desea, un frontend puede ser creado por separado.

---

## Requisitos

Para ejecutar esta aplicación necesitás tener instalado:

- **Docker** → [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
- **Docker Compose** → [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

No es necesario tener Java ni PostgreSQL instalados localmente, ya que todo corre dentro de contenedores Docker.

---

## Configuración

Los servicios se definen en el archivo `docker-compose.yml`:

- **db**: Base de datos PostgreSQL
  - Base de datos: `midb`
  - Usuario: `postgres`
  - Contraseña: `postgres`

- **app**: Aplicación Spring Boot que expone la API en el puerto `9090` de tu máquina.
- Los datos de la base se persisten en un volumen Docker llamado db_data.

La aplicación se conecta automáticamente a la base de datos gracias a las variables de entorno configuradas en `docker-compose.yml`.

---

## Instrucciones de uso

#  ACCEDER A LA API

Podés explorar y probar los endpoints de la API desde el siguiente enlace:

👉 [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

Desde ahí podés explorar y probar todos los endpoints de la API mediante la interfaz de Swagger.

