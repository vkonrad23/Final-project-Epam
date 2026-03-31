import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Book } from '../../models/book.model';
import { FeaturedBookListComponent } from '../featured-book-list/featured-book-list.component';

@Component({
  selector: 'app-hero-section',
  standalone: true,
  imports: [CommonModule, FeaturedBookListComponent],
  templateUrl: './hero-section.component.html',
  styleUrl: './hero-section.component.scss'
})
export class HeroSectionComponent {
  @Input() featuredBooks: Book[] = [];
}
