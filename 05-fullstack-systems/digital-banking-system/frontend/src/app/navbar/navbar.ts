import { Component } from '@angular/core';
import { AuthService } from '../services/auth-service'; // Vérifie le chemin
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar {

  constructor(public authService: AuthService, private router: Router) { }

  handleLogout() {
    this.authService.logout();
    this.router.navigateByUrl("/login");
  }
}
