package Service;

import Model.Account;
import Model.Message;
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
            System.out.print("Error");
        }
        return null;
    }


    public Message removeMessage(int id) {
        try{
            Message message = messageDAO.deleteMessageById(id);
            if (message==null) {
                throw new Exception("Missing message");
            }
            return message;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }
    public Message updateMessage(Message message) {
        try{
            String text = message.getMessage_text().trim();
            if (text.isEmpty()) {
                throw new Exception("Message is missing");
            }
            Message updatingMessage = messageDAO.insertMessage(message);
            return updatingMessage;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
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
