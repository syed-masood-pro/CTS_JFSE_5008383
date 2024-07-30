import java.util.HashMap;
import java.util.Map;

public class DependencyInjection {
    public static void main(String[] args) {
        // Create an instance of CustomerRepository
        CustomerRepository repository = new CustomerRepositoryImpl();

        // Inject the repository into CustomerService
        CustomerService service = new CustomerService(repository);

        // Test the service
        System.out.println("Fetching customer with ID 1:");
        Customer customer = service.getCustomer(1);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Customer not found.");
        }

        System.out.println("Fetching customer with ID 4:");
        customer = service.getCustomer(4);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }
}

// CustomerRepository Interface
interface CustomerRepository {
    Customer findCustomerById(int id);
}

// CustomerRepositoryImpl Class
class CustomerRepositoryImpl implements CustomerRepository {

    // Mock database of customers
    private static final Map<Integer, Customer> customerDatabase = new HashMap<>();

    static {
        customerDatabase.put(1, new Customer(1, "Syed"));
        customerDatabase.put(2, new Customer(2, "Masood"));
        customerDatabase.put(3, new Customer(3, "Albert"));
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerDatabase.get(id);
    }
}

// Customer Class
class Customer {
    private final int id;
    private final String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", name='" + name + "'}";
    }
}

// CustomerService Class
class CustomerService {
    private final CustomerRepository customerRepository;

    // Constructor injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(int id) {
        return customerRepository.findCustomerById(id);
    }
}
