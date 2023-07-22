package isaiah.jdbc;

import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** customer information
 *
 */
public class customerinfo {

    private int customerid;
    private String customername;
    private String address;
    private String postalcode;
    private String phonenumber;
    private int divisionsid;
    private Timestamp createdate;
    private String createdby;
    private Timestamp lastupdated;
    private String lastupdatedby;

    /** constructor
     *
     * @param customerid  constructor
     * @param customername constructor
     * @param address constructor
     * @param postalcode constructor
     * @param phonenumber constructor
     * @param createdate constructor
     * @param createdby constructor
     * @param lastupdated constructor
     * @param lastupdatedby constructor
     * @param divisionsid constructor
     */
    public customerinfo(int customerid, String customername, String address, String postalcode,
                        String phonenumber, Timestamp createdate, String createdby,
                        Timestamp lastupdated, String lastupdatedby, int divisionsid) {

        this.customerid = customerid;
        this.customername = customername;
        this.address = address;
        this.postalcode = postalcode;
        this.phonenumber = phonenumber;
        this.createdate = createdate;
        this.createdby = createdby;
        this.lastupdated = lastupdated;
        this.lastupdatedby = lastupdatedby;
        this.divisionsid = divisionsid;
    }

    /** getters
     *
     * @return getters
     */
    public String getCreatedby() {
        return this.createdby;
    }

    public Timestamp getCreatedate() {
        return this.createdate;
    }

    public Timestamp getLastupdated() {
        return this.lastupdated;
    }

    public String getLastupdatedby() {
        return this.lastupdatedby;
    }

    public int getId() {
        return this.customerid;
    }

    public String getName() {
        return this.customername;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalcode() {
        return this.postalcode;
    }

    public String  getPhonenumber() {
        return this.phonenumber;
    }

    public int getDivisionsid() {
        return this.divisionsid;
    }

    /** set customer table */
    private static ObservableList<customerinfo> allcustomerrecords = FXCollections.observableArrayList();

    /** set customer table
     *
     * @param newCustomer set customer table
     */
    public static void addcustomer(customerinfo newCustomer) {
        allcustomerrecords.add(newCustomer);
    }

    /** set customer table
     *
     * @param selectedcustomer set customer table
     * @return set customer table
     */
    public static boolean deletecustomer(customerinfo selectedcustomer) {
        return allcustomerrecords.remove(selectedcustomer);
    }

    /** set customer table */
    public static void deletecustomers() {
        allcustomerrecords.clear();
    }

    /** set customer table
     *
     * @return set customer table
     */
    public static ObservableList<customerinfo> getallcustomerrecords() {
        return allcustomerrecords;
    }
}
