
export class ChallengeDTO {
  nombre: string;
  descripcion: string;
  requisitos_Participantes: string;
  fechaI: Date;  // Usando Date para manejar fechas
  fechaT: Date;  // También Date para manejar fechas
  cuposParticipantes: number;
  urlImagen: string;
  criteriosEval: string[];
  challengePrice: number;

  constructor(
    nombre: string,
    descripcion: string,
    requisitos_Participantes: string,
    fechaI: Date,
    fechaT: Date,
    cuposParticipantes: number,
    urlImagen: string,
    criteriosEval: string[],
    challengePrice: number)
  {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.requisitos_Participantes = requisitos_Participantes;
    this.fechaI = fechaI;
    this.fechaT = fechaT;
    this.cuposParticipantes = cuposParticipantes;
    this.urlImagen = urlImagen;
    this.criteriosEval = criteriosEval;
    this.challengePrice = challengePrice;
  }
}
