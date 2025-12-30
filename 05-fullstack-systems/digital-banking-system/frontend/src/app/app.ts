import { Component, OnInit } from '@angular/core'; // 1. Ajouter OnInit
import { RouterOutlet } from '@angular/router';
import { Navbar } from './navbar/navbar';
import { AuthService } from './services/auth-service'; // 2. Importer AuthService

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
// 3. Implémenter OnInit
export class App implements OnInit {

  // 4. Injecter le service
  constructor(private authService: AuthService) {}

  // 5. Au démarrage, on restaure la session !
  ngOnInit(): void {
    this.authService.loadToken();
  }
}
