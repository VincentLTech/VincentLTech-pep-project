package DAO;
import java.sql.Statement;//important
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;
public class AccountDAO {
    public Account registerAccount(Account account){  

        System.out.println(account.account_id);
        System.out.println(account.password);
        String sql = "INSERT INTO account (username, password) VALUES (  ?,?)";
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//important Statement.RETURN_GENERATED_KEYS
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            // preparedStatement.executeQuery(); //this is excute dql
            preparedStatement.executeUpdate();//return 1 if something works and 0 if it fails
            ResultSet rs = preparedStatement.getGeneratedKeys();//we asked to get the generated keys
            if(rs.next()){//iterate over this ta
                int generated_author_id = (int) rs.getLong(1);//lets get this value that this value column
                return new Account(generated_author_id, account.getUsername(), account.getPassword());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        // PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return null;
    }
    
    public Account validateLogin(Account account) {
        String sql = "SELECT * FROM account WHERE username = ? AND password=?";
        // String sql2 ="SELECT password FROM account WHERE username = ?";
        Connection connection = ConnectionUtil.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){//this iterates the record to even check to see if anything is returned
                return new Account(rs.getInt(1), account.getUsername(), account.getPassword());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }






    public List<Account> getAllAccounts()throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        
        //Write SQL logic here
        String sql = "SELECT * FROM account";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.executeQuery();
            
        
        return accounts;
    }

    
}
