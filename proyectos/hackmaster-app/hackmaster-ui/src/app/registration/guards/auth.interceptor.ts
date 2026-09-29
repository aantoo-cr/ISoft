import { HttpInterceptorFn } from '@angular/common/http';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  // URLs de las solicitudes que no necesitan el token
  const excludedUrls = ['http://localhost:8080/auth/**'];

  // Verificar si la URL está en la lista de exclusión
  const isExcluded = excludedUrls.some((url) => req.url.includes(url));

  // Si está excluida, pasa la solicitud tal como está
  if (isExcluded) {
    return next(req);
  }

  // Obtén el token del almacenamiento
  const token = localStorage.getItem('token');

  // Si el token existe, clona la solicitud y agrega el encabezado Authorization
  const authReq = token
    ? req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    })
    : req;

  // Pasa la solicitud (modificada o no) al siguiente manejador
  return next(authReq);
};
