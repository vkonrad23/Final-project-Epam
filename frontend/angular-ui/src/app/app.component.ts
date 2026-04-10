/** This file contains TypeScript declarations and application logic for the UI layer. */
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
  private readonly allBooks = [...BOOKS];
  searchTerm = '';
  selectedCategory = 'All';
  cartCount = 0;
  cartTotal = 0;

  get books() {
    return this.allBooks.filter((book) => {
      const term = this.searchTerm.trim().toLowerCase();
      const matchesTerm = !term ||
        book.title.toLowerCase().includes(term) ||
        book.author.toLowerCase().includes(term);
      const matchesCategory = this.selectedCategory === 'All' || book.category === this.selectedCategory;
      return matchesTerm && matchesCategory;
    });
  }

  get featuredBooks() {
    return this.books.slice(0, 3);
  }

  get categories() {
    return Array.from(new Set(this.allBooks.map((book) => book.category))).sort();
  }

  onSearchChange(value: string): void {
    this.searchTerm = value;
  }

  onCategoryChange(value: string): void {
    this.selectedCategory = value;
  }

  onAddBook(book: (typeof BOOKS)[number]): void {
    this.allBooks.unshift(book);
  }

  onAddToCart(book: (typeof BOOKS)[number]): void {
    this.cartCount += 1;
    this.cartTotal += book.price;
  }
}

