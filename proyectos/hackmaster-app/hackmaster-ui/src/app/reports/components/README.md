# Componentes de la UI

Este directorio contiene los componentes de la UI que utilizan los servicios de la UI. Cada componente corresponde a un servicio específico y maneja la presentación de los datos obtenidos de la API en la aplicación.
|
## Componentes

### 1. `cu1`: Componente para Métricas del Organizador
- **Descripción**: Muestra las métricas generales de los hackatones gestionados por un organizador.
- **Servicio Asociado**: `CU1Service`
- **Funcionalidades**:
  - Obtiene y muestra los ingresos, premios y total de inscripciones por hackathon.

### 2. `cu2`: Componente para Lista de Hackatones de un Organizador
- **Descripción**: Muestra una lista de hackatones organizados por un actor específico.
- **Servicio Asociado**: `CU2Service`
- **Funcionalidades**:
  - Visualiza el nombre, fecha de inicio y fecha de término de cada hackathon organizado.

### 3. `cu3`: Componente para Detalles de un Hackathon
- **Descripción**: Proporciona detalles específicos de un hackathon gestionado por un organizador.
- **Servicio Asociado**: `CU3Service`
- **Funcionalidades**:
  - Muestra información sobre el número de registros, ingresos totales, y número de equipos.

### 4. `cu4`: Componente para Lista de Hackatones de un Juez
- **Descripción**: Muestra una lista de hackatones en los que participa un juez.
- **Servicio Asociado**: `CU4Service`
- **Funcionalidades**:
  - Visualiza los hackatones, incluyendo fechas de inicio y término.

### 5. `cu5`: Componente para Reporte de Hackathon para Juez
- **Descripción**: Proporciona un reporte de hackathon desde la perspectiva de un juez.
- **Servicio Asociado**: `CU5Service`
- **Funcionalidades**:
  - Muestra el ranking de equipos y calificaciones otorgadas por el juez.

### 6. `cu6`: Componente para Detalles del Participante
- **Descripción**: Proporciona detalles sobre hackatones en los que participa un actor.
- **Servicio Asociado**: `CU6Service`
- **Funcionalidades**:
  - Muestra la lista de hackatones, incluyendo nombres y fechas relevantes.

### 7. `cu7`: Componente para Detalles del Hackathon para Participante
- **Descripción**: Muestra detalles del hackathon desde la perspectiva de un participante.
- **Servicio Asociado**: `CU7Service`
- **Funcionalidades**:
  - Muestra información sobre el equipo del participante, calificaciones y posición en el ranking.

### 8. `cu8`: Componente para Detalles de Colaboración de Participante
- **Descripción**: Devuelve detalles de colaboraciones y logros de un participante en hackatones.
- **Servicio Asociado**: `CU8Service`
- **Funcionalidades**:
  - Visualiza el número de victorias, total de hackatones en los que ha participado y colaboradores frecuentes.
