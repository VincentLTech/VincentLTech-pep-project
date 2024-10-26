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
            validateMessage(message);
            Message addingMessage = messageDAO.insertMessage(message);
            return addingMessage;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }
    private void validateMessage(Message message) throws Exception{
        
        String username = message.getMessage_text().trim();

        if (username.isEmpty()) {
            throw new Exception("Username cannot be blank");
        }
    }
    public Message removeMessage(Message message) {
        try{
            Message removingMessage = messageDAO.deleteMessage(message);
            return removingMessage;
        } catch (Exception e) {
            System.out.print("Error");
        }
        return null;
    }
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
