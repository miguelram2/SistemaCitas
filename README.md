# Sistema de Administración de Citas – Consultorio Clínico

Aplicación de consola en Java que simula un sistema de administración de citas médicas con control de acceso por administradores y persistencia en archivos CSV.

---

## Instalación y configuración

### Requisitos
- Java JDK 17 o superior → https://adoptium.net
- Git → https://git-scm.com

### Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/consultorio-citas.git
cd consultorio-citas
git checkout master
```

### Compilar
```bash
javac -d out src/com/consultorio/*.java
```

### Generar el FAT JAR
```bash
jar cfe consultorio-citas.jar com.consultorio.Main -C out .
```

### Ejecutar
```bash
java -jar consultorio-citas.jar
```

---

## Uso del programa

### Credenciales por defecto
| Usuario | Contraseña |
|---------|------------|
| admin   | admin123   |

### Menú principal
```
1. Gestión de doctores
2. Gestión de pacientes
3. Gestión de citas
0. Salir
```

### Archivos de datos
Los datos se almacenan automáticamente en la carpeta `db/`:
- `db/doctores.csv`
- `db/pacientes.csv`
- `db/citas.csv`
- `db/administradores.csv`

> Si los archivos no existen, el programa los regenera automáticamente al iniciar.

### Formato CSV
```
# doctores.csv
DOC001,Dr. Carlos Mendoza,Cardiología

# pacientes.csv
PAC001,Juan Pérez López

# citas.csv
CIT001,2025-09-15 10:00,Revisión cardiológica,DOC001,PAC001
```

---

## Créditos

Desarrollado como evidencia final del curso **Computación en Java**.

| Rol         | Nombre           |
|-------------|------------------|
| Desarrollador | _Miguel Ramírez_ |
| Instructor  | _Jesús Cazares_  |

---

## Licencia

Tecmilenio
