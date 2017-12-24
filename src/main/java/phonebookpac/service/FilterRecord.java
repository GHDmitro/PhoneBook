package phonebookpac.service;

import java.io.Serializable;

/**
 * Created by macbookair on 12.03.17.
 */
public class FilterRecord implements Serializable {

    private String name ;

    private String surname;
    private String mobileNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
