package Service;
import Model.Account;
import DAO.AccountDAO;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    public Account createAccount(Account account) {
        try{
            validateAccount(account);
            Account createdAccount = accountDAO.registerAccount(account);
            return createdAccount;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }
    private void validateAccount(Account account) throws Exception{
        
            String username = account.getUsername().trim();
            String password = account.getPassword().trim();

            if (username.isEmpty()) {
                throw new Exception("Username cannot be blank");
            }
            if (password.isEmpty()) {
                throw new Exception("Password cannot be empty");
            }
            if (password.length() < 4) {
                throw new Exception("Password must be at least 4 characters long");
            }
    }
    public Account checkIfAccountExist(Account account) {
        try{
            Account checkedAccount = accountDAO.validateLogin(account);
            return checkedAccount;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }
}