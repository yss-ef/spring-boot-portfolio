import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth-service';

/**
 * HTTP Interceptor to add the Authorization header with the JWT token to outgoing requests.
 * It intercepts all requests except the login request.
 */
@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  /**
   * Intercepts an outgoing HTTP request and adds the Authorization header if a token exists.
   * @param request The outgoing HTTP request.
   * @param next The next interceptor in the chain.
   * @returns An Observable of the HTTP event.
   */
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    // 1. On exclut la requête de login
    if (!request.url.includes("/auth/login")) {

      // 2. CORRECTION : On lit directement le token brut depuis le stockage
      // C'est plus sûr que d'appeler une méthode qui pourrait ne rien retourner
      let token = localStorage.getItem('access-token');

      // 3. Si le token existe, on clone et on ajoute le Header
      if (token) {
        let newRequest = request.clone({
          headers: request.headers.set('Authorization', 'Bearer ' + token)
        });
        return next.handle(newRequest);
      }
    }

    return next.handle(request);
  }
}
