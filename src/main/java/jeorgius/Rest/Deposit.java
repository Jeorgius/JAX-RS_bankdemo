package jeorgius.Rest;

import jeorgius.DbAccess.DbAction;
import jeorgius.Rest.Entities.DepositEntity;
import jeorgius.Validation.PreValidation;
import jeorgius.Validation.ValidationService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("{account}/deposit")
public class Deposit {

    private PreValidation preValidation = new PreValidation();
    private DbAction dbAction = new DbAction();

    public Deposit(){}

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String deposit(@PathParam("account") String accountNum,
                                 DepositEntity depositEntity) {
        try{
            if(preValidation.validateAccountBalanceAction(accountNum,
                                                          depositEntity.getAccount_number(),
                                                          depositEntity.getDeposit())) {

                Integer toDeposit = Integer.parseInt(depositEntity.getDeposit());
                Integer currentBalance = dbAction.getAccount(depositEntity.getAccount_number()).getBalance();

                Integer updatedSum = currentBalance + toDeposit;

                dbAction.updateAccount(depositEntity.getAccount_number(),updatedSum);
                return ValidationService.Message0+ValidationService.Message2 + toDeposit;
            } else {
                return "Error: " + preValidation.getResultMsg();
            }
        } catch (Exception e){
            System.out.println("Error: deposit failed");
            return "Error: deposit failed";
        }
    }
}
