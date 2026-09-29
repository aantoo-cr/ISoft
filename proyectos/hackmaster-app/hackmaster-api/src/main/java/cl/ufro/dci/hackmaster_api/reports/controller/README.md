# ReportsController - Hackmaster API

## Descripción General

Este controlador de la API maneja los reportes relacionados con los organizadores, jueces, y participantes de hackatones. Cada endpoint corresponde a uno de los casos de uso definidos en el proyecto, permitiendo visualizar diferentes reportes y métricas de hackatones en los que los actores (organizadores, jueces, o participantes) están involucrados.

## Endpoints de la API

### 1. CU01_MostrarReporteMetricasPromedioOrganizador

URL: /api/reports/organizador/metricas/{actorId}
Descripción: Muestra inscripciones por hackatón, premios en dinero, e ingresos generados.
Método HTTP: GET
Parámetro: actorId (ID del organizador)

### 2. CU02_MostrarTablaReportesPorHackathonDelOrganizador

URL: /api/reports/organizador/lista/{actorId}
Descripción: Muestra la lista de hackatones organizados, incluyendo fechas de inicio y término, y nombre del evento.
Método HTTP: GET
Parámetro: actorId (ID del organizador)

### 3. CU03_VisualizarReporteInscripcionEquipos

URL: /api/reports/organizador/challenge/{actorId}/{hackathonId}
Descripción: Muestra un reporte detallado de las inscripciones y equipos de un hackatón, y composición de los equipos.
Método HTTP: GET
Parámetros:
actorId (ID del organizador)
hackathonId (ID del hackatón)

### 4. CU04_MostrarTablaReportesPorJuez

URL: /api/reports/juez/lista/{judgeId}
Descripción: Muestra los hackatones calificados por un juez, con fechas de inicio y término, y nombres de los eventos.
Método HTTP: GET
Parámetro: judgeId (ID del juez

### 5. CU05_VisualizarReporteCalificaciones

URL: /api/reports/juez/challenge/{judgeId}/{hackathonId}
Descripción: Visualiza un reporte de las calificaciones grupales, incluyendo comparativos con las notas del juez y el promedio de otros jueces.
Método HTTP: GET
Parámetros:
judgeId (ID del juez)
hackathonId (ID del hackatón)

### 6. CU06_MostrarTablaReportesPorParticipante

URL: /api/reports/participante/lista/{actorId}
Descripción: Muestra los hackatones en los que un participante se ha registrado, incluyendo fechas y nombres de eventos.
Método HTTP: GET
Parámetro: actorId (ID del participante)

### CU07_VisualizarReporteHackatonEquipo

URL: /api/reports/participante/challenge/{actorId}/{hackathonId}
Descripción: Muestra información del hackatón y su equipo, con datos como calificaciones finales, posicionamiento, y comparativo de resultados.
Método HTTP: GET
Parámetros:
actorId (ID del participante)
hackathonId (ID del hackatón)

### CU08_MostrarReporteMétricasPromedioParticipante

URL: /api/reports/participante/detalles/{actorId}
Descripción: Muestra un reporte del participante, como nombre, con las métricas de participación promedio en los Hackatones como: 
total de hackatones participados, tasa de victorias totales del participante y tabla con participantes frecuentes.
Parámetros:
actorId (ID del participante)


