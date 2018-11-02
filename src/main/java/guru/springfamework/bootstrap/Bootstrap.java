package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//CommandLineRunner is a spring boot specific class that will make Boostrap class run before application starts.
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        initCategoryData();
        initCustomerData();
    }

    private void initCategoryData() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category Data loaded ... " + categoryRepository.count());
    }

    private void initCustomerData() {
        Customer flash = new Customer();
        flash.setFirstName("Flash");
        flash.setLastName("Gordon");

        Customer robbie = new Customer();
        robbie.setFirstName("Robbie");
        robbie.setLastName("Williams");

        Customer tom = new Customer();
        tom.setFirstName("Tom");
        tom.setLastName("Cruise");

        Customer arnold = new Customer();
        arnold.setFirstName("Arnold");
        arnold.setLastName("Schwarzenegger");

        Customer rocky = new Customer();
        rocky.setFirstName("Rocky");
        rocky.setLastName("Balboa");

        customerRepository.save(flash);
        customerRepository.save(robbie);
        customerRepository.save(tom);
        customerRepository.save(arnold);
        customerRepository.save(rocky);

        System.out.println("Customer Data loaded ... " + customerRepository.count());
    }
}
