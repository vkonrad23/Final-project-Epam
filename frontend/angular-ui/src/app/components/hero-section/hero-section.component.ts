/** This file contains TypeScript declarations and application logic for the UI layer. */
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Book } from '../../models/book.model';
import { FeaturedBookListComponent } from '../featured-book-list/featured-book-list.component';

@Component({
  selector: 'app-hero-section',
  standalone: true,
  imports: [CommonModule, FormsModule, FeaturedBookListComponent],
  templateUrl: './hero-section.component.html',
  styleUrl: './hero-section.component.scss'
})
export class HeroSectionComponent {
  @Input() featuredBooks: Book[] = [];
  @Input() categories: string[] = [];
  @Output() readonly searchChange = new EventEmitter<string>();
  @Output() readonly categoryChange = new EventEmitter<string>();

  searchText = '';
  selectedCategory = 'All';

  onSearchChange(): void {
    this.searchChange.emit(this.searchText);
  }

  onCategoryChange(): void {
    this.categoryChange.emit(this.selectedCategory);
  }
}

