public class BookResource extends RepresentationModel<BookResource> {
    private Long id;
    private String title;
    private String author;
    private Double price;

    // Constructor to convert Book to BookResource
    public BookResource(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
    }
}
