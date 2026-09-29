import { HttpInterceptorFn } from '@angular/common/http';

export const loggerInterceptor: HttpInterceptorFn = (req, next) => {
    // Obtener el token desde el localStorage (o desde donde lo estés guardando)
    const token = localStorage.getItem('access_token');

    // Si hay token, se agrega al encabezado Authorization
    if (token) {
        const authReq = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        });
        console.log(`Request va para ${req.url} con el token`);
        return next(authReq);
    } else {
        console.log(`Request va para ${req.url} sin token`);
        return next(req);
    }
};
