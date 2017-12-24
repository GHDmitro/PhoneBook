package phonebookpac.service;

import phonebookpac.model.Account;
import phonebookpac.model.Record;

import java.util.Collection;
import java.util.List;

/**
 * Created by macbookair on 06.03.17.
 */
public interface AccountService {

    Account findOneAccount(Long id);

    Record findOneRecord(Long id);

    Account saveAccount(Account account);

    Record saveRecord(Record record);

    Account findByLogin(String login);

    List<Account> findAllAccounts();

    List<Record> findAllRecords();

    Account findAccountByLogin(String login);

    Iterable<Account> saveAllAccounts(Collection<Account> accounts);

    Account createAccount(String name, String surname, String lastname, String login, String password, String confirmPassword);

    List<Record> getAllRecords(Long accountId);

    List<Record> getRecordsByName(Long accountId, String name);

    List<Record> getRecordsBySurname(Long accountId, String surname);

    List<Record> getRecordsByMobileNumber(Long accountId, String mobileNumber);

    List<Record> getRecordsByNameAndSurname(Long accountId, String name, String surname);

    List<Record> getRecordsByNameAndMobileNumber(Long accountId, String name, String mobileNumber);

    List<Record> getRecordsBySurnameAndMobileNumber(Long accountId, String surname, String mobileNumber);

    List<Record> getRecordsByNameAndSurnameAndMobileNumber(Long accountId, String name, String surname, String mobileNumber);

}
