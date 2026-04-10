/** This file contains TypeScript declarations and application logic for the UI layer. */
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Book } from '../../models/book.model';

@Component({
  selector: 'app-featured-book-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './featured-book-list.component.html',
  styleUrl: './featured-book-list.component.scss'
})
export class FeaturedBookListComponent {
  @Input() books: Book[] = [];
}

