package phonebookpac.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by macbookair on 06.03.17.
 */
@Entity
@Table(name = "account")
public class Account implements Serializable{


    @Id
    @GeneratedValue
    private Long id;

    private String login;

    private String password;

    private String confirmPassword;

    private String name;

    private String surname;

    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Record> phoneBook = new HashSet<>();

    public Account() {
    }

    public Account(String login, String password, String confirmPassword, String name, String surname, String lastname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.confirmPassword = confirmPassword;
    }

    public void addRecord(Record record){
        if (phoneBook == null){
            phoneBook = new HashSet<>();
        }
        if (!phoneBook.contains(record)){
            phoneBook.add(record);
            record.setAccount(this);
        }
    }

    public void deleteRecord(Record record){
        if (phoneBook == null){
            phoneBook = new HashSet<>();
        }
        if (phoneBook.contains(record)){
            phoneBook.remove(record);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @JsonManagedReference
    public Set<Record> getPhoneBook() {
        return phoneBook;
    }

    public void setPhoneBook(Set<Record> phoneBook) {
        this.phoneBook = phoneBook;
    }

    public void setId(int i) {

    }
}
