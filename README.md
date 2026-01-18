# Biblionet – Sistema de Gestión Bibliotecaria

**Biblionet** es un sistema web para la gestión integral de una biblioteca académica.

Permite administrar libros, ejemplares, usuarios, préstamos y reportes, organizando la funcionalidad por roles y priorizando el orden, la mantenibilidad y las buenas prácticas de desarrollo.

El proyecto fue **desarrollado de forma autónoma para fines académicos**, aplicando una arquitectura clara y decisiones técnicas coherentes.

La gestión de autenticación y roles se implementó mediante **sesiones HTTP** (`HttpSession`), sin utilizar **Spring Security**, con el objetivo de comprender y controlar directamente el manejo de sesiones y la lógica de acceso.

<img width="1362" height="681" alt="image" src="https://github.com/user-attachments/assets/d02388d2-93e3-4ec6-986e-50730355dc28" />

## 1. Objetivo del Proyecto

El objetivo principal de **Biblionet** es aplicar de forma práctica los conceptos fundamentales del desarrollo backend con **Spring Boot**, organizando correctamente la lógica del sistema y manteniendo una clara separación de responsabilidades.

El proyecto se enfocó en:

* Diseñar un sistema **realista y funcional**, basado en el funcionamiento de una biblioteca académica.
* Aplicar **buenas prácticas** en la estructura de controladores, servicios y repositorios.
* Implementar **flujos completos de negocio** (como préstamos, sanciones y reportes), evitando el desarrollo de simples CRUD aislados.
* Tomar decisiones técnicas conscientes orientadas a la **claridad, mantenibilidad y coherencia** del sistema.

## 2. Roles del Sistema

### 👤 Administrador
Es el encargado de la gestión global y supervisión del sistema. Sus funciones principales incluyen:

* **Gestión Integral:** Control total sobre usuarios, inventario de libros y categorías.
* **Supervisión de Préstamos:** Monitoreo del flujo completo de registro y estado de los préstamos.
* **Generación de Reportes:** Capacidad para generar 2 tipos de reportes especializados.
* **Dashboard Inteligente:**
    * **6 KPI Cuantitativos:** Contadores en tiempo real para métricas clave del sistema.
    * **2 KPI Analíticos:** Visualización de datos mediante gráficos dinámicos para facilitar la toma de decisiones.

<img width="1366" height="685" alt="image" src="https://github.com/user-attachments/assets/bcbede04-0bb7-4fa5-8b14-3f530db2214a" />

### 📚 Bibliotecario
Este rol se enfoca en la operatividad diaria de la biblioteca y el control de los ejemplares. Sus responsabilidades son:

* **Gestión de Inventario:** Control directo sobre los libros y las categorías del sistema.
* **Control de Préstamos:** Supervisión del flujo completo de registro y seguimiento de préstamos.
* **Reportes Operativos:** Generación de un tipo de reporte específico para la gestión de la biblioteca.
* **Dashboard de Monitoreo:**
    * **3 KPI Cuantitativos:** Contadores en tiempo real para métricas operativas.
    * **2 KPI Analíticos:** Gráficos dinámicos que facilitan el monitoreo de préstamos basados en ejemplares específicos.

<img width="1365" height="684" alt="image" src="https://github.com/user-attachments/assets/dad1b291-d77e-4d45-ab85-2ebb855b2cf1" />

### 🎓 Docente / Alumnos
Este rol representa a los usuarios finales del sistema, enfocándose en el acceso a la información y el seguimiento personal:

* **Consulta de Libros:** Acceso al catálogo completo con filtros por categorías para verificar disponibilidad.
* **Historial Personal:** Seguimiento detallado de sus propios préstamos realizados a lo largo del tiempo.
* **Dashboard de Usuario:**
    * **3 KPI Cuantitativos:** Indicadores numéricos sobre su actividad en la biblioteca.
    * **Historial de Interacciones:** Panel en tiempo real que detalla las **últimas 5 acciones** realizadas (consultas, filtros, etc.).

<img width="1366" height="683" alt="image" src="https://github.com/user-attachments/assets/dbab77da-c102-42d6-a50f-b50b89afaf30" />

## 3. Funcionalidades Principales

* Gestión de Usuarios, libros y categorías.
* Registro y control de préstamos.
* Generación de reportes.
* Historial de préstamos por usuario.
* Dashboards por rol.
* Reportes administrativos (libros más prestados, usuarios sancionados, devoluciones pendientes)
* Validaciones de disponibilidad y sanciones.

## 4. Tecnologías Utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* Thymeleaf
* Tailwind CSS (CDN)
* MySQL
* Maven

## 5. Arquitectura y Decisiones Clave

* Arquitectura **MVC** con **Spring Boot**
* Controladores delgados, sin lógica de negocio
* Lógica centralizada en la capa **Service**
* Uso de **DTOs** para reportes y consultas complejas
* Reutilización de vistas mediante un **layout** único (`layout/dashboard`)
* Inyección dinámica de contenido en vistas según el rol
* Separación clara entre autenticación y lógica de negocio

Estas decisiones permiten que el sistema sea fácil de mantener, extender y comprender.

## 6. Arquitectura en Capas

El proyecto sigue una **arquitectura en capas**, con responsabilidades bien definidas para garantizar orden, mantenibilidad y escalabilidad.

El flujo general del sistema es el siguiente:

* **Controller:** recibe las solicitudes HTTP, valida el contexto de sesión y delega la lógica al servicio correspondiente. No contiene lógica de negocio.
* **Service:** concentra la lógica de negocio del sistema y coordina las operaciones necesarias según el caso de uso.
* **Repository:** se encarga del acceso a datos mediante JPA, gestionando consultas y persistencia de las entidades.
* **Model / Entity:** representa las entidades del dominio y la estructura de la base de datos.
* **DTOs:** se utilizan cuando se requieren respuestas optimizadas o reportes específicos, evitando exponer directamente las entidades.

## 7. Flujo de Registro de Préstamos

El registro de préstamos se diseñó considerando la lógica real de una biblioteca.

El préstamo se realiza sobre un **ejemplar específico** y no directamente sobre el libro, permitiendo un control correcto de la disponibilidad.

El flujo implementado incluye:

* Selección de un usuario ya sea docente o alumno que este activo
* Selección del libro solicitado
* Carga de ejemplares disponibles según el libro escogido
* validaciones de disponibilidad y estado del usuario
* Escoger la fecha de devolución con la regla de solo fechas en adelante de la actual
* registro del préstamo y actualización del estado del ejemplar.

<img width="1366" height="686" alt="image" src="https://github.com/user-attachments/assets/fb09a4e4-bb48-4c52-9238-7e10df8da264" />

## 8. Ejecución del proyecto

### 🔧 Requisitos
- Java JDK 17
- Maven
- MySQL 8
- Git
- Entorno de desarrollo:
  - IntelliJ IDEA (recomendado)
  - Visual Studio Code (válido con extensiones Java)

### 🗄️ Base de datos
Crear la base de datos en MySQL antes de ejecutar el proyecto:

```sql
CREATE DATABASE datanet;


