# Mutant Analyzer

## Descripción del Proyecto

Magneto quiere reclutar tantos mutantes como sea posible para luchar contra los X-Men.  
Este proyecto es una API REST diseñada para detectar si un humano es mutante basándose en su secuencia de ADN.

El sistema recibe como parámetro un array de Strings que representan cada fila de una matriz (NxN).  
Las letras permitidas son únicamente: **A, T, C, G**, que representan las bases nitrogenadas del ADN.

### Condición de Mutante

Un humano es considerado mutante cuando existen **más de una secuencia** de **cuatro letras iguales consecutivas** en alguna de las siguientes direcciones:

- Horizontal
- Vertical
- Diagonal
- Diagonal inversa

### Ejemplo de ADN Mutante

```json
[
  "ATGAGC",
  "TACGGC",
  "GGCTAC",
  "TTTTAC",
  "GACATG",
  "ATGGCC"
]
```

En este ejemplo existen secuencias válidas en varias direcciones, por lo que el sujeto es un **MUTANTE**.

---

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot (Web, Data JPA)**
- **Hibernate Envers**
- **H2 Database**
- **Gradle**

---

## Instalación y Ejecución

### Prerrequisitos

- **Java JDK 21**

### Pasos

```bash
git clone https://github.com/SantiWengorra/Ejercicio-Mutantes-Wengorra.git
cd Mutantes-Wengorra
./gradlew clean build
./gradlew bootRun
```

La aplicación iniciará en:

```
http://localhost:8080
```

---

## Endpoints

### 🔍 Detectar Mutante
**POST** `/api/mutant/analizar`

#### Request Body

```json
{
  "nombre": "Santiago",
  "apellido": "Wengorra",
  "adn": [
    "ATGAGC",
    "TACGGC",
    "GGCTAC",
    "TATTAC",
    "GACATG",
    "ATGGCC"
  ]
}
```

#### Respuestas

- **200 OK** → La persona es un mutante
- **200 OK** → La persona no es un mutante
- **400 Bad Request** → ADN inválido
