package phonebookpac.assertsTests;

import org.junit.Test;
import phonebookpac.PhoneBookApplicationTests;
import phonebookpac.model.Account;
import phonebookpac.model.Record;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dmytro Tymoshenko on 16.11.17.
 */
public class ModelsTest extends PhoneBookApplicationTests {

    @Test
    public void testModels() {

        String expectedName = "name";
        String expectedSurname = "surName";
        String expectedLastName = "lastName";

        Account account = getAccount(expectedName, expectedSurname, expectedLastName);

        Record record = getRecord(account.getName(), account.getSurname(), account.getLastname());

        account.addRecord(record);

        assertTrue(account.getPhoneBook().size() > 0);

        assertTrue(account.getPhoneBook().contains(record));

    }


    private Account getAccount(String name, String surName, String lastName) {
        return new Account("login", "password" , "password", name, surName, lastName);

    }

    private Record getRecord(String name, String surname, String lastName) {
        return new Record(name, surname, lastName, UUID.randomUUID().toString());
    }



}
