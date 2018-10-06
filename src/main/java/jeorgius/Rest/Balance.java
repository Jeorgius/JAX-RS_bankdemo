package jeorgius.Rest;

import jeorgius.Account.Account;
import jeorgius.DbAccess.DbAction;
import jeorgius.Validation.PreValidation;
import javax.ws.rs.*;

@Path("{account}/balance")
public class Balance {

    private PreValidation preValidation = new PreValidation();
    private DbAction dbAction = new DbAction();
    private String result;

    public Balance(){}

    @GET
    public String accountDeposit(@PathParam("account") String accountNum) {
        try{
            if(preValidation.validateAccountBalance(accountNum)) {

                Account account = dbAction.getAccount(accountNum);
                return "Your current account balance is: " + account.getBalance().toString();
            } else{
                return "Error: " + preValidation.getResultMsg();
            }
        } catch (Exception e){
            return "Error: balance operation failed " + e.getMessage();
        }
    }
}
