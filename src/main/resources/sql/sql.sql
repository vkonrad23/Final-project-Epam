-- This file provides SQL scripts for initializing or maintaining application data.
-- Inserts seed data into EMPLOYEES.
INSERT INTO EMPLOYEES (BIRTH_DATE, EMAIL, NAME, PASSWORD, PHONE)
VALUES ('1990-05-15', 'john.doe@email.com', 'John Doe', '{noop}pass123', '555-123-4567'),
       ('1985-09-20', 'jane.smith@email.com', 'Jane Smith', '{noop}abc456', '555-987-6543'),
       ('1978-03-08', 'bob.jones@email.com', 'Bob Jones', '{noop}qwerty789', '555-321-6789'),
       ('1982-11-25', 'alice.white@email.com', 'Alice White', '{noop}secret567', '555-876-5432'),
       ('1995-07-12', 'mike.wilson@email.com', 'Mike Wilson', '{noop}mypassword', '555-234-5678'),
       ('1989-01-30', 'sara.brown@email.com', 'Sara Brown', '{noop}letmein123', '555-876-5433'),
       ('1975-06-18', 'tom.jenkins@email.com', 'Tom Jenkins', '{noop}pass4321', '555-345-6789'),
       ('1987-12-04', 'lisa.taylor@email.com', 'Lisa Taylor', '{noop}securepwd', '555-789-0123'),
       ('1992-08-22', 'david.wright@email.com', 'David Wright', '{noop}access123', '555-456-7890'),
       ('1980-04-10', 'emily.harris@email.com', 'Emily Harris', '{noop}1234abcd', '555-098-7654');

-- Inserts seed data into CLIENTS.
INSERT INTO CLIENTS (BALANCE, EMAIL, NAME, PASSWORD)
VALUES (1000.00, 'client1@example.com', 'Medelyn Wright', '{noop}password123'),
       (1500.50, 'client2@example.com', 'Landon Phillips', '{noop}securepass'),
       (800.75, 'client3@example.com', 'Harmony Mason', '{noop}abc123'),
       (1200.25, 'client4@example.com', 'Archer Harper', '{noop}pass456'),
       (900.80, 'client5@example.com', 'Kira Jacobs', '{noop}letmein789'),
       (1100.60, 'client6@example.com', 'Maximus Kelly', '{noop}adminpass'),
       (1300.45, 'client7@example.com', 'Sierra Mitchell', '{noop}mypassword'),
       (950.30, 'client8@example.com', 'Quinton Saunders', '{noop}test123'),
       (1050.90, 'client9@example.com', 'Amina Clarke', '{noop}qwerty123'),
       (880.20, 'client10@example.com', 'Bryson Chavez', '{noop}pass789');

-- Inserts seed data into BOOKS.
INSERT INTO BOOKS (name, genre, age_group, price, publication_year, author, number_of_pages, characteristics,description, language)
VALUES ('To Kill a Mockingbird', 'Drama', 'TEEN', 14.99, '1960-07-11', 'Harper Lee', 281, 'Classic social novel','A landmark novel about justice and moral growth in the American South', 'ENGLISH'),
       ('1984', 'Science Fiction', 'ADULT', 13.50, '1949-06-08', 'George Orwell', 328, 'Dystopian classic', 'A chilling portrait of totalitarian control and surveillance', 'ENGLISH'),
       ('Pride and Prejudice', 'Romance', 'TEEN', 12.95, '1813-01-28', 'Jane Austen', 432, 'Regency romance','A witty exploration of love, class, and first impressions', 'ENGLISH'),
       ('The Hobbit', 'Fantasy', 'CHILD', 15.75, '1937-09-21', 'J. R. R. Tolkien', 310, 'Epic quest','A beloved adventure following Bilbo Baggins through Middle-earth', 'ENGLISH'),
       ('The Catcher in the Rye', 'Contemporary', 'TEEN', 11.99, '1951-07-16', 'J. D. Salinger', 277,'Coming-of-age voice', 'A defining novel of adolescent alienation and identity', 'ENGLISH'),
       ('The Great Gatsby', 'Literary Fiction', 'ADULT', 10.50, '1925-04-10', 'F. Scott Fitzgerald', 180, 'Jazz Age classic','A tragic tale of ambition, illusion, and the American Dream', 'ENGLISH'),
       ('Moby-Dick', 'Adventure', 'ADULT', 16.00, '1851-11-14', 'Herman Melville', 635,'Maritime epic', 'A profound voyage of obsession, fate, and the sea', 'ENGLISH'),
       ('Jane Eyre', 'Romance', 'ADULT', 13.99, '1847-10-16', 'Charlotte Bronte', 500, 'Gothic romance','A resilient heroine seeks independence, dignity, and love', 'ENGLISH'),
       ('The Da Vinci Code', 'Thriller', 'ADULT', 17.50, '2003-03-18', 'Dan Brown', 454, 'Conspiracy thriller','A fast-paced mystery across European art and history', 'ENGLISH'),
       ('The Name of the Rose', 'Mystery', 'ADULT', 18.25, '1980-01-01', 'Umberto Eco', 536, 'Historical mystery','A medieval investigation in a labyrinthine abbey library', 'ENGLISH'),
       ('Dune', 'Science Fiction', 'ADULT', 19.40, '1965-08-01', 'Frank Herbert', 412, 'Space opera', 'A sweeping saga of politics, ecology, and destiny on Arrakis', 'ENGLISH'),
       ('The Book Thief', 'Historical Fiction', 'TEEN', 14.80, '2005-03-14', 'Markus Zusak', 552, 'WWII perspective', 'A moving story of words and survival in Nazi Germany', 'ENGLISH'),
       ('The Alchemist', 'Adventure', 'OTHER', 12.10, '1988-01-01', 'Paulo Coelho', 208, 'Philosophical journey', 'A fable about pursuing one''s personal legend', 'ENGLISH'),
       ('Gone Girl', 'Thriller', 'ADULT', 16.70, '2012-06-05', 'Gillian Flynn', 432, 'Psychological suspense', 'A sharp and unsettling portrait of marriage and media', 'ENGLISH'),
       ('Life of Pi', 'Adventure', 'TEEN', 13.95, '2001-09-11', 'Yann Martel', 319, 'Survival narrative', 'A visionary tale of faith, storytelling, and survival at sea', 'ENGLISH'),
       ('The Kite Runner', 'Drama', 'ADULT', 15.30, '2003-05-29', 'Khaled Hosseini', 371, 'Emotional depth', 'A powerful story of friendship, guilt, and redemption', 'ENGLISH'),
       ('The Girl with the Dragon Tattoo', 'Mystery', 'ADULT', 17.60, '2005-08-01', 'Stieg Larsson', 465, 'Nordic noir', 'A dark investigation into family secrets and corruption', 'ENGLISH'),
       ('Sapiens: A Brief History of Humankind', 'Contemporary', 'OTHER', 20.90, '2011-01-01', 'Yuval Noah Harari', 443, 'Big-history nonfiction', 'A broad narrative of human history from evolution to modernity', 'ENGLISH');

