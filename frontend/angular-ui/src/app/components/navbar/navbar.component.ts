/** This file contains TypeScript declarations and application logic for the UI layer. */
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  private readonly apiBaseUrl = 'http://localhost:8084/api/auth';

  isAuthPanelOpen = false;
  authMode: 'login' | 'signup' = 'login';
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';
  accountEmail = '';

  loginModel = {
    email: '',
    password: ''
  };

  signUpModel = {
    email: '',
    name: '',
    password: ''
  };

  constructor(private readonly http: HttpClient) {}

  get isAuthenticated(): boolean {
    return Boolean(localStorage.getItem('token'));
  }

  openAuth(mode: 'login' | 'signup'): void {
    this.authMode = mode;
    this.isAuthPanelOpen = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  closeAuth(): void {
    this.isAuthPanelOpen = false;
    this.authMode = 'login';
    this.errorMessage = '';
    this.successMessage = '';
  }

  switchAuthMode(mode: 'login' | 'signup'): void {
    this.authMode = mode;
    this.errorMessage = '';
    this.successMessage = '';
  }

  login(): void {
    if (!this.loginModel.email || !this.loginModel.password) {
      this.errorMessage = 'Please fill all fields.';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.http.post<{ token: string }>(`${this.apiBaseUrl}/login`, {
      email: this.loginModel.email,
      password: this.loginModel.password
    }).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.accountEmail = this.loginModel.email;
        this.successMessage = 'Welcome back.';
        this.isSubmitting = false;
        this.closeAuth();
      },
      error: (error) => {
        this.errorMessage = error?.error?.error || 'Login failed';
        this.isSubmitting = false;
      }
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    this.accountEmail = '';
    this.loginModel = { email: '', password: '' };
    this.signUpModel = { email: '', name: '', password: '' };
    this.successMessage = 'Signed out.';
    this.errorMessage = '';
  }

  register(): void {
    if (!this.signUpModel.email || !this.signUpModel.name || !this.signUpModel.password) {
      this.errorMessage = 'Please fill all fields.';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.http.post<{ token: string }>(`${this.apiBaseUrl}/register`, {
      email: this.signUpModel.email,
      name: this.signUpModel.name,
      password: this.signUpModel.password
    }).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.accountEmail = this.signUpModel.email;
        this.successMessage = 'Registration successful. You are now signed in.';
        this.isSubmitting = false;

        this.loginModel = {
          email: this.signUpModel.email,
          password: ''
        };
        this.signUpModel = {
          email: '',
          name: '',
          password: ''
        };

        this.closeAuth();
      },
      error: (error) => {
        this.errorMessage = error?.error?.error || 'Registration failed';
        this.isSubmitting = false;
      }
    });
  }
}

