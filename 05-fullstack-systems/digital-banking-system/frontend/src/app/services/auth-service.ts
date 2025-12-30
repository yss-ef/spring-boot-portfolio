import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  backendHost: string = "http://localhost:8085";

  // 1. Les variables pour stocker l'identité de l'utilisateur connecté
  public isAuthenticated: boolean = false;
  public roles: string[] = [];
  public username: any = undefined;
  public accessToken: any = undefined;

  constructor(private httpClient: HttpClient) { }

  public login(username: string, password: string): Observable<any> {
    // On envoie simplement les identifiants, on ne stocke rien encore ici
    return this.httpClient.post(this.backendHost + "/auth/login", {
      username: username,
      password: password
    });
  }

  // 2. La méthode CRUCIALE : Elle reçoit le token, le sauvegarde et l'analyse
  public loadProfile(token: string) {
    this.accessToken = token;

    // Sauvegarde physique dans le navigateur
    localStorage.setItem('access-token', token);

    // Décodage du JWT (Partie Payload en Base64)
    // Le token est sous forme : aaaaa.bbbbb.ccccc
    // On prend la partie "bbbbb" (index 1) qui contient les données
    let decodedJWT = JSON.parse(atob(token.split('.')[1]));

    // Extraction des infos
    this.username = decodedJWT.sub; // 'sub' contient le nom d'utilisateur standard
    this.roles = decodedJWT.scope.split(' '); // Spring met les rôles dans 'scope', séparés par espace

    this.isAuthenticated = true;
  }

  public logout() {
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.username = undefined;
    this.roles = [];
    localStorage.removeItem('access-token');
  }

  public loadToken() {
    let token = localStorage.getItem('access-token');

    if(token) {
      // C'EST LA LIGNE MAGIQUE : Elle restaure username, roles et isAuthenticated
      this.loadProfile(token);
    } else {
      this.logout();
    }
  }
}
