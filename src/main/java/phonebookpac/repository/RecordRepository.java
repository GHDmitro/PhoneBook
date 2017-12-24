package phonebookpac.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import phonebookpac.model.Account;
import phonebookpac.model.Record;

import java.util.List;

/**
 * Created by macbookair on 06.03.17.
 */

@Repository
public interface RecordRepository extends CrudRepository<Record, Long>{
//JpaRepository<Record, Long>

//    List<Record> findByAccountByOrderByIdAsc(Account account);
//    @Query("select r from Record r where r.account=:account and r.name LIKE CONCAT(:name,'%')") //@Param("account") Account account ,
    List<Record> findByAccountAndNameStartingWithOrderByNameAsc(Account account, String name);
//    List<Record> findRecordsByAccountWithPartOfName(@Param("account") Account account , @Param("name") String name);
//    @Query("select r from Record r where r.account=:account and r.surname LIKE :surname% order by r.surname")
    List<Record> findByAccountAndSurnameStartingWithOrderBySurnameAsc(Account account, String surname);
//    @Query("select r from Record r where r.account=:account and r.mobileNumber LIKE :mobileNumber||'%' order by r.mobileNumber")
    List<Record> findByAccountAndMobileNumberStartingWithOrderByMobileNumberAsc(Account account, String mobileNumber);

    List<Record> findByAccountAndNameStartingWithAndSurnameStartingWithOrderByNameAsc(Account account,
                                                                                      String name,
                                                                                      String surname);

    List<Record> findByAccountAndNameStartingWithAndMobileNumberStartingWithOrderByNameAsc(Account account,
                                                                                           String name,
                                                                                           String mobileNumber);

    List<Record> findByAccountAndSurnameStartingWithAndMobileNumberStartingWithOrderBySurnameAsc(Account account,
                                                                                                 String surname,
                                                                                                 String mobileNumber);
    List<Record> findByAccountAndNameStartingWithAndSurnameStartingWithAndMobileNumberStartingWithOrderByNameAsc(Account account,
                                                                                                                 String name,
                                                                                                                 String surname,
                                                                                                                 String mobileNumber);
}
