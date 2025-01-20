package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
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
            preparedStatement.executeUpdate();//return 1 if something works and 0 if it fails
            ResultSet rs = preparedStatement.getGeneratedKeys();//we asked to get the generated keys
            if(rs.next()){//iterate over this ta
                int id = (int) rs.getLong(1);//lets get this value that this value column
                return new Message(id, message.getPosted_by(),message.getMessage_text(), message.getTime_posted_epoch());
            }
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
                // messages.add(mapResultSetToMessage(rs));

                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }












    public Message getById(int id) {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement preparedstatement = connection.prepareStatement(sql)) {
            preparedstatement.setInt(1, id);
            ResultSet rs = preparedstatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                return message;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    public boolean deleteMessageById(Message message) {
        String sql = "DELETE FROM message WHERE message_id = ?";
        int rowsUpdated = 0;
        Connection conn = ConnectionUtil.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, message.getMessage_id());
            rowsUpdated = ps.executeUpdate();
        } catch (Exception e) {
        System.out.println(e.getMessage());
        }
        return rowsUpdated > 0;
    }
    
    public boolean updateMessageById(Message message, int id){
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";        
        int rowsUpdated = 0;
        Connection connection = ConnectionUtil.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2, id);
            rowsUpdated = preparedStatement.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return rowsUpdated > 0;

    }


    public List<Message> getMessagesByAccountId(int accountId) {
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        Connection conn = ConnectionUtil.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return mapResultSetToList(rs);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
    private List<Message> mapResultSetToList(ResultSet rs) throws SQLException {
        List<Message> messages = new ArrayList<>();
        while (rs.next()) {
            Message message2 = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
            messages.add(message2);
            
        }
        return messages;
    }
    
}
