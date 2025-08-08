# 📚 ForoHub API

Este proyecto es una API RESTful para una plataforma de foro de discusión, desarrollada como parte del challenge de **Alura Latam** y **Oracle**.  
La API permite a los usuarios **registrarse, autenticarse y gestionar tópicos y cursos** de manera segura.

---

## 🛠️ Tecnologías Utilizadas

- **Java 21**: Lenguaje de programación principal.
- **Spring Boot 3**: Framework para la creación de aplicaciones robustas y escalables.
- **Spring Security**: Autenticación y autorización mediante JWT (JSON Web Tokens).
- **Spring Data JPA**: Capa de persistencia con acceso simplificado a la base de datos.
- **Flyway**: Migraciones de base de datos.
- **MySQL**: Base de datos relacional.
- **Maven**: Gestión de dependencias y ciclo de vida del proyecto.
- **JWT (auth0/java-jwt)**: Generación y validación de tokens de autenticación.

---

## ✨ Funcionalidades Principales

### 🔐 Autenticación y Registro
- **Registro de usuarios**: Cualquier persona puede crear una cuenta.
- **Login**: El usuario obtiene un **JWT** tras autenticarse con correo y contraseña.
- **Contraseñas cifradas** con BCrypt.

### 📌 Gestión de Tópicos (CRUD)
- **Crear**: Usuarios autenticados pueden publicar nuevos tópicos (evitando duplicados).
- **Leer**: Listado paginado o búsqueda por ID.
- **Actualizar**: Solo el autor puede editar título o mensaje.
- **Eliminar**: Solo el autor puede borrar su propio tópico.

### 🎓 Gestión de Cursos
- **Crear**: Usuarios autenticados pueden crear cursos.

---

## 🚀 Cómo Empezar

### 1. Clona el repositorio

```bash
git clone <url-del-repositorio>

2. Configura la base de datos
Crea una base de datos en MySQL llamada alura_forohub.

Configura las credenciales en src/main/resources/application.properties:

properties
Copiar
Editar
spring.datasource.url=jdbc:mysql://localhost:3306/alura_forohub
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Flyway se encargará de aplicar las migraciones automáticamente al iniciar el proyecto.

3. Ejecuta la aplicación
Desde tu IDE (IntelliJ IDEA, Eclipse) o usando Maven:

bash
Copiar
Editar
mvn spring-boot:run
📡 Endpoints de la API
🔐 Autenticación y Registro
POST /login → Autenticar usuario y obtener token JWT.

POST /usuarios → Registrar nuevo usuario.

📚 Tópicos
GET /topicos → Listado paginado de tópicos.

GET /topicos/{id} → Obtener tópico por ID.

POST /topicos → Crear tópico (requiere autenticación).

PUT /topicos → Actualizar tópico (requiere autenticación y ser autor).

DELETE /topicos/{id} → Eliminar tópico (requiere autenticación y ser autor).

🎓 Cursos
POST /cursos → Crear curso (requiere autenticación).

📥 Ejemplos de Peticiones (JSON)
🔸 Crear un nuevo usuario (POST /usuarios)
json
Copiar
Editar
{
  "nombre": "Juan",
  "correoElectronico": "juan@example.com",
  "contrasena": "123456"
}
🔸 Iniciar sesión (POST /login)
json
Copiar
Editar
{
  "correoElectronico": "juan@example.com",
  "contrasena": "123456"
}
🔐 Respuesta esperada:
Un token JWT en la propiedad token:

json
Copiar
Editar
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
Usa ese token en el header de las siguientes peticiones autenticadas:

makefile
Copiar
Editar
Authorization: Bearer <token>
🔸 Crear un nuevo tópico (POST /topicos)
json
Copiar
Editar
{
  "titulo": "Duda sobre inyección de dependencias",
  "mensaje": "No entiendo cómo funciona la inyección de dependencias en Spring Boot. ¿Podrían explicarlo con un ejemplo?",
  "autorId": 3,
  "cursoId": 1
}
🔸 Obtener detalles de un tópico (GET /topicos/{id})
Ejemplo:

bash
Copiar
Editar
GET http://localhost:8080/topicos/4
🔸 Actualizar un tópico (PUT /topicos)
json
Copiar
Editar
{
  "id": 3,
  "titulo": "Explicación detallada de inyección de dependencias en Spring",
  "mensaje": "Revisé la documentación y ahora entiendo mejor cómo funciona. ¿Podríamos discutir un ejemplo de inyección con una interfaz?"
}
🔸 Eliminar un tópico (DELETE /topicos/{id})
Ejemplo:

bash
Copiar
Editar
DELETE http://localhost:8080/topicos/4
✅ Estado actual
Autenticación funcionando con JWT.

Contraseñas cifradas con BCrypt.

Control de acceso por usuario autenticado y roles.

Protección de endpoints sensibles.

Migraciones con Flyway.

📌 Notas finales
Este proyecto fue desarrollado como práctica y demostración de conocimientos en desarrollo backend con Spring Boot y seguridad moderna con JWT.
