import { Directive, HostListener, ElementRef, Renderer2, Input, Self, Optional } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
    standalone: true,
    selector: '[appRutFormat]' // Nombre de la directiva
})
export class RutFormatDirective {

    constructor(
        private el: ElementRef,
        private renderer: Renderer2,
        @Self() @Optional() private control: NgControl // Accedemos al control del formulario
    ) {}

    @HostListener('input', ['$event'])
    onInput(event: Event): void {
        const input = this.el.nativeElement as HTMLInputElement;
        let value = input.value.replace(/\D/g, ''); // Eliminar todos los caracteres no numéricos (excepto la letra k)

        if (value.length > 7) {
            // Si tiene más de 7 dígitos, insertamos el guion antes del dígito verificador
            const rut = value.slice(0, -1); // RUT sin el último carácter
            const dv = value.slice(-1).toLowerCase(); // Dígito verificador (último carácter)
            value = `${rut}-${dv}`; // El valor visual será el RUT formateado
        }

        // Actualizamos el valor real del formulario (NgModel/FormControl)
        if (this.control) {
            this.control.control?.setValue(value); // Establece el valor real en el formulario
        }

        // Cambiar solo el valor visual del input
        this.renderer.setProperty(input, 'value', value);
    }
}
