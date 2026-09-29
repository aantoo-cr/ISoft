# Servicios de la UI

Este directorio contiene los servicios utilizados por la UI para interactuar con la API de Hackmaster. Cada servicio utiliza HTTP para acceder a los datos necesarios para distintos módulos de la aplicación.

## Servicios

### 1. `CU1Service`: Métricas del Organizador
- **Endpoint**: `/api/reports/organizador/metricas/{actorId}`
- **Descripción**: Obtiene métricas generales de hackatones gestionados por un organizador.
- **Método**: `GET`
- **Parámetros**:
  - `actorId`: ID del organizador.
- **Retorno**:
  ```typescript
  {
    actorType: string,
    actorName: string,
    hackatones: HackathonMetric[]
  }
### 2. `CU2Service`: Lista de Hackatones de un Organizador
- **Endpoint**: `/api/reports/organizador/lista/{actorId}`
- **Descripción**: Devuelve una lista de hackatones organizados por un actor específico.
- **Método**: `GET`
- **Parámetros**:
  - `actorId`: ID del organizador.
- **Retorno**:
  ```typescript
   {
   actorType: string,
   actorName: string,
   hackatones: Hackaton[]
   }
### 3. `CU3Service`: Detalles de un Hackathon
- **Endpoint**: `/api/reports/organizador/challenge/{actorId}/{hackathonId}`
- **Descripción**: Proporciona detalles específicos sobre un hackathon gestionado por un organizador.
- **Método**: `GET`
- **Parámetros**:
  - `actorId`: ID del organizador.
  - `hackathonId`: ID del hackathon.
- **Retorno**:
  ```typescript
   {
   numberOfRegistrations: number,
   actorType: string,
   membersCountPerTeam: number[],
   actorName: string,
   hackatonName: string,
   totalRevenue: number,
   numberOfTeams: number
   }
### 4. `CU4Service`: Lista de Hackatones de un Juez
- **Endpoint**: `/api/reports/juez/lista/{judgeId}`
- **Descripción**: Obtiene una lista de hackatones en los que participa un juez.
- **Método**: `GET`
- **Parámetros**:
  - `judgeId`: ID del juez.
- **Retorno**:
  ```typescript
   {
   actorType: string,
   actorName: string,
   hackatones: JudgeHackathon[]
   }
### 5. `CU5Service`: Reporte de Hackathon para el Juez
- **Endpoint**: `/api/reports/juez/challenge/{judgeId}/{hackathonId}`
- **Descripción**: Proporciona un reporte del hackathon desde la perspectiva de un juez.
- **Método**: `GET`
- **Parámetros**:
  - `judgeId`: ID del juez.
  - `hackathonId:` ID del hackathon.
- **Retorno**:
  ```typescript
   {
   actorType: string,
   actorName: string,
   hackatonName: string,
   ranking: TeamRanking[],
   grades: JudgeGrade[]
   }
### 6. `CU6Service`: Detalles del Participante
- **Endpoint**: `/api/reports/participante/lista/{actorId}`
- **Descripción**: Proporciona detalles sobre hackatones en los que participa un actor.
- **Método**: `GET`
- **Parámetros**:
  - `actorId`: ID del participante.
- **Retorno**:
  ```typescript
   {
   actorType: string,
   actorName: string,
   hackatonesDetails: HackathonDetail[]
   }
### 7. `CU7Service`: Detalles del Hackathon para Participante
- **Endpoint**: `/api/reports/participante/challenge/{actorId}/{hackathonId}`
- **Descripción**: Devuelve detalles del hackathon desde la perspectiva de un participante.
- **Método**: `GET`
- **Parámetros**:
  - `actorId`: ID del organizador.
  - `hackathonId`: ID del hackathon.
- **Retorno**:
  ```typescript
   {
   teamName: string,
   actorType: string,
   averageHackathonGrade: number,
   hackathonName: string,
   integrantesGrupo: string[],
   actorName: string,
   notaGrupo: number,
   ranking: string[]
   }
### 8. `CU8Service`: Detalles de Colaboración de Participante
- **Endpoint**: `/api/reports/participante/detalles/{actorId}`
- **Descripción**: Devuelve detalles de colaboraciones y logros de un participante en hackatones.
- **Método**: `GET`
- **Parámetros**:
  - `actorId`: ID del organizador.
- **Retorno**:
  ```typescript
   {
   actorType: string,
   numberOfVictories: number,
   totalHackatonesParticipados: number,
   collaborators: CollaborationDetail[],
   actorName: string
   }
