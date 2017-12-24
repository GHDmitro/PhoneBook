package phonebookpac.service;

import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

/**
 * Created by macbookair on 08.03.17.
 */

public class ClientRecord implements Serializable{

//    @NotNull
//    private Long accountId;

    private String name;

    private String surname;

    private String lastName;

//    @Length(min = 12,max = 12)
    private String mobileNumber;

    private String homeNumber;

    private String address;

    @Email
    private String email;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Long getAccountId() {
//        return accountId;
//    }
//
//    public void setAccountId(Long accountId) {
//        this.accountId = accountId;
//    }

    //    public static void main(String[] args) {
//
//        ClientRecord clientRecord =new ClientRecord();
////        clientRecord.setName("d");
//        clientRecord.setSurname("d");
//        clientRecord.setLastName("d");
//        clientRecord.setMobileNumber("3865434545");
//
//        System.out.println(clientRecord.getMobileNumber());
//
//    }
}
