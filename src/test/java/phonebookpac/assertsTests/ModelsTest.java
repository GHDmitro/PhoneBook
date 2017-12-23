package phonebookpac.assertsTests;

import groovy.util.logging.Slf4j;
import org.apache.log4j.Logger;
import org.junit.Test;
import phonebookpac.PhoneBookApplicationTests;
import phonebookpac.model.Account;
import phonebookpac.model.Record;

import java.util.LinkedHashSet;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Dmytro Tymoshenko and Serhiy Romanenko on 16.11.17.
 */

@Slf4j
public class ModelsTest extends PhoneBookApplicationTests {

    Logger logger = Logger.getLogger(ModelsTest.class);

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

        logger.info("testModels");
    }

    @Test
    public void testMockitoModel() {

        Account mockAccount = mock(Account.class);

        assertTrue(mockAccount.getPhoneBook().isEmpty());

        when(mockAccount.getPhoneBook()).thenReturn(new LinkedHashSet<Record>(asList(getRecord(), getRecord())));

        assertEquals(mockAccount.getPhoneBook().size(), 2);

        verify(mockAccount, times(2)).getPhoneBook();
        logger.info("testMockitoModel");
    }


    private Account getAccount(String name, String surName, String lastName) {
        return new Account("login", "password" , "password", name, surName, lastName);

    }

    private Record getRecord(String name, String surname, String lastName) {
        return new Record(name, surname, lastName, UUID.randomUUID().toString());
    }

    private Record getRecord() {

        return getRecord("name", "serName", "lastName");
    }

    private Account getAccount(){
        return getAccount("name", "surName", "lastName");
    }
}













