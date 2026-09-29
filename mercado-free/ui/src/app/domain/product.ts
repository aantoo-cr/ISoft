export class Product {


    proId : number;
    proName : string;
    proDescription : string;
    proPrice : number;
    proBrand : string;
    proImage : string;

    constructor(proId : number, 
                proName : string, 
                proDescription : string, 
                proPrice : number, 
                proBrand : string, 
                proImage : string) {
        this.proId = proId;
        this.proName = proName;
        this.proDescription = proDescription;
        this.proPrice = proPrice;
        this.proBrand = proBrand;
        this.proImage = proImage;
    }


}