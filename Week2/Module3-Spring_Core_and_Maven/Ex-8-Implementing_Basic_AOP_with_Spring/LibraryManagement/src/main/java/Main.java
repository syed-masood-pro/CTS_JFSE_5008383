import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.BookService;

public class Main {
    public static void main(String[] args) {
        // Load Spring context from XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean and test
        BookService bookService = (BookService) context.getBean(BookService.class);
        bookService.test();  // This should trigger logging before and after method execution
    }
}
