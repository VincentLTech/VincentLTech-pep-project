package Service;
import Model.Account;
import Model.Message;
import io.javalin.http.NotFoundResponse;
import DAO.MessageDAO;

import java.util.List;
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    
    public Message addMessage(Message message) {
        try{
            String username = message.getMessage_text().trim();
            if (username.isEmpty()) {
                throw new Exception("Username cannot be blank");
            }
            Message addingMessage = messageDAO.insertMessage(message);
            return addingMessage;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }
    public List<Message> getAllMessages() {
        try{
            List<Message> messages = messageDAO.getAllMessages();
            return messages;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }








    public Message getMessageById(int id) {
        try {
            Message message = messageDAO.getById(id);
            if (message==null) {
                throw new Exception("Missing message");
            }
            return message;
        }  catch (Exception e) {
            System.out.print("57 Error");
        }
        return null;
    }






    public void removeMessage(Message message) {
        boolean hasDeletedMessage = messageDAO.deleteMessageById(message);
        if (!hasDeletedMessage) {
            throw new NotFoundResponse("Message to delete not found");
        } System.out.print("72 Error");
    }
    

    public void updateMessage(Message message, int id) {

        // Check if the message exists
        if (getMessageById(id)==null) {
            throw new RuntimeException("Message not found");
        }
        // Check to see if the message you want to replace with satify the conditions
        if (message.getMessage_text() == null ||message.getMessage_text().isEmpty()) {
            throw new RuntimeException("Message text cannot be empty");
        }
        if (message.getMessage_text().length() > 255) {
            throw new RuntimeException("Message text be more 255");
        }

        // Update the message in the database
        boolean hasUpdatedMessage = messageDAO.updateMessageById(message,id);
        if (!hasUpdatedMessage) {
            throw new NotFoundResponse("Message to update failed");
        } 
    }






    public List<Message> getMessagesByAccountId(int accountId) {
        try {
            List<Message> messages = messageDAO.getMessagesByAccountId(accountId);
            return messages;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }

}