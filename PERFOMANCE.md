Reporte de Performance - TP5 Inventario Inteligente
Grupo: Bruno Sebastian Sandoval-EISI 1256, Valentino Lemor

## 1. Tabla de Complejidad Teórica

A continuación se detalla el análisis asintótico (Big O) de las operaciones principales del sistema, asumiendo el uso de `ConcurrentHashMap` como estructura de almacenamiento en memoria.

| **Endpoint**                   | **Operación dominante**                 | **Big O teórico** | **Justificación**                                                                                                                                |
| ------------------------------ | --------------------------------------- | ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
| `GET /api/productos`           | `Stream.filter()` sobre `values()`      | $O(n)$            | Se debe iterar sobre los $n$ elementos del mapa para recolectarlos en una lista y aplicar posibles filtros.                                      |
| `GET /api/productos/{id}`      | `ConcurrentHashMap.get(key)`            | $O(1)$            | Búsqueda directa en tabla hash. Gracias a la función de dispersión, el acceso es de tiempo constante amortizado.                                 |
| `POST /api/productos`          | `ConcurrentHashMap.put(key, value)`     | $O(1)$            | Inserción directa en tabla hash. Costo constante amortizado (salvo colisiones severas o rehashing).                                              |
| `PUT /api/productos/{id}`      | `ConcurrentHashMap.put(key, value)`     | $O(1)$            | Reemplazo de un valor existente en la tabla hash mediante su clave.                                                                              |
| `DELETE /api/productos/{id}`   | `ConcurrentHashMap.remove(key)`         | $O(1)$            | Eliminación directa de la tabla hash mediante su clave.                                                                                          |
| `GET /api/productos/buscar?q=` | `Stream.filter()` + `String.contains()` | $O(n \cdot m)$    | Itera los $n$ productos. Por cada uno, ejecuta `contains()`, el cual tiene complejidad $O(m)$ donde $m$ es la longitud de la cadena de búsqueda. |
| `GET /api/productos/ordenados` | `List.sort()` (TimSort)                 | $O(n \log n)$     | El motor de Java utiliza TimSort, cuyo peor caso y caso promedio garantiza un rendimiento logarítmico-lineal.                                    |
| `POST /api/movimientos`        | `get()` + `AtomicInteger.addAndGet()`   | $O(1)$            | Buscar el producto es $O(1)$. La operación atómica a nivel de CPU sobre el stock también se resuelve en tiempo constante.                        |
| `GET /api/alertas/stock-bajo`  | Patrón Strategy iterativo               | $O(n)$            | Se itera el mapa completo ($n$ elementos) evaluando cada producto individualmente contra la regla de negocio inyectada.                          |




# 2. Tabla de Mediciones Empíricas
 Las mediciones fueron tomadas utilizando `System.nanoTime()`. Se incluyó una fase de "warm-up" (calentamiento) previa a la medición real para permitir que el compilador JIT (Just-In-Time) de la JVM optimice el bytecode.

| **Endpoint**                     | **1.000 registros** | **10.000 registros** | **100.000 registros** | **Escala observada** |
| -------------------------------- | ------------------- | -------------------- | --------------------- | -------------------- |
| `GET /api/productos (O(n))`      | 1231500 ns          | 2042600 ns           | 6360800 ns            | Lineal               |
| `GET /api/productos/{id} (O(1))` | 12100 ns            | 11500 ns             | 14200 ns              | Constante            |

## 3.  Teoría vs. Realidad

Al analizar las mediciones empíricas frente a la teoría matemática de la notación Big O, se pueden observar ciertas discrepancias. Estas variaciones son esperables en un entorno de ejecución administrado como la Java Virtual Machine (JVM) y se justifican por los siguientes factores técnicos:

1. **Overhead de Streams en volúmenes bajos:**
    
    Para un número muy pequeño de registros (ej. 1.000), una operación $O(1)$ podría registrar un tiempo engañosamente alto en comparación con iteraciones simples. Esto se debe al "overhead" (costo de inicialización) que tiene la API de Streams de Java al crear la tubería de datos y las lambdas, lo cual diluye la velocidad teórica en conjuntos de datos chicos.
    
2. **Impacto del Garbage Collector (GC):**
    
    Las mediciones tomadas con `System.nanoTime()` incluyen interrupciones microscópicas. Si el Garbage Collector de Java decide ejecutarse para limpiar objetos huérfanos durante la medición de los 100.000 registros, el tiempo reportado incluirá esa pausa ("Stop-the-world pause"), mostrando un salto de tiempo que no es culpa de la complejidad del algoritmo en sí.
    
3. **Colisiones en el Hash y Segmentación de Locks:**
    
    Si bien el `ConcurrentHashMap` garantiza $O(1)$, cuando insertamos 100.000 registros de golpe, el mapa interno debe redimensionarse (Rehashing) varias veces para acomodar la carga. Si la medición capturó el milisegundo exacto donde ocurrió un rehash, ese $O(1)$ momentáneamente se comportará como $O(n)$.