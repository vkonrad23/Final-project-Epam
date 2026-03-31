import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HeroSectionComponent } from './components/hero-section/hero-section.component';
import { FiltersSidebarComponent } from './components/filters-sidebar/filters-sidebar.component';
import { CatalogGridComponent } from './components/catalog-grid/catalog-grid.component';
import { BOOKS } from './data/books.mock';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    NavbarComponent,
    HeroSectionComponent,
    FiltersSidebarComponent,
    CatalogGridComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  books = BOOKS;
  featuredBooks = BOOKS.slice(0, 3);
}
