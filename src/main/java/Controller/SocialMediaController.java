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

        Javalin app = Javalin.create();
        app.post("/register", this::postAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        // app.delete("/messages/{message_id}", this::deleteMessageByMessageId);

        // app.get("/messages/{message_id}", this::getMessageById);
        // app.patch("/messages/{message_id}", this::updateMessageByMessageId);

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



















    private void deleteMessageByMessageId(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message deleteMessage = messageService.removeMessage(message);
        if(deleteMessage==null){
            ctx.status(200);
        } else{
            ctx.json(mapper.writeValueAsString(deleteMessage));
        }
    }

    // private void getAllMessageForUserHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     Account author = mapper.readValue(ctx.body(), Account.class);
    //     Account addedAuthor = AccountService.setAuthor(author);

    //     if(addedAuthor!=null){
    //         ctx.json(mapper.writeValueAsString(addedAuthor));
    //     }else{
    //         ctx.status(400);
    //     }
    // }
    // private void getAllMessageHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     Account author = mapper.readValue(ctx.body(), Account.class);
    //     Account addedAuthor = AccountService.setAuthor(author);

    //     if(addedAuthor!=null){
    //         ctx.json(mapper.writeValueAsString(addedAuthor));
    //     }else{
    //         ctx.status(400);
    //     }
    // }
    // private void getMessageTextHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     Account author = mapper.readValue(ctx.body(), Account.class);
    //     Account addedAuthor = AccountService.setAuthor(author);

    //     if(addedAuthor!=null){
    //         ctx.json(mapper.writeValueAsString(addedAuthor));
    //     }else{
    //         ctx.status(400);
    //     }
    // }
    // private void updateMessageTextByIdHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     Account author = mapper.readValue(ctx.body(), Account.class);
    //     Account addedAuthor = AccountService.setAuthor(author);

    //     if(addedAuthor!=null){
    //         ctx.json(mapper.writeValueAsString(addedAuthor));
    //     }else{
    //         ctx.status(400);
    //     }
    // }
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

}