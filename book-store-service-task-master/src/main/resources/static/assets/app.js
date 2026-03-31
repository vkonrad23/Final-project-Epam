(function() {
    'use strict';

    angular.module('bookStoreApp', [])
        .controller('MainController', ['$http', function($http) {
            var vm = this;
            vm.books = [];
            vm.error = '';
            vm.lang = 'en';
            vm.newBook = { genre: 'General', ageGroup: 'TEEN', language: 'ENGLISH', publicationDate: '2024-01-01' };

            vm.messages = {
                en: { title: 'Book Store Service', books: 'Books', addBook: 'Add Book' },
                uk: { title: 'Сервіс Книгарні', books: 'Книги', addBook: 'Додати книгу' }
            };

            vm.t = function(key) {
                return vm.messages[vm.lang][key] || key;
            };

            vm.toggleLanguage = function() {
                vm.lang = vm.lang === 'en' ? 'uk' : 'en';
            };

            vm.loadBooks = function() {
                $http.get('/api/books').then(function(response) {
                    vm.books = response.data;
                }).catch(function() {
                    vm.error = 'Unable to load books';
                });
            };

            vm.addBook = function(form) {
                if (form.$invalid) {
                    return;
                }

                $http.post('/api/books', vm.newBook).then(function(response) {
                    vm.books.push(response.data);
                    vm.newBook = { genre: 'General', ageGroup: 'TEEN', language: 'ENGLISH', publicationDate: '2024-01-01' };
                    form.$setPristine();
                    form.$setUntouched();
                }).catch(function(err) {
                    vm.error = (err.data && err.data.error) || 'Unable to add book';
                });
            };

            vm.loadBooks();
        }]);
})();
