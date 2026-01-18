# Biblionet – Sistema de Gestión Bibliotecaria

**Biblionet** es un sistema web para la gestión integral de una biblioteca académica.

Permite administrar libros, ejemplares, usuarios, préstamos y reportes, organizando la funcionalidad por roles y priorizando el orden, la mantenibilidad y las buenas prácticas de desarrollo.

El proyecto fue **desarrollado de forma autónoma con fines académicos**, aplicando una arquitectura clara y decisiones técnicas coherentes.

La gestión de autenticación y roles se implementó mediante **sesiones HTTP** (`HttpSession`), sin utilizar **Spring Security**, con el objetivo de comprender y controlar directamente el manejo de sesiones y la lógica de acceso.

<img width="1362" height="681" alt="image" src="https://github.com/user-attachments/assets/d02388d2-93e3-4ec6-986e-50730355dc28" />

---
## 1. Objetivo del Proyecto

El objetivo principal de **Biblionet** es aplicar de forma práctica los conceptos fundamentales del desarrollo backend con **Spring Boot**, organizando correctamente la lógica del sistema y manteniendo una clara separación de responsabilidades.

El proyecto se enfocó en:

* Diseñar un sistema **realista y funcional**, basado en el funcionamiento de una biblioteca académica.
* Aplicar **buenas prácticas** en la estructura de controladores, servicios y repositorios.
* Implementar **flujos completos de negocio** (como préstamos, sanciones y reportes), evitando el desarrollo de simples CRUD aislados.
* Tomar decisiones técnicas conscientes orientadas a la **claridad, mantenibilidad y coherencia** del sistema.
