package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Optional;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        // Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);

        // return app;
        Javalin app = Javalin.create();
        app.post("/register", this::postAccount);
        app.post("/login", this::loginAccount);

        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);

        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageId);
        app.patch("/messages/{message_id}", this::updateMessageByMessageId);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountId);
        return app;
    }
    
    private void postAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.createAccount(account);
        if(registeredAccount==null){
            ctx.status(400);
        }
        else{
            ctx.json(mapper.writeValueAsString(registeredAccount));
        }
        
    }
    private void loginAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account checkedAccount = accountService.checkIfAccountExist(account);
        if(checkedAccount==null){
            ctx.status(401);
        }
        else{
            ctx.json(mapper.writeValueAsString(checkedAccount));
        }
    }
    private void createMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = messageService.addMessage(message);
        if(createMessage==null){
            ctx.status(400);
        } else{
            ctx.json(mapper.writeValueAsString(createMessage));
        }
    }
    private void getAllMessages(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }









    private void getMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message getMessage = messageService.getMessageById(id);
        if(getMessage==null){
            ctx.status(200);
        } else{
            ctx.json(mapper.writeValueAsString(getMessage));
        }
    }
    
    private void deleteMessageByMessageId(Context ctx) throws JsonProcessingException {
        
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if (message!=null) {// The message exists, so delete it
            messageService.removeMessage(message);
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }
    private void updateMessageByMessageId(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ctx.bodyAsClass(Message.class);
        // Message message = messageService.getMessageById(id);

        try{
            if (message!=null) {// The message exists, so update it
                messageService.updateMessage(message,id);
                System.out.println(messageService.getMessageById(id));
                ctx.json(messageService.getMessageById(id));
                
            } else {
                ctx.status(400);
            }
        }
        catch(Exception e){
            ctx.status(400);
        }
    }
    private void getMessagesByAccountId(Context ctx) {
        try {
            int accountId = Integer.parseInt(ctx.pathParam("account_id"));

            // Call the messageService to retrieve messages by account ID
            List<Message> messages = messageService.getMessagesByAccountId(accountId);
            if (!messages.isEmpty()) {
                // If messages are found, send them as a JSON response
                ctx.json(messages);
            } else {
                // If no messages are found, send an empty JSON response
                ctx.json(messages);
                ctx.status(200);
            }
        } catch (Exception e) {
            // Handle ServiceException and set the status code to 400 (Bad Request)
            ctx.status(400);
        }
    }
}