/** This file contains TypeScript declarations and application logic for the UI layer. */
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventEmitter, Output } from '@angular/core';
import { Book } from '../../models/book.model';

@Component({
  selector: 'app-filters-sidebar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './filters-sidebar.component.html',
  styleUrl: './filters-sidebar.component.scss'
})
export class FiltersSidebarComponent {
  @Output() readonly addBook = new EventEmitter<Book>();

  isSubmitted = false;
  formModel = {
    title: '',
    author: '',
    category: '',
    price: null as number | null,
    coverImage: ''
  };

  saveBook(): void {
    this.isSubmitted = true;

    if (!this.formModel.title || !this.formModel.author || !this.formModel.category || !this.formModel.price) {
      return;
    }

    const newBook: Book = {
      id: Date.now(),
      title: this.formModel.title,
      author: this.formModel.author,
      category: this.formModel.category,
      rating: 4,
      price: this.formModel.price,
      coverStart: '#344a5f',
      coverEnd: '#738da8',
      coverImage: this.formModel.coverImage || 'https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=640&q=80'
    };

    this.addBook.emit(newBook);
    this.isSubmitted = false;
    this.formModel = {
      title: '',
      author: '',
      category: '',
      price: null,
      coverImage: ''
    };
  }
}

