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

La aplicación se conecta automáticamente a la base de datos gracias a las variables de entorno configuradas en `docker-compose.yml`.

---

## Instrucciones de uso

1. **Compilar la aplicación**

Primero, asegurate de tener el JAR generado. Desde la raíz del proyecto, ejecutá:

```bash
./mvnw clean package
