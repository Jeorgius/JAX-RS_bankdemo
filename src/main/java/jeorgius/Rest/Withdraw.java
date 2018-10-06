package jeorgius.Rest;

import jeorgius.DbAccess.DbAction;
import jeorgius.Rest.Entities.WithdrawEntity;
import jeorgius.Validation.PreValidation;
import jeorgius.Validation.ValidationService;
import javax.ws.rs.*;

@Path("{account}/withdraw")
    public class Withdraw {

    public Withdraw(){}


    private PreValidation preValidation = new PreValidation();
    private DbAction dbAction = new DbAction();
    private String result;

    @PUT
    @Consumes("application/json")
    public String accountDeposit(@PathParam("account") String accountNum,
                                 WithdrawEntity withdrawEntity) {
        try{
            if(preValidation.validateAccountBalanceAction(accountNum, withdrawEntity.getAccount_number(), withdrawEntity.getWithdraw())) {

                Integer toWithdraw = Integer.parseInt(withdrawEntity.getWithdraw());
                Integer currentBalance = dbAction.getAccount(withdrawEntity.getAccount_number()).getBalance();

                Integer updatedSum = currentBalance - toWithdraw;
                if(updatedSum>=0){
                    dbAction.updateAccount(withdrawEntity.getAccount_number(),updatedSum);
                    return ValidationService.Message0+ValidationService.Message3 + toWithdraw +
                           "\nCurrent balance is: " + updatedSum;
                } else {
                    return "Error: insufficient funds\n" +
                                       "Balance: "+currentBalance +"\n" +
                                       "Sum to withdraw: " + toWithdraw;
                }

            } else {
                return "Error: " + preValidation.getResultMsg();
            }
        } catch (Exception e){
            return "Error: withdraw failed " + e.getMessage();
        }
    }
}
