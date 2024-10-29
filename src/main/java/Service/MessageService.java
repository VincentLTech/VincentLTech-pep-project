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
        try{
            boolean hasDeletedMessage = messageDAO.deleteMessageById(message);
            if (!hasDeletedMessage) {
                throw new NotFoundResponse("Message to delete not found");
            } 
        } catch (Exception e) {
            System.out.print("75 Error");
        }
    }

    public Message updateMessage(Message message) {
        Message retrievedMessage = this.getMessageById(message.getMessage_id());
        // Check if the message exists
        if (retrievedMessage==null) {
            System.out.print("84 Error Message Not Found");
        }
        // Update the message text with the new value
        retrievedMessage.setMessage_text(message.getMessage_text());
        // Validate the updated message
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            System.out.print("92 Error Message text cannot be null or empty");
        }
        if (message.getMessage_text().length() > 254) {
            System.out.print("96 Message text cannot exceed 254 characters");
        }
        try {
            // Update the message in the database
            messageDAO.updateMessageById(retrievedMessage);
            return retrievedMessage;
        } catch (Exception e) {
            System.out.print("100 Error");
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





// public Message updateMessage(Message message) {
    //     try{
    //         if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
    //             throw new Exception("Message text cannot be null or empty");
    //         }
    //         if (message.getMessage_text().length() > 254) {
    //             throw new Exception("Message text cannot exceed 254 characters");
    //         }
    //         Message updatingMessage = messageDAO.updateMessageById(message);
    //         return updatingMessage;
    //     } catch (Exception e) {
    //         System.out.print("Error");
    //     }
    //     return null;
    // }
    // public Message updateMessage(Message message) {
    //     Message retrievedMessage = this.getMessageById(message.getMessage_id());

    //     // Check if the message exists
    //     if (retrievedMessage ==null) {
    //         throw new Exception("Message not found");
    //     }
    //     if (retrievedMessage.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
    //         throw new Exception("Message text cannot be null or empty");
    //     }
    //     if (retrievedMessage.getMessage_text().length() > 254) {
    //         throw new Exception("Message text cannot exceed 254 characters");
    //     }
    //     try {
    //         messageDAO.update(retrievedMessage.get());
    //         return retrievedMessage.get();
    //     } catch (Exception e) {
    //         throw new Exception("error");
    //     }
    // }
