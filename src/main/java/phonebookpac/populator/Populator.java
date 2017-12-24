package phonebookpac.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phonebookpac.model.Account;
import phonebookpac.model.Record;
import phonebookpac.service.AccountService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by macbookair on 07.03.17.
 */
@Service
public class Populator {

    private final int ACCOUNT_AMOUNT = 3;
    private final int RECORD_AMOUNT = 5;


//    @Autowired
//    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void init(){
//        (List<Account>) accountRepository.findAll()
        List<Account> list = accountService.findAllAccounts();
        if (list == null || list.size()==0) {

            List<Account> accounts = new LinkedList<>();
            for (int i = 0; i < ACCOUNT_AMOUNT; i++) {
                String pass = passwordEncoder.encode("password" + i);
                Account account = new Account("login" + i + "@gmail.com", pass, pass, "name" + i, "surname" + i, "lastname" + i);
                for (int j = 0; j < RECORD_AMOUNT; j++) {
                    Record record = new Record("clientName" + j, "clientSurname" + j, "clientLastName" + j, "+380 (66) 443-23-4" + j);
                    account.addRecord(record);
                }
                accounts.add(account);
            }
            Iterable<Account> save = accountService.saveAllAccounts(accounts);
            save.forEach((e)->{
                System.out.println(e.getId()+"  ");
            });
        }else System.out.println("База уже забита и тестовые записи записывать не нужно");
    }
}
