import { Directive, HostListener } from '@angular/core';
import { ElementRef } from '@angular/core';

@Directive({
    standalone: true,
    selector: '[appNumberFormat]' // El nombre de la directiva
})
export class NumberFormatDirective {

    constructor(private el: ElementRef) {}

    // Metodo que formatea el número al agregar puntos mientras escribe
    @HostListener('input', ['$event'])
    onInput(event: Event): void {
        const input = event.target as HTMLInputElement;
        let value = input.value.replace(/\D/g, '');  // Eliminar caracteres no numéricos
        if (value.length > 3 && value.length <= 6) {
            value = value.replace(/(\d{3})(\d{1,3})$/, '$1.$2');  // Agregar un punto después de los primeros 3 dígitos
        } else if (value.length > 6 && value.length <= 9) {
            value = value.replace(/(\d{3})(\d{3})(\d{1,3})$/, '$1.$2.$3');  // Agregar un punto después de los 6 primeros dígitos
        } else if (value.length > 9) {
            value = value.slice(0, 9);  // Limitar a 9 dígitos
        }
        input.value = value;  // Asignar el valor formateado al campo de entrada
    }
}
