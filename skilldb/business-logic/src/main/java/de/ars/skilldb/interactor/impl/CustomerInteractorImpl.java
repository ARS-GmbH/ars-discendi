/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Customer;
import de.ars.skilldb.interactor.CustomerInteractor;
import de.ars.skilldb.repository.CustomerRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Kunden.
 */
public class CustomerInteractorImpl extends AbstractInteractor<Customer> implements CustomerInteractor {


    private final CustomerRepository customerRepository;

    /**
     * Erzeugt ein neues Objekt der Klasse CustomerInteractorImpl.
     *
     * @param customerRepository
     *            Repository für den Datenbankzugriff
     */
    @Autowired
    public CustomerInteractorImpl(final CustomerRepository customerRepository) {
        super(Customer.class, customerRepository);
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer add(final Customer customer) {
        checkCustomer(customer);

        Customer sameName = customerRepository.findByNameCaseInsensitive(customer.getName());
        checkExisting(sameName);

        customer.setCreated(new Date());
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer update(final Customer customer) {
        checkCustomer(customer);

        Customer samerName = customerRepository.findByNameCaseInsensitiveWithoutId(customer.getId(), customer.getName());
        checkExisting(samerName);

        customer.setLastModified(new Date());
        return customerRepository.save(customer);
    }

    private void checkCustomer(final Customer customer) {
            Ensure.that(customer).isNotNull("Customer must not be null.");

            Map<String, String> errorMap = new HashMap<String, String>();

            if (customer.getName().trim().isEmpty()) {
                errorMap.put("name", "Der Name darf nicht leer sein.");
            }
            if (!errorMap.isEmpty()) {
                throw new IllegalUserArgumentsException(errorMap);
            }
    }

    private void checkExisting(final Customer sameName) {
        Map<String, String> errorMap = new HashMap<String, String>();

        if (sameName != null) {
            errorMap.put("name", String.format("Es existiert bereits ein Kunde mit dem Namen %s.", sameName.getName()));
        }

        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }
    }
}
