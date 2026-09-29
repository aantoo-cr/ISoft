# Cu2Service - Hackmaster API

## Descripción General

`Cu2Service` es una clase del servicio de la API encargada de gestionar la lógica relacionada con la obtención de datos para los reportes de **hackatones**. Esta capa intermedia entre el **controlador** y las **configuraciones del repositorio** permite realizar consultas eficientes y aplicar reglas de negocio para obtener información detallada sobre los organizadores, jueces y participantes.


## Métodos Principales

### 1. getDetailsByOrganizerId
Descripción: Obtiene métricas y estadísticas detalladas de los hackatones organizados por un organizador.

### 2. getDetailsListByOrganizerId
Descripción: Retorna una lista de reportes de todos los hackatones organizados por un actor en formato tabular.

### 3. getHackathonDetailsOrganizer
Descripción: Recupera detalles específicos de un hackatón organizado por un actor, como inscripciones y premios.

### 4. getDetailsListByJudgeId
Descripción: Obtiene una lista de hackatones calificados por un juez específico.

### 5. getHackathonDetailsJudge
Descripción: Recupera los reportes de calificaciones realizadas por un juez en un hackatón específico.

### 6. getDetailsListbyParticipantId
Descripción: Retorna una lista de los hackatones en los que ha participado un actor.

### 7. getHackathonDetailsParticipant
Descripción: Recupera los detalles de un hackatón específico para un participante, incluyendo equipos e inscripciones.

### 8. getDetailsParticipantbyId
Descripción: Recupera métricas y detalles sobre un participante específico, incluyendo el número de victorias, total de hackatones en los que ha participado y una lista de colaboradores.