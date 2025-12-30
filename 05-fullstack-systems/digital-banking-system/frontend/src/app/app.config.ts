import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {
  provideHttpClient,
  withInterceptorsFromDi,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { AppHttpInterceptor } from './interceptor/app-http-interceptor'; // Vérifiez bien le chemin de votre fichier

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),

    // 1. On configure le client HTTP pour accepter les intercepteurs classiques (DI)
    provideHttpClient(withInterceptorsFromDi()),

    // 2. On injecte votre intercepteur ici
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptor,
      multi: true
    }
  ]
};
