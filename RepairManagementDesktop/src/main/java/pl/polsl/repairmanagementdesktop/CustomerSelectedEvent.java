package pl.polsl.repairmanagementdesktop;

import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;

public class CustomerSelectedEvent {

    private CustomerTableRow customer;

    private Object source;

    public CustomerSelectedEvent(CustomerTableRow customer, Object source) {

        this.customer = customer;
        this.source = source;
    }


    public CustomerTableRow getCustomer() {
        return customer;
    }

    public Object getSource() {
        return source;
    }
}
