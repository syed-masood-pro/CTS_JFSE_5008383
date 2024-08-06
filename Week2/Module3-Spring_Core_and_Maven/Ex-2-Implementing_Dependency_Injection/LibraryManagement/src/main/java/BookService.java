public class BookService {

    private BookRepository bookRepository;
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void test() {
        if (bookRepository != null) {
            System.out.println("BookService is working and BookRepository is injected!");
        } else {
            System.out.println("BookRepository is not injected!");
        }
    }
}
