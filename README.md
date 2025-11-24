# Algoritmos de Ordenación y Comparación con Datasets CSV

## Unidad 2 – Estructura de Datos

---

## 1. Introducción

Este proyecto implementa un sistema profesional de comparación de algoritmos de ordenación, desarrollado para el Taller 6 – Comparación Empírica de Ordenación.
El programa permite evaluar y contrastar los algoritmos clásicos:

* **Insertion Sort**

* **Selection Sort**

* **Bubble Sort (con corte temprano)**

utilizando datasets reales en formato CSV, capaces de reflejar distintos escenarios:

* **Datos aleatorios**

* **Datos casi ordenados**

* **Datos con duplicados**

* **Datos totalmente invertidos**

El sistema trabaja mediante un menú interactivo de consola, desde el cual el usuario puede:

* **Cargar CSVs incluidos en resources/data/**

* **Cargar CSVs desde una ruta manual**

* **Ejecutar mediciones profesionales (R=10 repeticiones → mediana de 7)**

* **Analizar comparaciones, swaps y tiempos**

* **Exportar el historial**

* **Ver los resultados directamente en pantalla**

Se diseñó con una arquitectura por capas siguiendo buenas prácticas de ingeniería de software.

---

## 2. Arquitectura del Proyecto

El proyecto sigue una arquitectura limpia por capas, lo que permite una separación profesional entre el dominio, servicios, persistencia, utilidades y presentación.

```
src/
└── comparacion/
    ├── app/
    │   ├── Main.java
    │   └── MenuCLI.java
    ├── domain/
    │   ├── Record.java
    │   ├── Cita.java
    │   ├── Paciente.java
    │   └── Inventario.java
    ├── service/
    │   ├── CsvLoader.java
    │   ├── SortService.java
    │   ├── InsertionSort.java
    │   ├── SelectionSort.java
    │   ├── BubbleSort.java
    │   └── SortMetrics.java
    ├── persistence/
    │   ├── CsvReader.java
    │   └── HistoryRepository.java
    ├── util/
    │   ├── AnsiColors.java
    │   └── MedianCalculator.java
    └── resources/
        └── data/
            ├── citas_100.csv
            ├── citas_100_casi_ordenadas.csv
            ├── pacientes_500.csv
            └── inventario_500_inverso.csv
```

### Descripción de Capas
|Capa|	Función|
| :--- | :--- |
|app	|Punto de entrada y menú interactivo del sistema.|
|domain	|Modelos de datos del CSV (Cita, Paciente, Inventario).
|service	|Lógica de negocio: carga de CSV, ordenación, instrumentación y comparación.|
|persistence	|Lectura de archivos CSV y almacenamiento del historial.|
|util	|Colores ANSI, cálculos auxiliares, medianas, formato.|
|resources/data/	|Datasets reproducibles del taller (UTF-8, delimitador ;).|

---

## 3. Algoritmos Implementados

### Insertion Sort

* **Estable**

* Excelente para datos **casi ordenados**

* Complejidad O(n) en el mejor caso

* Minimiza desplazamientos en escenarios favorables

* Útil para arreglos pequeños

* Recorre elementos previos para insertar en la posición adecuada

### Selection Sort

* **No estable**

* Realiza muy pocos swaps (a lo sumo n−1)

* Recorre siempre el arreglo completo para buscar el mínimo

* Complejidad siempre O(n²), sin importar el orden

* Recomendado cuando el costo del swap es elevado

### Bubble Sort (con corte temprano)

* **Estable**

* Muy visual y didáctico

* Realiza swaps solo entre elementos adyacentes

* Incluye corte temprano cuando no se realizan intercambios

* Muy eficiente cuando el arreglo está casi ordenado

* Ineficiente en casos adversos (orden inverso)

---

## 4. Datasets del Taller (CSV)

Los datasets utilizados fueron generados con semilla 42 para mantener reproducibilidad y respetan el formato:

```
UTF-8
Delimitador: ;
Encabezado en la primera fila
Fechas ISO 8601: YYYY-MM-DDTHH:MM
```

Archivos incluidos:

| Archivo | Tamaño | Descripción | 
| :--- | :--- |  :--- |
| citas_100.csv	| 100	| Aleatorio, | basado en fecha y hora| 
| citas_100_casi_ordenadas.csv	| 100	| Casi ordenado | 
| pacientes_500.csv	| 500	 | Con duplicados, basado en apellido | 
| inventario_500_inverso.csv	| 500	| Totalmente inverso (stock) | 

Los archivos se encuentran en:

```src/main/resources/data/```


y se cargan mediante:

```getResourceAsStream("/data/nombre.csv")```

---

## 5. Sistema de Comparación

El sistema implementa un proceso profesional de medición:

* R = 10 repeticiones

* Descartar primeras 3 (calentamiento JVM/JIT)

* Tomar la mediana de las 7 restantes

* Cargar CSV fuera del tiempo medido

* Comparaciones registradas (comparisons++)

* Swaps registrados (swaps++)

* Tiempo medido con System.nanoTime()

Métricas registradas:

* Comparaciones

* Swaps

* Tiempo (mediana)

* Tamaño del dataset

* Tipo de orden

* Algoritmo aplicado

* Fecha/hora de ejecución

Ejemplo:

```
Algoritmo: InsertionSort
Dataset: citas_100.csv
Comparaciones: 428
Swaps: 95
Tiempo mediana: 162,200 ns
```
---

## 6. Requisitos del Sistema
### Software necesario

* **Java JDK 21 o superior**
    * (Recomendado: JDK 25 OpenJDK)
* Cualquier IDE Java:
    * IntelliJ IDEA
    * NetBeans
    * Eclipse
    * Visual Studio Code con extensión Java
* Git + GitHub

###  Sistema operativo

Funciona en:

* Windows
* Linux
* macOS

---

## 7. Cómo clonar y ejecutar el proyecto

### 1. Clonar el repositorio
```git clone https://github.com/R0yalCode/Comparacion-.git```

### 2. Navegar al proyecto
```cd Comparacion```

###  3. Compilar el proyecto
``` javac -d bin src/ed/u2/sorting/**/*.java ```

###  4. Ejecutar el programa
``` java -cp bin comparacion.app.Main```

---

## 8. Instrucciones de Uso

Al ejecutar el programa aparece:

======= COMPARADOR DE ALGORITMOS =======

1) Cargar CSV manualmente
2) Ver historial
3) Exportar historial
0) Salir

### Opciones disponibles:

* Seleccionar dataset (1–4)

* Seleccionar algoritmo:

* Evaluar todos

* **1.Inserción**

* **2.Selección**

* **3.Burbuja**

* Ver historial

* Exportar historial .txt

* Salir del programa

---

## 9. Formato de los archivos CSV

Cada archivo debe respetar:


```
id;campo1;campo2
UTF-8

Ejemplo:

id_cita;apellido;fechaHora
C001;Pérez;2025-03-15T09:30
C002;López;2025-03-15T10:05
```

## 10. Historial de Comparaciones

* El historial registra:

* Algoritmo aplicado

* Archivo utilizado

* Comparaciones

* Swaps

* Tiempo (mediana)

* Fecha/hora

* Resultado ordenado

Ejemplo:

```
[2025-03-20 14:21]
Archivo: pacientes_500.csv
Algoritmo: SelectionSort
Comparaciones: 124750
Swaps: 499
Tiempo: 4.82 ms
```
---

## 11. Excepciones manejadas

* El sistema detecta:

* Archivo CSV inexistente

* Archivo vacío

* Formato incorrecto

* Delimitador inválido

* Campos inconsistentes

* Ruta incorrecta

* Datos numéricos faltantes o mal escritos

* Mensajes amigables evitan fallos en tiempo de ejecución.

---

## 12. Conclusiones

Este proyecto permitió:

* Comprender el comportamiento real de Inserción, Selección y Burbuja

* Medir tiempos reales usando métodos profesionales (mediana, múltiples repeticiones, aislamiento de IO)

* Trabajar con datos estructurados provenientes de archivos CSV

* Aplicar una arquitectura por capas que facilita mantenimiento y escalabilidad

* Identificar qué algoritmo es más conveniente según:

* **grado de orden**

* **duplicados**

* **tamaño**

* **swaps**

* **estabilidad**



## Autores: 
### [Royel Jima](https://github.com/R0yalCode)
### [Darwin Jimbo](https://github.com/Darwin-J5)
