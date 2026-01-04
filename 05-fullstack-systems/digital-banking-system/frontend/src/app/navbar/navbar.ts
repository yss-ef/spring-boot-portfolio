import { Component } from '@angular/core';
import { AuthService } from '../services/auth-service'; // Vérifie le chemin
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {NgIf} from '@angular/common';

/**
 * Component representing the navigation bar.
 * Handles user logout and navigation.
 */
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.html',
  imports: [
    RouterLink,
    RouterLinkActive,
    NgIf
  ],
  styleUrls: ['./navbar.css']
})
export class Navbar {

  constructor(public authService: AuthService, private router: Router) { }

  /**
   * Logs out the current user and redirects to the login page.
   */
  handleLogout() {
    this.authService.logout();
    this.router.navigateByUrl("/login");
  }
}
