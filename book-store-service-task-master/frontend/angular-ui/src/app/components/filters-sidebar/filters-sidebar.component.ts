import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-filters-sidebar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './filters-sidebar.component.html',
  styleUrl: './filters-sidebar.component.scss'
})
export class FiltersSidebarComponent {
  genre = 'All';
  language = 'All';
  minRating = 0;
  minPrice: number | null = null;
  maxPrice = 35;
  sortBy = 'Popularity';
}
