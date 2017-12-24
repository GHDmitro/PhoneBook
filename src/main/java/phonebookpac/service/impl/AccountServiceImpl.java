package phonebookpac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import phonebookpac.model.Account;
import phonebookpac.model.Record;
import phonebookpac.repository.AccountRepository;
import phonebookpac.repository.RecordRepository;
import phonebookpac.service.AccountService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by macbookair on 06.03.17.
 */
@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Cacheable(
            value = "accounts",
            key = "#id")
    @Override
    public Account findOneAccount(Long id) {
        return accountRepository.findOne(id);
    }

    @Cacheable(
            value = "records",
            key = "#id")
    @Override
    public Record findOneRecord(Long id) {
        return recordRepository.findOne(id);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "accounts",
            key = "#result.id")
    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }


    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "records",
            key = "#result.id")
    @Override
    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }


    @Override
    public Account findByLogin(String login) {
        Account account = accountRepository.findAccountByLogin(login);
        if (account == null){
            System.out.println("account не существует в AccountServiceImpl");
        }
        return account;
    }


    @Override
    public List<Account> findAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    public List<Record> findAllRecords() {
        return (List<Record>) recordRepository.findAll();
    }


    @Override
    public Account findAccountByLogin(String login) {
        return accountRepository.findAccountByLogin(login);
    }

    @Override
    public Iterable<Account> saveAllAccounts(Collection<Account> accounts) {
        return accountRepository.save(accounts);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "accounts",
            key = "#result.id")
    @Override
    public Account createAccount(String name, String surname, String lastname, String login, String password, String confirmPassword) {

        Account testAccount = accountRepository.findAccountByLogin(login);

        if (testAccount == null){
            String passEncode = passwordEncoder.encode(password);
//            String confirmPasswordEncode = passwordEncoder.encode(confirmPassword);
            if (passwordEncoder.matches(confirmPassword, passEncode)){
            Account account = new Account(login, passEncode, passEncode, name, surname, lastname);

            return accountRepository.save(account);
            }
            System.out.printf("разные пароли");
            return null;
        }return null;
    }

    @Override
    public List<Record> getAllRecords(Long accountId) {
        Set<Record> set = accountRepository.findOne(accountId).getPhoneBook().stream().sorted((a1,a2)->{
            return a1.getId().compareTo(a2.getId());
        }).collect(Collectors.toSet());
        if (set != null){
            return new LinkedList<>(set);
        }else return null;
    }

    @Override
    public List<Record> getRecordsByName(Long accountId, String name) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.findByAccountAndNameStartingWithOrderByNameAsc(account, name);
    }

    @Override
    public List<Record> getRecordsBySurname(Long accountId, String surname) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.findByAccountAndSurnameStartingWithOrderBySurnameAsc(account, surname);
    }

    @Override
    public List<Record> getRecordsByMobileNumber(Long accountId, String mobileNumber) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.
                findByAccountAndMobileNumberStartingWithOrderByMobileNumberAsc(account, mobileNumber);
    }

    @Override
    public List<Record> getRecordsByNameAndSurname(Long accountId, String name, String surname) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.
                findByAccountAndNameStartingWithAndSurnameStartingWithOrderByNameAsc(account, name, surname);
    }

    @Override
    public List<Record> getRecordsByNameAndMobileNumber(Long accountId, String name, String mobileNumber) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.
                findByAccountAndNameStartingWithAndMobileNumberStartingWithOrderByNameAsc(account, name, mobileNumber);
    }

    @Override
    public List<Record> getRecordsBySurnameAndMobileNumber(Long accountId, String surname, String mobileNumber) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.
                findByAccountAndSurnameStartingWithAndMobileNumberStartingWithOrderBySurnameAsc(account, surname, mobileNumber);
    }

    @Override
    public List<Record> getRecordsByNameAndSurnameAndMobileNumber(Long accountId, String name,
                                                                            String surname, String mobileNumber) {
        Account account = accountRepository.findOne(accountId);
        return recordRepository.
                findByAccountAndNameStartingWithAndSurnameStartingWithAndMobileNumberStartingWithOrderByNameAsc(account,
                        name, surname, mobileNumber);
    }


}



















