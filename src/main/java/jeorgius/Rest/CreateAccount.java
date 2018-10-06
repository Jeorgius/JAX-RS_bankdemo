package jeorgius.Rest;

import jeorgius.DbAccess.DbAction;
import jeorgius.Rest.Entities.CreateEntity;
import jeorgius.Validation.PreValidation;
import jeorgius.Validation.ValidationService;
import org.glassfish.jersey.message.internal.MediaTypes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("{account}")
public class CreateAccount {


    private PreValidation preValidation = new PreValidation();
    private DbAction dbAction = new DbAction();

    public CreateAccount(){}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createAccount(@PathParam("account") String accountNum,
                                CreateEntity createEntity) {
        try{
            if(preValidation.validateCreation(accountNum, createEntity.getAccount_number())) {
                dbAction.addAccount(createEntity.getAccount_number());
                System.out.println(ValidationService.Message0+ValidationService.Message1);
                return ValidationService.Message0+ValidationService.Message1;
            } else{
                System.out.println(preValidation.getResultMsg());
                return "Error: " + preValidation.getResultMsg();
            }
        } catch (Exception e){
            System.out.println("Error: creating account failed");
            return "Error: creating account failed";
        }
    }
}
