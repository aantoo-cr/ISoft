export class avance {
  description: string| undefined;
  urlAvance: string | undefined;
  date: Date | undefined;

  constructor(urlAvance: string) {
    this.urlAvance = urlAvance;
  }
}
