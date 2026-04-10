/** This file contains TypeScript declarations and application logic for the UI layer. */
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Book } from '../../models/book.model';

@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {
  @Input({ required: true }) book!: Book;
  @Output() readonly addToCart = new EventEmitter<Book>();

  get stars(): string {
    const full = Math.round(this.book.rating);
    return '★'.repeat(full) + '☆'.repeat(5 - full);
  }

  onAddToCart(): void {
    this.addToCart.emit(this.book);
  }
}

