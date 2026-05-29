# SistemaCitas
Programa que simulará un sistema de  administración de citas para un consultorio clínico
Sistema de Administración de Citas – Consultorio Clínico
Aplicación de consola desarrollada en Java que simula un sistema de administración de citas médicas con control de acceso por administradores.

Instalación y configuración
Requisitos previos

Java JDK 17 o superior

Clonar el repositorio
bashgit clone https://github.com/miguelram2/sistemacitas.git
cd consultorio-citas
git checkout develop
Compilar el proyecto
bashjavac -d out src/com/consultorio/*.java
Generar el JAR ejecutable
bashjar cfe consultorio-citas.jar com.consultorio.Main -C out .

Uso del programa
Ejecutar
bashjava -jar consultorio-citas.jar

Opciones del menú principal
1. Gestión de doctores
2. Gestión de pacientes
3. Gestión de citas
4. Salir
Archivos CSV generados
Los datos se almacenan automáticamente en la misma carpeta del JAR:

doctores.csv — registro de doctores
pacientes.csv — registro de pacientes
citas.csv — registro de citas
administradores.csv — usuarios del sistema

Créditos
Desarrollado como evidencia final de Computación en Java.
Desarrollador	Miguel Angel Ramírez Montiel
Supervisor	Jesús Cazares Martínez
---
Licencia
Este proyecto se distribuye bajo la licencia TecMilenio.
Copyright (c) 2025
