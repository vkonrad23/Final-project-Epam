/** This file contains TypeScript declarations and application logic for the UI layer. */
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Book } from '../../models/book.model';
import { BookCardComponent } from '../book-card/book-card.component';

@Component({
  selector: 'app-catalog-grid',
  standalone: true,
  imports: [CommonModule, BookCardComponent],
  templateUrl: './catalog-grid.component.html',
  styleUrl: './catalog-grid.component.scss'
})
export class CatalogGridComponent {
  @Input() books: Book[] = [];
  @Output() readonly addToCart = new EventEmitter<Book>();
}

