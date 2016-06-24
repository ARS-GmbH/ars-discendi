package de.ars.skilldb.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.Customer;
import de.ars.skilldb.domain.Project;
import de.ars.skilldb.interactor.CustomerInteractor;
import de.ars.skilldb.interactor.ProjectInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Controller, der die Benutzeranfragen zu Customern entgegenimmt, weiterreicht und
 * die Antwort für den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController extends AbstractController {


    private static final String BASE_URL = "/customers/";
    private static final String LIST_ALL_CUSTOMERS = BASE_URL + "list";
    private static final String CREATE_CUSTOMER = BASE_URL + "create";
    private static final String EDIT_CUSTOMER = BASE_URL + "edit";

    @Autowired
    private CustomerInteractor customerInteractor;

    @Autowired
    private ProjectInteractor projectInteractor;

    /**
     * Füllt die View für die Anzeige der exisitierenden Kunden.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(final ModelMap model) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Collection<Customer> allCustomers = customerInteractor.findAll(sort);

        model.addAttribute("allCustomers", allCustomers);

        return LIST_ALL_CUSTOMERS;
    }

    /**
     * Erstellt die Inhalte für das Formular zur Eingabe eines neuen Kunden.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        model.addAttribute("customer", new Customer());

        return CREATE_CUSTOMER;
    }

    /**
     * Erstellt einen Kunden anhand der validen Benutzereingaben und übergibt diesen dem CustomerInteractor.
     * @param customer Anzulegender Kunde
     * @param result Validiert die Benutzereingaben
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("customer") final Customer customer,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            markErrorFields(result.getFieldErrors(), model);
            return CREATE_CUSTOMER;
        }

        Customer savedCustomer;
        try {
            savedCustomer = customerInteractor.add(customer);
            String successMessage = String.format("%s wurde erfolgreich angelegt.", savedCustomer.getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("customer", customer);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            return CREATE_CUSTOMER;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten des zu bearbeitenden Kunden.
     * @param customerId ID des Kunden
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long customerId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        Customer customer = customerInteractor.findOne(customerId);

        if (customer == null) {
            addErrorFlashMessage("Es existiert kein Kunde mit der ID " + customerId, redirectAttributes);
            return redirectTo(BASE_URL);
        }

        Sort sort = new Sort(Sort.Direction.DESC, "end");
        Collection<Project> projects = projectInteractor.findByCustomer(customerId, sort);

        if (projects.isEmpty()) {
            model.addAttribute("noProjects", true);
        }
        else {
            model.addAttribute("allProjects", projects);
        }

        model.addAttribute("customer", customer);
        addHistory(customer.getCreated(), customer.getLastModified(), model);

        return EDIT_CUSTOMER;
    }

    /**
     * Nimmt den geänderten Kunden entgegen und übergibt ihn dem CustomerInteractor, wenn er valide ist.
     * @param customer Customer
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{customerId}", method = RequestMethod.POST)
    public String update(@Valid final Customer customer,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            markErrorFields(result.getFieldErrors(), model);
            model.addAttribute("customer", customer);
            return EDIT_CUSTOMER;
        }

        try {
            Customer savedCustomer = customerInteractor.update(customer);
            String successMessage = String.format("%s wurde erfolgreich geändert.", savedCustomer.getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);

            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("customer", customer);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            return EDIT_CUSTOMER;
        }
    }
}
