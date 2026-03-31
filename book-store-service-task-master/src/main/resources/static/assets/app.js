(function() {
    'use strict';

    angular.module('bookStoreApp', [])
        .controller('MainController', ['$http', function($http) {
            var vm = this;
            vm.books = [];
            vm.selectedBook = null;
            vm.selectedCoverUrl = null;
            vm.selectedCoverTitle = '';
            vm.error = '';
            vm.lang = 'en';
            vm.isAuthenticated = false;
            vm.authMessage = '';
            vm.searchQuery = '';
            vm.selectedGenre = '';
            vm.genres = [];
            vm.currentYear = new Date().getFullYear();
            vm.credentials = {
                username: 'john.doe@email.com',
                password: 'pass123'
            };
            vm.newBook = { genre: 'General', ageGroup: 'TEEN', language: 'ENGLISH', publicationDate: '2024-01-01' };
            vm.cart = {
                items: [],
                total: 0
            };
            vm.coverThemeByGenre = {
                'Adventure': ['#2f4f68', '#7fa0bb', '🧭'],
                'Fantasy': ['#4a3a6d', '#9078c8', '✨'],
                'Mystery': ['#2f3b49', '#6d7d8d', '🕵'],
                'Romance': ['#6a3d4f', '#c17f98', '❤'],
                'Science Fiction': ['#223b60', '#5f8cc0', '🚀'],
                'Thriller': ['#3f2b34', '#8c5a6b', '⚡'],
                'Historical Fiction': ['#5a4332', '#b08d67', '🏛'],
                'Literary Fiction': ['#3d364b', '#8a7ea5', '✒'],
                'Drama': ['#344457', '#748aa1', '🎭'],
                'Contemporary': ['#35504c', '#7da39d', '📘']
            };

            // Title-first themes make covers visually match each known real-world book.
            vm.coverThemeByTitle = {
                'To Kill a Mockingbird': ['#5a4634', '#b79a78', '🐦'],
                '1984': ['#2a2d36', '#6b7280', '👁'],
                'Pride and Prejudice': ['#6c4a5b', '#d39cb6', '💌'],
                'The Hobbit': ['#3f4f32', '#9fb07a', '🗺'],
                'The Catcher in the Rye': ['#8b5e3b', '#d9b17b', '🌾'],
                'The Great Gatsby': ['#123447', '#4ca3c7', '🍸'],
                'Moby-Dick': ['#1f3e5a', '#76a7c9', '🐋'],
                'Jane Eyre': ['#3b3742', '#8f8899', '🏰'],
                'The Da Vinci Code': ['#4a2e2e', '#b06d5f', '🔺'],
                'The Name of the Rose': ['#4c3d3b', '#a68a80', '🌹'],
                'Dune': ['#6a4724', '#d1a36d', '🏜'],
                'The Book Thief': ['#3d3d4b', '#8c90a8', '📖'],
                'The Alchemist': ['#5b4e2f', '#c5a96b', '🧪'],
                'Gone Girl': ['#2f3b4c', '#7d8fa6', '🔍'],
                'Life of Pi': ['#3a4d3a', '#94b494', '🐯'],
                'The Kite Runner': ['#4c586b', '#a8b7cf', '🪁'],
                'The Girl with the Dragon Tattoo': ['#2f2f2f', '#7a7a7a', '🐉'],
                'Sapiens: A Brief History of Humankind': ['#3c3a35', '#91887a', '🧠']
            };

            vm.coverImageByTitle = {
                'To Kill a Mockingbird': '/assets/covers/to-kill-a-mockingbird.webp',
                '1984': '/assets/covers/1984.webp',
                'Pride and Prejudice': '/assets/covers/pride-and-prejudice.webp',
                'The Hobbit': '/assets/covers/the-hobbit.webp',
                'The Catcher in the Rye': '/assets/covers/the-catcher-in-the-rye.webp',
                'The Great Gatsby': '/assets/covers/the-great-gatsby.webp',
                'Moby-Dick': '/assets/covers/moby-dick.webp',
                'Jane Eyre': '/assets/covers/jane-eyre.webp',
                'The Da Vinci Code': '/assets/covers/the-da-vinci-code.webp',
                'The Name of the Rose': '/assets/covers/the-name-of-the-rose.webp',
                'Dune': '/assets/covers/dune.webp',
                'The Book Thief': '/assets/covers/the-book-thief.webp',
                'The Alchemist': '/assets/covers/the-alchemist.webp',
                'Gone Girl': '/assets/covers/gone-girl.webp',
                'Life of Pi': '/assets/covers/life-of-pi.webp',
                'The Kite Runner': '/assets/covers/the-kite-runner.webp',
                'The Girl with the Dragon Tattoo': '/assets/covers/the-girl-with-the-dragon-tattoo.webp',
                'Sapiens: A Brief History of Humankind': '/assets/covers/sapiens-a-brief-history-of-humankind.webp'
            };
            vm.googleCoverByTitle = {};
            vm.googleCoverRequestInFlight = {};

            vm.messages = {
                en: { title: 'Bookhaus', books: 'Catalog', addBook: 'Add Book' },
                uk: { title: 'Букгауз', books: 'Каталог', addBook: 'Додати книгу' }
            };

            vm.t = function(key) {
                return vm.messages[vm.lang][key] || key;
            };

            vm.toggleLanguage = function() {
                vm.lang = vm.lang === 'en' ? 'uk' : 'en';
            };

            vm.getAuthConfig = function() {
                if (!vm.credentials.username || !vm.credentials.password) {
                    return {};
                }
                return {
                    headers: {
                        Authorization: 'Basic ' + window.btoa(vm.credentials.username + ':' + vm.credentials.password)
                    }
                };
            };

            vm.escapeXml = function(text) {
                return (text || '')
                    .replace(/&/g, '&amp;')
                    .replace(/</g, '&lt;')
                    .replace(/>/g, '&gt;')
                    .replace(/"/g, '&quot;')
                    .replace(/'/g, '&apos;');
            };

            vm.pickCoverTheme = function(book) {
                if (book && book.name && vm.coverThemeByTitle[book.name]) {
                    return vm.coverThemeByTitle[book.name];
                }
                return vm.coverThemeByGenre[book.genre] || ['#2f4158', '#7a94b1', '📚'];
            };

            vm.coverTitleLines = function(name) {
                var words = (name || 'Book').split(' ');
                if (words.length === 1) {
                    return [words[0], ''];
                }
                return [words.slice(0, 2).join(' '), words.slice(2, 4).join(' ')];
            };

            vm.generateCoverDataUri = function(book) {
                var theme = vm.pickCoverTheme(book);
                var lines = vm.coverTitleLines(book.name);
                var icon = theme[2];
                var title1 = vm.escapeXml(lines[0]);
                var title2 = vm.escapeXml(lines[1]);
                var author = vm.escapeXml(book.author || 'Unknown');
                var genre = vm.escapeXml(book.genre || 'General');
                var svg =
                    '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 360 520">' +
                    '<defs><linearGradient id="g" x1="0" y1="0" x2="1" y2="1">' +
                    '<stop offset="0%" stop-color="' + theme[0] + '"/>' +
                    '<stop offset="100%" stop-color="' + theme[1] + '"/>' +
                    '</linearGradient></defs>' +
                    '<rect width="360" height="520" rx="24" fill="url(#g)"/>' +
                    '<rect x="24" y="24" width="312" height="472" rx="18" fill="rgba(255,255,255,0.08)"/>' +
                    '<text x="34" y="72" font-size="36" fill="white">' + icon + '</text>' +
                    '<text x="34" y="340" font-family="Georgia" font-size="34" font-weight="700" fill="#ffffff">' + title1 + '</text>' +
                    '<text x="34" y="378" font-family="Georgia" font-size="30" fill="#f7f2e8">' + title2 + '</text>' +
                    '<text x="34" y="430" font-family="Arial" font-size="16" fill="#ebf2ff">' + author + '</text>' +
                    '<text x="34" y="460" font-family="Arial" font-size="14" fill="#d7e1ef">' + genre + '</text>' +
                    '</svg>';
                return 'data:image/svg+xml;charset=UTF-8,' + encodeURIComponent(svg);
            };

            vm.getBookCoverStyle = function(book) {
                var cover = vm.getBookCoverUrl(book);
                return {
                    backgroundImage: "linear-gradient(140deg, rgba(36,51,75,0.14), rgba(111,138,122,0.14)), url('" + cover + "')"
                };
            };

            vm.getBookCoverUrl = function(book) {
                if (!book || !book.name) {
                    return vm.generateCoverDataUri({ name: 'Book', author: 'Unknown', genre: 'General' });
                }

                if (vm.coverImageByTitle[book.name]) {
                    return vm.coverImageByTitle[book.name];
                }

                if (vm.googleCoverByTitle[book.name]) {
                    return vm.googleCoverByTitle[book.name];
                }

                vm.fetchGoogleCover(book);
                return vm.generateCoverDataUri(book);
            };

            vm.fetchGoogleCover = function(book) {
                if (!book || !book.name || vm.coverImageByTitle[book.name] || vm.googleCoverByTitle[book.name] || vm.googleCoverRequestInFlight[book.name]) {
                    return;
                }

                vm.googleCoverRequestInFlight[book.name] = true;
                var query = encodeURIComponent('intitle:' + (book.name || '') + ' inauthor:' + (book.author || ''));
                var url = 'https://www.googleapis.com/books/v1/volumes?q=' + query + '&maxResults=1';

                $http.get(url).then(function(response) {
                    var item = response.data && response.data.items && response.data.items[0];
                    var links = item && item.volumeInfo && item.volumeInfo.imageLinks;
                    var cover = (links && (links.large || links.medium || links.thumbnail || links.smallThumbnail)) || '';
                    if (cover) {
                        vm.googleCoverByTitle[book.name] = cover.replace('http://', 'https://');
                    }
                }).catch(function() {
                    // Keep generated/local cover fallback when Google Books is unavailable.
                }).finally(function() {
                    vm.googleCoverRequestInFlight[book.name] = false;
                });
            };

            vm.prefetchGoogleCovers = function(books) {
                (books || []).forEach(function(book) {
                    vm.fetchGoogleCover(book);
                });
            };

            vm.openCoverViewer = function(book) {
                if (!book) {
                    return;
                }
                vm.selectedCoverUrl = vm.getBookCoverUrl(book);
                vm.selectedCoverTitle = book.name || 'Book cover';
            };

            vm.closeCoverViewer = function() {
                vm.selectedCoverUrl = null;
                vm.selectedCoverTitle = '';
            };

            vm.showDetails = function(book) {
                vm.selectedBook = book;
            };

            vm.closeDetails = function() {
                vm.selectedBook = null;
            };

            vm.recalculateCart = function() {
                vm.cart.total = vm.cart.items.reduce(function(sum, item) {
                    return sum + Number(item.price || 0);
                }, 0);
            };

            vm.addToCart = function(book) {
                vm.cart.items.push(book);
                vm.recalculateCart();
                vm.authMessage = 'Added "' + book.name + '" to cart.';
            };

            vm.removeFromCart = function(index) {
                vm.cart.items.splice(index, 1);
                vm.recalculateCart();
            };

            vm.checkout = function() {
                if (!vm.cart.items.length) {
                    return;
                }
                vm.authMessage = 'Purchase completed for ' + vm.cart.items.length + ' book(s).';
                vm.cart.items = [];
                vm.recalculateCart();
            };

            vm.login = function(form) {
                if (form && form.$invalid) {
                    vm.authMessage = 'Enter valid email and password.';
                    return;
                }

                vm.error = '';
                $http.get('/api/books', vm.getAuthConfig()).then(function(response) {
                    vm.books = response.data;
                    vm.refreshGenres();
                    vm.prefetchGoogleCovers(vm.books);
                    vm.isAuthenticated = true;
                    vm.authMessage = 'Signed in as ' + vm.credentials.username;
                }).catch(function() {
                    vm.isAuthenticated = false;
                    vm.authMessage = 'Login failed. Use valid credentials.';
                });
            };

            vm.logout = function() {
                vm.isAuthenticated = false;
                vm.authMessage = 'Signed out';
                vm.error = '';
                vm.books = [];
                vm.closeCoverViewer();
            };

            vm.refreshGenres = function() {
                var genreMap = {};
                vm.books.forEach(function(book) {
                    if (book.genre) {
                        genreMap[book.genre] = true;
                    }
                });
                vm.genres = Object.keys(genreMap).sort();
            };

            vm.getFilteredBooks = function() {
                var searchTerm = (vm.searchQuery || '').toLowerCase().trim();
                return vm.books.filter(function(book) {
                    var matchesSearch = !searchTerm ||
                        (book.name && book.name.toLowerCase().indexOf(searchTerm) !== -1) ||
                        (book.author && book.author.toLowerCase().indexOf(searchTerm) !== -1);
                    var matchesGenre = !vm.selectedGenre || book.genre === vm.selectedGenre;
                    return matchesSearch && matchesGenre;
                });
            };

            vm.getFeaturedBooks = function() {
                return vm.books.slice(0, 3);
            };

            vm.loadBooks = function() {
                $http.get('/api/books', vm.getAuthConfig()).then(function(response) {
                    vm.books = response.data;
                    vm.refreshGenres();
                    vm.prefetchGoogleCovers(vm.books);
                }).catch(function() {
                    vm.error = 'Unable to load books. Please sign in.';
                });
            };

            vm.addBook = function(form) {
                if (form.$invalid) {
                    return;
                }

                var payload = angular.copy(vm.newBook);
                payload.genre = payload.genre || 'General';
                payload.ageGroup = payload.ageGroup || 'TEEN';
                payload.language = payload.language || 'ENGLISH';
                payload.publicationDate = payload.publicationDate || '2024-01-01';
                payload.pages = Number(payload.pages);
                payload.price = Number(payload.price);

                $http.post('/api/books', payload, vm.getAuthConfig()).then(function(response) {
                    vm.books.push(response.data);
                    vm.fetchGoogleCover(response.data);
                    vm.refreshGenres();
                    vm.newBook = { genre: 'General', ageGroup: 'TEEN', language: 'ENGLISH', publicationDate: '2024-01-01' };
                    form.$setPristine();
                    form.$setUntouched();
                    vm.error = '';
                    vm.authMessage = 'Book added successfully.';
                }).catch(function(err) {
                    vm.error = (err.data && (err.data.message || err.data.error)) || ('Unable to add book (status ' + err.status + ')');
                });
            };

            vm.login();
        }]);
})();
