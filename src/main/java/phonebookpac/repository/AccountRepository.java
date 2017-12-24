package phonebookpac.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import phonebookpac.model.Account;

/**
 * Created by macbookair on 06.03.17.
 */
//extends CrudRepository<Account, Long>
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    public Account findAccountByLogin(String login);

}
