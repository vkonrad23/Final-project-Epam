/** This file contains JavaScript logic used by the application runtime. */
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
            vm.signupData = {
                name: '',
                email: '',
                password: ''
            };
            vm.passwordChangeData = {
                currentPassword: '',
                newPassword: ''
            };
            vm.adminOrders = [];
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

            vm.genreTranslations = {
                'Adventure': { en: 'Adventure', uk: 'Пригоди' },
                'Contemporary': { en: 'Contemporary', uk: 'Сучасна проза' },
                'Drama': { en: 'Drama', uk: 'Драма' },
                'Fantasy': { en: 'Fantasy', uk: 'Фентезі' },
                'General': { en: 'General', uk: 'Загальний' },
                'Historical Fiction': { en: 'Historical Fiction', uk: 'Історична проза' },
                'Literary Fiction': { en: 'Literary Fiction', uk: 'Літературна проза' },
                'Mystery': { en: 'Mystery', uk: 'Детектив' },
                'Romance': { en: 'Romance', uk: 'Романтика' },
                'Science Fiction': { en: 'Science Fiction', uk: 'Наукова фантастика' },
                'Thriller': { en: 'Thriller', uk: 'Трилер' }
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
                en: {
                    title: 'Bookhouse',
                    books: 'Catalog',
                    catalog: 'Catalog',
                    trending: 'Trending',
                    addBook: 'Add Book',
                    login: 'Login',
                    logout: 'Logout',
                    signUp: 'Sign Up',
                    email: 'Email',
                    password: 'Password',
                    name: 'Name',
                    eyebrow: 'Curated collections for mindful readers',
                    heroTitle: 'Discover Your Next Favorite Book',
                    heroSubtitle: 'Explore handpicked fiction, timeless classics, and contemporary voices in a premium bookstore experience.',
                    searchPlaceholder: 'Search by title or author',
                    allGenres: 'All genres',
                    trendingWeek: 'Trending This Week',
                    general: 'General',
                    addBookSubtitle: 'Add a new title to the catalog with clean metadata.',
                    bookName: 'Book Name',
                    author: 'Author',
                    pages: 'Pages',
                    price: 'Price',
                    genre: 'Genre',
                    results: 'results',
                    cart: 'Cart',
                    purchasingAs: 'Purchasing as',
                    booksCount: 'book(s)',
                    buyNow: 'Buy Now',
                    na: 'N/A',
                    details: 'Details',
                    add: 'Add',
                    selectedBooks: 'Selected Books',
                    remove: 'Remove',
                    noDescription: 'No description available for this book yet.',
                    addToCart: 'Add to Cart',
                    close: 'Close',
                    footerTagline: 'Bookhouse, premium online bookstore experience.',
                    msgAddedToCart: 'Added "{0}" to cart.',
                    msgPurchaseCompleted: 'Purchase completed for {0} book(s) by {1}.',
                    msgInvalidLogin: 'Enter valid email and password.',
                    msgSignedInAs: 'Signed in as {0}',
                    msgLoginFailed: 'Login failed. Use valid credentials.',
                    msgSignedOut: 'Signed out',
                    msgInvalidSignup: 'Fill valid name, email, and password to sign up.',
                    msgAccountCreated: 'Account created. Logging in...',
                    msgSignupFailed: 'Sign up failed.',
                    msgLoadBooksFailed: 'Unable to load books. Please sign in.',
                    msgBookAdded: 'Book added successfully.',
                    msgAddBookFailed: 'Unable to add book (status {0})',
                    accountTools: 'Account Tools',
                    currentPassword: 'Current password',
                    newPassword: 'New password',
                    changePassword: 'Change Password',
                    msgPasswordChanged: 'Password changed successfully.',
                    msgPasswordChangeFailed: 'Unable to change password.',
                    adminOrders: 'Admin Orders',
                    orderBy: 'Ordered by',
                    orderHandledBy: 'Handled by',
                    orderTotal: 'Total',
                    orderDate: 'Order date',
                    noOrders: 'No orders yet.'
                },
                uk: {
                    title: 'Bookhouse',
                    books: 'Каталог',
                    catalog: 'Каталог',
                    trending: 'Тренди',
                    addBook: 'Додати книгу',
                    login: 'Увійти',
                    logout: 'Вийти',
                    signUp: 'Реєстрація',
                    email: 'Ел. пошта',
                    password: 'Пароль',
                    name: 'Ім\'я',
                    eyebrow: 'Добірки для уважних читачів',
                    heroTitle: 'Знайдіть свою наступну улюблену книгу',
                    heroSubtitle: 'Відкривайте добірки художньої літератури, класики та сучасних авторів у преміальному книжковому досвіді.',
                    searchPlaceholder: 'Пошук за назвою або автором',
                    allGenres: 'Усі жанри',
                    trendingWeek: 'Тренди тижня',
                    general: 'Загальний',
                    addBookSubtitle: 'Додайте нову книгу до каталогу з коректними метаданими.',
                    bookName: 'Назва книги',
                    author: 'Автор',
                    pages: 'Сторінки',
                    price: 'Ціна',
                    genre: 'Жанр',
                    results: 'результатів',
                    cart: 'Кошик',
                    purchasingAs: 'Покупка від',
                    booksCount: 'книг(и)',
                    buyNow: 'Купити',
                    na: 'Н/Д',
                    details: 'Деталі',
                    add: 'Додати',
                    selectedBooks: 'Обрані книги',
                    remove: 'Видалити',
                    noDescription: 'Опис цієї книги поки відсутній.',
                    addToCart: 'Додати в кошик',
                    close: 'Закрити',
                    footerTagline: 'Bookhouse, преміальний онлайн-досвід книжкового магазину.',
                    msgAddedToCart: '"{0}" додано в кошик.',
                    msgPurchaseCompleted: 'Покупку завершено для {0} книг(и) користувачем {1}.',
                    msgInvalidLogin: 'Введіть коректні email і пароль.',
                    msgSignedInAs: 'Вхід виконано як {0}',
                    msgLoginFailed: 'Помилка входу. Використайте коректні дані.',
                    msgSignedOut: 'Вихід виконано',
                    msgInvalidSignup: 'Заповніть коректно ім\'я, email і пароль для реєстрації.',
                    msgAccountCreated: 'Акаунт створено. Виконується вхід...',
                    msgSignupFailed: 'Не вдалося зареєструватися.',
                    msgLoadBooksFailed: 'Не вдалося завантажити книги. Будь ласка, увійдіть.',
                    msgBookAdded: 'Книгу успішно додано.',
                    msgAddBookFailed: 'Не вдалося додати книгу (статус {0})',
                    accountTools: 'Налаштування акаунту',
                    currentPassword: 'Поточний пароль',
                    newPassword: 'Новий пароль',
                    changePassword: 'Змінити пароль',
                    msgPasswordChanged: 'Пароль успішно змінено.',
                    msgPasswordChangeFailed: 'Не вдалося змінити пароль.',
                    adminOrders: 'Замовлення адміністратора',
                    orderBy: 'Замовив',
                    orderHandledBy: 'Обробляє',
                    orderTotal: 'Сума',
                    orderDate: 'Дата замовлення',
                    noOrders: 'Замовлень поки немає.'
                }
            };

            /**
             * t: executes this UI/business action for the page state.
             * @param key value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.t = function(key) {
                return vm.messages[vm.lang][key] || key;
            };

            vm.tf = function(key, value) {
                return vm.t(key).replace('{0}', value);
            };

            vm.normalizeEmail = function(value) {
                var email = (value || '').trim();
                if (!email) {
                    return '';
                }
                if (email.indexOf('@') === -1) {
                    return email + '@gmail.com';
                }
                return email;
            };

            /**
             * toggleLanguage: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.toggleLanguage = function() {
                vm.lang = vm.lang === 'en' ? 'uk' : 'en';
                vm.refreshGenres();
            };

            /**
             * getGenreLabel: returns localized label for a genre value.
             * @param genre value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.getGenreLabel = function(genre) {
                var sourceGenre = (genre || 'General').trim();
                if (!sourceGenre) {
                    sourceGenre = 'General';
                }

                var translation = vm.genreTranslations[sourceGenre];
                if (translation && translation[vm.lang]) {
                    return translation[vm.lang];
                }

                return sourceGenre;
            };

            /**
             * getAuthConfig: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
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

            /**
             * escapeXml: executes this UI/business action for the page state.
             * @param text value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.escapeXml = function(text) {
                return (text || '')
                    .replace(/&/g, '&amp;')
                    .replace(/</g, '&lt;')
                    .replace(/>/g, '&gt;')
                    .replace(/"/g, '&quot;')
                    .replace(/'/g, '&apos;');
            };

            /**
             * pickCoverTheme: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.pickCoverTheme = function(book) {
                if (book && book.name && vm.coverThemeByTitle[book.name]) {
                    return vm.coverThemeByTitle[book.name];
                }
                return vm.coverThemeByGenre[book.genre] || ['#2f4158', '#7a94b1', '📚'];
            };

            /**
             * coverTitleLines: executes this UI/business action for the page state.
             * @param name value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.coverTitleLines = function(name) {
                var words = (name || 'Book').split(' ');
                if (words.length === 1) {
                    return [words[0], ''];
                }
                return [words.slice(0, 2).join(' '), words.slice(2, 4).join(' ')];
            };

            /**
             * generateCoverDataUri: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.generateCoverDataUri = function(book) {
                var theme = vm.pickCoverTheme(book);
                var lines = vm.coverTitleLines(book.name);
                var icon = theme[2];
                var title1 = vm.escapeXml(lines[0]);
                var title2 = vm.escapeXml(lines[1]);
                var author = vm.escapeXml(book.author || 'Unknown');
                var genre = vm.escapeXml(vm.getGenreLabel(book.genre || 'General'));
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

            /**
             * getBookCoverStyle: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.getBookCoverStyle = function(book) {
                var cover = vm.getBookCoverUrl(book);
                return {
                    backgroundImage: "linear-gradient(140deg, rgba(36,51,75,0.14), rgba(111,138,122,0.14)), url('" + cover + "')"
                };
            };

            /**
             * getBookCoverUrl: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
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

            /**
             * fetchGoogleCover: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
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

            /**
             * prefetchGoogleCovers: executes this UI/business action for the page state.
             * @param books value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.prefetchGoogleCovers = function(books) {
                (books || []).forEach(function(book) {
                    vm.fetchGoogleCover(book);
                });
            };

            /**
             * openCoverViewer: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.openCoverViewer = function(book) {
                if (!book) {
                    return;
                }
                vm.selectedCoverUrl = vm.getBookCoverUrl(book);
                vm.selectedCoverTitle = book.name || 'Book cover';
            };

            /**
             * closeCoverViewer: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.closeCoverViewer = function() {
                vm.selectedCoverUrl = null;
                vm.selectedCoverTitle = '';
            };

            /**
             * showDetails: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.showDetails = function(book) {
                vm.selectedBook = book;
            };

            /**
             * closeDetails: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.closeDetails = function() {
                vm.selectedBook = null;
            };

            /**
             * recalculateCart: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.recalculateCart = function() {
                vm.cart.total = vm.cart.items.reduce(function(sum, item) {
                    return sum + Number(item.price || 0);
                }, 0);
            };

            /**
             * addToCart: executes this UI/business action for the page state.
             * @param book value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.addToCart = function(book) {
                vm.cart.items.push(book);
                vm.recalculateCart();
                vm.authMessage = vm.tf('msgAddedToCart', book.name);
            };

            /**
             * removeFromCart: executes this UI/business action for the page state.
             * @param index value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.removeFromCart = function(index) {
                vm.deleteBookFromCart(index);
            };

            /**
             * deleteBookFromCart: deletes a book from cart by index or by book object/id.
             * @param itemOrIndex value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.deleteBookFromCart = function(itemOrIndex) {
                var index = -1;

                if (typeof itemOrIndex === 'number') {
                    index = itemOrIndex;
                } else if (itemOrIndex && typeof itemOrIndex === 'object') {
                    if (itemOrIndex.id !== undefined && itemOrIndex.id !== null) {
                        index = vm.cart.items.findIndex(function(cartItem) {
                            return cartItem && cartItem.id === itemOrIndex.id;
                        });
                    }

                    if (index === -1) {
                        index = vm.cart.items.indexOf(itemOrIndex);
                    }
                }

                if (index < 0 || index >= vm.cart.items.length) {
                    return;
                }

                vm.cart.items.splice(index, 1);
                vm.recalculateCart();
            };

            /**
             * removeLastFromCart: removes the latest added book from the cart bar action.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.removeLastFromCart = function() {
                if (!vm.cart.items.length) {
                    return;
                }

                vm.deleteBookFromCart(vm.cart.items.length - 1);
            };

            /**
             * checkout: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.checkout = function() {
                if (!vm.cart.items.length) {
                    return;
                }

                var quantityByBook = {};
                vm.cart.items.forEach(function(item) {
                    var name = item && item.name;
                    if (!name) {
                        return;
                    }
                    quantityByBook[name] = (quantityByBook[name] || 0) + 1;
                });

                var bookItems = Object.keys(quantityByBook).map(function(bookName) {
                    return {
                        bookName: bookName,
                        quantity: quantityByBook[bookName]
                    };
                });

                if (!bookItems.length) {
                    return;
                }

                var orderPayload = {
                    clientEmail: vm.credentials.username,
                    employeeEmail: 'john.doe@email.com',
                    orderDate: new Date().toISOString().slice(0, 19),
                    price: vm.cart.total,
                    bookItems: bookItems
                };

                $http.post('/api/orders', orderPayload, vm.getAuthConfig()).then(function() {
                    var buyerEmail = vm.credentials.username || 'unknown';
                    vm.authMessage = vm.t('msgPurchaseCompleted')
                        .replace('{0}', vm.cart.items.length)
                        .replace('{1}', buyerEmail);
                    vm.cart.items = [];
                    vm.recalculateCart();
                    vm.loadAdminOrders();
                }).catch(function(err) {
                    vm.authMessage = (err.data && (err.data.message || err.data.error)) || vm.t('msgLoginFailed');
                });
            };

            /**
             * login: executes this UI/business action for the page state.
             * @param form value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.login = function(form) {
                if (form && form.$invalid) {
                    vm.authMessage = vm.t('msgInvalidLogin');
                    return;
                }

                vm.credentials.username = vm.normalizeEmail(vm.credentials.username);

                vm.error = '';
                $http.get('/api/books', vm.getAuthConfig()).then(function(response) {
                    vm.books = response.data;
                    vm.refreshGenres();
                    vm.prefetchGoogleCovers(vm.books);
                    vm.isAuthenticated = true;
                    vm.authMessage = vm.tf('msgSignedInAs', vm.credentials.username);
                    vm.loadAdminOrders();
                }).catch(function() {
                    vm.isAuthenticated = false;
                    vm.authMessage = vm.t('msgLoginFailed');
                });
            };

            /**
             * logout: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.logout = function() {
                vm.isAuthenticated = false;
                vm.authMessage = vm.t('msgSignedOut');
                vm.error = '';
                vm.books = [];
                vm.adminOrders = [];
                vm.passwordChangeData = { currentPassword: '', newPassword: '' };
                vm.closeCoverViewer();
            };

            vm.isAdmin = function() {
                return (vm.credentials.username || '').toLowerCase() === 'admin@bookstore.local';
            };

            vm.loadAdminOrders = function() {
                if (!vm.isAdmin()) {
                    vm.adminOrders = [];
                    return;
                }

                $http.get('/api/orders/admin/all', vm.getAuthConfig()).then(function(response) {
                    vm.adminOrders = response.data || [];
                }).catch(function() {
                    vm.adminOrders = [];
                });
            };

            vm.changePassword = function(form) {
                if (form && form.$invalid) {
                    return;
                }

                $http.post('/api/auth/change-password', vm.passwordChangeData, vm.getAuthConfig()).then(function() {
                    vm.authMessage = vm.t('msgPasswordChanged');
                    vm.credentials.password = vm.passwordChangeData.newPassword;
                    vm.passwordChangeData = { currentPassword: '', newPassword: '' };
                    if (form) {
                        form.$setPristine();
                        form.$setUntouched();
                    }
                }).catch(function(err) {
                    vm.authMessage = (err.data && (err.data.message || err.data.error)) || vm.t('msgPasswordChangeFailed');
                });
            };

            /**
             * signup: registers a new customer account and signs in with the new credentials.
             * @param form value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.signup = function(form) {
                if (form && form.$invalid) {
                    vm.authMessage = vm.t('msgInvalidSignup');
                    return;
                }

                vm.signupData.email = vm.normalizeEmail(vm.signupData.email);

                vm.error = '';
                $http.post('/api/auth/register', vm.signupData).then(function() {
                    vm.credentials.username = vm.signupData.email;
                    vm.credentials.password = vm.signupData.password;
                    vm.signupData = { name: '', email: '', password: '' };
                    vm.isAuthenticated = true;
                    if (form) {
                        form.$setPristine();
                        form.$setUntouched();
                    }
                    vm.authMessage = vm.t('msgAccountCreated');
                    vm.loadBooks();
                    vm.loadAdminOrders();
                }).catch(function(err) {
                    vm.authMessage = (err.data && (err.data.message || err.data.error)) || vm.t('msgSignupFailed');
                });
            };

            /**
             * refreshGenres: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.refreshGenres = function() {
                var genreMap = {};
                vm.books.forEach(function(book) {
                    if (book.genre) {
                        genreMap[book.genre] = true;
                    }
                });
                vm.genres = Object.keys(genreMap).sort(function(a, b) {
                    return vm.getGenreLabel(a).localeCompare(vm.getGenreLabel(b), vm.lang);
                });
            };

            /**
             * getFilteredBooks: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
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

            /**
             * getFeaturedBooks: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.getFeaturedBooks = function() {
                return vm.books.slice(0, 3);
            };

            /**
             * loadBooks: executes this UI/business action for the page state.
             * @returns computed value or updates application state depending on implementation.
             */
            vm.loadBooks = function() {
                $http.get('/api/books', vm.getAuthConfig()).then(function(response) {
                    vm.books = response.data;
                    vm.refreshGenres();
                    vm.prefetchGoogleCovers(vm.books);
                }).catch(function() {
                    vm.error = vm.t('msgLoadBooksFailed');
                });
            };

            /**
             * addBook: executes this UI/business action for the page state.
             * @param form value used by this function.
             * @returns computed value or updates application state depending on implementation.
             */
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
                    vm.authMessage = vm.t('msgBookAdded');
                }).catch(function(err) {
                    vm.error = (err.data && (err.data.message || err.data.error)) || vm.tf('msgAddBookFailed', err.status);
                });
            };

            // Do not auto-login on page load; show account tools only after explicit login/signup.
        }]);
})();

