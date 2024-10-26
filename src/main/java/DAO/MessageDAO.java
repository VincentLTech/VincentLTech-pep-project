package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    public Message insertMessage(Message message){
        System.out.println(message.getPosted_by());
        System.out.println(message.getMessage_text());
        System.out.println(message.getTime_posted_epoch());
        String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//important Statement.RETURN_GENERATED_KEYS
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());
            // preparedStatement.executeQuery(); //this is excute dql
            preparedStatement.executeUpdate();//return 1 if something works and 0 if it fails
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();//we asked to get the generated keys
            // if(pkeyResultSet.next()){//iterate over this ta
            //     int generated_author_id = (int) pkeyResultSet.getLong(1);//lets get this value that this value column
            //     return new Message(generated_author_id, account.getUsername(), account.getPassword());
            // }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public List<Message> deleteMessage(Message message)throws SQLException{//why do we need SQLException      
        Connection connection = ConnectionUtil.getConnection();
        
        //Write SQL logic here
        String sql = "DELETE FROM message Where id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //write preparedStatement's setString method here.

        preparedStatement.setString(1,message.getUsername());
        preparedStatement.executeQuery();
        
        return null;
    }

    
}
