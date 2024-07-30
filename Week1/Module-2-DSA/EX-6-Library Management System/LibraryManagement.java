import java.util.*;

public class LibraryManagement  {

    public static void main(String[] args) {
        LibraryLinear librarylin = new LibraryLinear();

        librarylin.addBook(new Book(1, "Harry Potter", "J.K. Rowling"));
        librarylin.addBook(new Book(2, "The Hobbit", "J.R.R. Tolkien"));
        librarylin.addBook(new Book(3, "1984", "George Orwell"));

        System.out.println("All books in the library:");
        librarylin.printBooks();

        // Linear search
        System.out.println("\nLinear Search Result:");
        Book foundBook = librarylin.linearSearchByTitle("The Hobbit");
        if (foundBook != null) {
            System.out.println("Book found: " + foundBook);
        } else {
            System.out.println("Book not found");
        }

        LibraryBinary librarybin = new LibraryBinary();

        // Adding the same books to LibraryBinary
        librarybin.addBook(new Book(1, "Harry Potter", "J.K. Rowling"));
        librarybin.addBook(new Book(2, "The Hobbit", "J.R.R. Tolkien"));
        librarybin.addBook(new Book(3, "1984", "George Orwell"));

        // Binary search
        librarybin.sortBooksByTitle();
        System.out.println("\nBinary Search Result:");
        foundBook = librarybin.binarySearchByTitle("1984");
        if (foundBook != null) {
            System.out.println("Book found: " + foundBook);
        } else {
            System.out.println("Book not found");
        }
    }

    // Nested Book class
    static class Book {
        private int bookId;
        private String title;
        private String author;

        public Book(int bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        public int getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "bookId=" + bookId +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    '}';
        }
    }

    // Nested LibraryLinear class
    static class LibraryLinear {
        private List<Book> books;

        public LibraryLinear() {
            this.books = new ArrayList<>();
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public Book linearSearchByTitle(String title) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    return book;
                }
            }
            return null;
        }

        public void printBooks() {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    // Nested LibraryBinary class
    static class LibraryBinary {
        private List<Book> books;

        public LibraryBinary() {
            this.books = new ArrayList<>();
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public void sortBooksByTitle() {
            Collections.sort(books, Comparator.comparing(Book::getTitle));
        }

        public Book binarySearchByTitle(String title) {
            int left = 0, right = books.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                Book midBook = books.get(mid);
                int comparison = midBook.getTitle().compareToIgnoreCase(title);

                if (comparison == 0) {
                    return midBook;
                } else if (comparison < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return null;
        }

        public void printBooks() {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}
