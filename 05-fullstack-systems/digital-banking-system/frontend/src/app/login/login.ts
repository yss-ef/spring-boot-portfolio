import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../services/auth-service'; // Vérifie le chemin
import { Router } from '@angular/router';

/**
 * Component representing the login page.
 * Handles user authentication.
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  imports: [ReactiveFormsModule],
  styleUrls: ['./login.css']
})
export class Login implements OnInit {

  formLogin!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  /**
   * Initializes the login form with validation.
   */
  ngOnInit(): void {
    this.formLogin = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  /**
   * Handles the login form submission.
   * Calls the AuthService to authenticate the user and redirects upon success.
   */
  handleLogin() {
    let username = this.formLogin.value.username;
    let password = this.formLogin.value.password;

    this.authService.login(username, password).subscribe({
      next: (data) => {
        // Le backend répond : { "access-token": "eyJhbGciOi..." }
        let jwt = data['access-token'];

        // C'EST ICI QU'ON STOCKE LES INFOS
        this.authService.loadProfile(jwt);

        // Puis on change de page
        this.router.navigateByUrl('/home');
      },
      error: (err) => {
        console.log(err);
        alert("Mauvais identifiants !");
      }
    });
  }
}
