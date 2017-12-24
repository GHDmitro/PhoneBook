package phonebookpac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phonebookpac.model.Account;
import phonebookpac.model.Record;
import phonebookpac.service.AccountService;
import phonebookpac.service.ClientRecord;
import phonebookpac.service.FilterRecord;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by macbookair on 08.03.17.
 */

@RestController
public class ControllerRest extends BaseController{

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/recordAdd/{accountId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> recordAdd(@PathVariable Long accountId,  @RequestBody ClientRecord clientRecord){

        Account account = accountService.findOneAccount(accountId);

        if (account != null){

            if (clientRecord.getName() != null && clientRecord.getSurname() != null && clientRecord.getLastName() != null &&
                    clientRecord.getMobileNumber() != null){
                Record record = new Record(clientRecord.getName(), clientRecord.getSurname(), clientRecord.getLastName(),
                        clientRecord.getMobileNumber());
                if(!clientRecord.getAddress().equals("")){
                    record.setAddress(clientRecord.getAddress());
                }
                if (!clientRecord.getEmail().equals("")){
                    record.setEmail(clientRecord.getEmail());
                }
                if (clientRecord.getHomeNumber() != null){
                    record.setHomeNumber(clientRecord.getHomeNumber());
                }

                account.addRecord(record);

                accountService.saveAccount(account);

                return new ResponseEntity<Record>(record, HttpStatus.CREATED);
            }else {
                return new ResponseEntity<Record>(HttpStatus.BAD_REQUEST);
            }

            //проверить будет ли в аккаунт добалвена эта апись при следующем вызове листа

        }else return new ResponseEntity<Record>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/recordChange/{accountId}/{recordId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> recordChange(@PathVariable Long accountId,@PathVariable(name = "recordId") Long recordId ,
                                               @Valid @RequestBody ClientRecord clientRecord){


        Account account = accountService.findOneAccount(accountId);

        Record record = accountService.findOneRecord(recordId);

        if (account != null && record != null){


            if (clientRecord.getName() != null && !clientRecord.getName().equals("")){
                record.setName(clientRecord.getName());
            }
            if (clientRecord.getSurname() != null && !clientRecord.getSurname().equals("")){
                record.setSurname(clientRecord.getSurname());
            }
            if (clientRecord.getLastName() != null && !clientRecord.getLastName().equals("")){
                record.setLastname(clientRecord.getLastName());
            }
            if (clientRecord.getMobileNumber()!= null && !clientRecord.getMobileNumber().equals("")){
                record.setMobileNumber(clientRecord.getMobileNumber());
            }
            if (clientRecord.getHomeNumber() != null){
                record.setHomeNumber(clientRecord.getHomeNumber());
            }
            if (clientRecord.getEmail()!=null){
                record.setEmail(clientRecord.getEmail());
            }
            if (clientRecord.getAddress() != null){
                record.setAddress(clientRecord.getAddress());
            }

            record = accountService.saveRecord(record);

            return new ResponseEntity<Record>(record, HttpStatus.OK);

        }else return new ResponseEntity<Record>(HttpStatus.FOUND);

    }

    @RequestMapping(value = "/deleteRecord/{recordId}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteRecord(@PathVariable("recordId") Long recordId){

        Record record = accountService.findOneRecord(recordId);

        if (record != null){
            Long id = record.getAccount().getId();


            Account account = accountService.findOneAccount(id);

            account.deleteRecord(record);

            accountService.saveAccount(account);

            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/records/{accountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Record>> records(@PathVariable("accountId") Long accountId){

        Account account = accountService.findOneAccount(accountId);

        if(account != null){
            Set<Record> set = account.getPhoneBook();
            if (set != null){
                List<Record> list = set.stream().sorted((a1,a2)->{
                    return a1.getName().compareTo(a2.getName());
                }).collect(Collectors.toList());
                return new ResponseEntity<List<Record>>(list, HttpStatus.OK);
            }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<List<Record>>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/recordSorted/{accountId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Record>> recordssorted(@PathVariable(name = "accountId") Long accountId, @RequestBody FilterRecord filter){
        String name = filter.getName();
        String surname = filter.getSurname();
        String mobileNumber = filter.getMobileNumber();

        Account account = accountService.findOneAccount(accountId);

        if(account != null){

            if (name != null && surname != null && mobileNumber!= null){
                List<Record> records1 = accountService.getRecordsByNameAndSurnameAndMobileNumber(accountId,
                        name, surname, mobileNumber);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
            }else if (name!= null && surname!= null){
                List<Record> records1 = accountService.getRecordsByNameAndSurname(accountId, name, surname);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);

            }else if (name!= null && mobileNumber!= null){
                List<Record> records1 = accountService.getRecordsByNameAndMobileNumber(accountId, name, mobileNumber);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
            }else if (surname != null && mobileNumber!= null){
                List<Record> records1 = accountService.getRecordsBySurnameAndMobileNumber(accountId, surname, mobileNumber);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
            }else if (name != null){
                List<Record> records1 = accountService.getRecordsByName(accountId, name);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
            }else if (surname != null){
                List<Record> records1 = accountService.getRecordsBySurname(accountId, surname);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
            }else if (mobileNumber != null){
                List<Record> records1 = accountService.getRecordsByMobileNumber(accountId, mobileNumber);
                if (records1 != null) {
                    return new ResponseEntity<List<Record>>(records1, HttpStatus.OK);
                }else return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
            }else return new ResponseEntity<List<Record>>(HttpStatus.BAD_REQUEST);

        }else return new ResponseEntity<List<Record>>(HttpStatus.BAD_REQUEST);

    }



    @RequestMapping(value = "/recordOne/{recordId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> getRecordOne(@PathVariable Long recordId){

        Record record = accountService.findOneRecord(recordId);

        if (record != null){
            return new ResponseEntity<Record>(record, HttpStatus.OK);
        }else return new ResponseEntity<Record>(HttpStatus.BAD_REQUEST);
    }



}




































