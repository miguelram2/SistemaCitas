Instalación y configuración Requisitos previos Java JDK 17 o superior Clonar el repositorio



git clone https://github.com/miguelram2/sistemacitas.git

cd sistemacitas

git checkout develop

Compilar el proyecto (FAT JAR)



mvn clean package

El archivo ejecutable se generará en target/sistemacitas-1.0-SNAPSHOT-jar-with-dependencies.jar.

Uso del programa Ejecutar



java -jar target/sistemacitas-1.0-SNAPSHOT-jar-with-dependencies.jar

Opciones del menú principal



1\. Gestión de doctores

2\. Gestión de pacientes

3\. Gestión de citas

4\. Salir

Archivos CSV generados Los datos se almacenan automáticamente en la misma carpeta del JAR: doctores.csv — registro de doctores pacientes.csv — registro de pacientes citas.csv — registro de citas administradores.csv — usuarios del sistema

Créditos Desarrollado como evidencia final de Computación en Java. Desarrollador Miguel Angel Ramírez Montiel Supervisor Jesús Cazares Martínez

Licencia Este proyecto se distribuye bajo la licencia TecMilenio. Copyright (c) 2025

