package Service;

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
        return messageDAO.insertMessage(message);
    }
    public Message deleteMessage(Message message) {
        return messageDAO.deleteMessage(message);
    }
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
