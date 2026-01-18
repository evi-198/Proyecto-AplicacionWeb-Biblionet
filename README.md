# Biblionet – Sistema de Gestión Bibliotecaria

**Biblionet** es un sistema web para la gestión integral de una biblioteca académica.

Permite administrar libros, ejemplares, usuarios, préstamos y reportes, organizando la funcionalidad por roles y priorizando el orden, la mantenibilidad y las buenas prácticas de desarrollo.

El proyecto fue **desarrollado de forma autónoma con fines académicos**, aplicando una arquitectura clara y decisiones técnicas coherentes.

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


