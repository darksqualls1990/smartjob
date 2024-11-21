# SmartJob Application

SmartJob es una aplicación desarrollada con **Spring Boot**, diseñada para gestionar usuarios. Implementa validaciones avanzadas, autenticación con JWT, manejo global de excepciones y utiliza Swagger para documentar la API.

## Características principales

- **Validación de usuarios**:
  - Validación de formato de correo electrónico.
  - Validación de claves configurables mediante expresiones regulares.
- **Autenticación**:
  - Generación de tokens JWT para usuarios registrados.
- **Manejo de excepciones globales**:
  - Respuestas consistentes para excepciones específicas y genéricas.
- **Swagger**:
  - Documentación interactiva de la API con OpenAPI 3.0.
- **Arquitectura modular**:
  - Separación clara de controladores, servicios, repositorios y utilidades.
## Tecnologías utilizadas


- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **JWT** (Json Web Token)
- **Lombok**
- **ModelMapper**
- **JUnit 5** y **Mockito**
- **Swagger/OpenAPI**

---

## Configuración del proyecto

### Requisitos previos

- **Java 17+**
- **Maven**

### Variables de configuración

Configura las siguientes propiedades en `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

jwt.secret=Y2hhbmdlbW9yaW5nYXBwYWRyZXNwYXNzd29yZGZyYWdzaGVsb3BhY2tlZ2Vvc3Rh
# Expresión regular para la clave que debe tener al menos una mayúscula, un carácter especial y entre 8 a 20 caracteres
key.regex=^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':"\\|,.<>\\/?]).{8,20}$

springdoc.swagger-ui.path=/api-docs

springdoc.api-docs.title=API de Usuarios
springdoc.api-docs.description=Documentación de la API de manejo de usuarios
```


## Instalación y ejecución

Sigue los pasos a continuación para configurar y ejecutar el proyecto:

### 1. Clona el repositorio

Clona este proyecto en tu máquina local usando el siguiente comando:

```bash
git clone https://github.com/darksqualls1990/smartjob.git
```

### 2. Ve al directorio del proyecto
Navega al directorio raíz del proyecto:

```bash
cd smartjob
```

### 3. Compila el proyecto
Ejecuta el siguiente comando para compilar el proyecto:

```bash
mvn clean install
```
### 4. Ejecuta la aplicación
Inicia la aplicación con el siguiente comando:

```bash
mvn spring-boot:run
```

### 5. Acceso a la consola de H2 (opcional)
Puedes acceder a la consola de la base de datos H2 en:

```bash
http://localhost:8080/h2-console
```
Usa las siguientes credenciales:

URL JDBC: jdbc:h2:mem:testdb
Usuario: sa
Contraseña:

### 6. Documentación de la API
Accede a la documentación interactiva de Swagger en:

```bash
http://localhost:8080/swagger-ui/index.html
```

## Estructura de archivos

El proyecto incluye las siguientes carpetas en la raíz:

### 1. Carpeta `sql`
Contiene los scripts necesarios para configurar la base de datos.

- `sql/DDL_ScriptSmartJob.sql`: Script para crear el modelo de base de datos.

### 2. Carpeta `diagrama_solucion`
Incluye el diagrama de la solución propuesta.

- `diagrama_solucion/Diagrama_de_componente.jpg`: Diagrama de componentes del sistema.

## Pruebas
El proyecto incluye pruebas unitarias implementadas con JUnit y Mockito.

Para ejecutar las pruebas, utiliza el siguiente comando:

```bash
mvn test
```

## Ejemplo de uso de la API

### Registro de usuario

Puedes realizar una petición al endpoint de registro de usuario con el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8080/users/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Juan Rodriguez",
    "email": "juan@odriguez.org",
    "password": "HRamirez-1990",
    "phones": [
        {
        "number": "1234567",
        "citycode": "1",
        "contrycode": "57"
        },
        {
        "number": "3175555324",
        "citycode": "1",
        "contrycode": "57"
        }
    ]
}'

```
