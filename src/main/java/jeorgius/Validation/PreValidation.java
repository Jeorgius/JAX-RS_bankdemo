package jeorgius.Validation;

import jeorgius.Account.Account;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PreValidation {

    private ValidationService validationService = new ValidationService();
    private String resultMsg;
    private boolean val;

    public boolean validateCreation(String url, String post_number) {

        Account acc = validationService.accountNumberCaster(url, post_number, "0");
        //Integer[] acc = validationService.accountNumberCaster(url, post_number, "0");

        if(acc!=null){
            try{
                val = validationService.url_Post_match(acc.getUrl(),acc.getAccount_number()) &&
                        validationService.validateRule(acc.getAccount_number())        &&
                        !validationService.checkMatch(acc.getAccount_number());

                resultMsg = validationService.getResultMsg();
                return val;

            } catch (Exception e){
                System.out.println("Error: database " + e.getMessage());
                resultMsg = "Error: database " + e.getMessage();
                return false;
            }
        } else{
            resultMsg = validationService.getResultMsg();
            return false;
        }
    }

    public boolean validateAccountBalanceAction(String url, String post_number, String sum) {

        Account acc = validationService.accountNumberCaster(url, post_number, sum);
        //Integer[] acc = validationService.accountNumberCaster(url, post_number, sum);

        if(acc!=null){
            try{

                val = validationService.url_Post_match(acc.getUrl(),acc.getAccount_number()) &&
                        validationService.validateRule(acc.getAccount_number())  &&
                        validationService.checkMatch(acc.getAccount_number()) &&
                        validationService.checkSumValue(acc.getSum());

                resultMsg = validationService.getResultMsg();
                return val;

            } catch (Exception e){
                resultMsg = "Error: database " + e.getMessage();
                System.out.println("Error: database " + e.getMessage());
                return false;
            }
        } else{
            resultMsg = validationService.getResultMsg();
            return false;
        }
    }

    public boolean validateAccountBalance(String url) {

        //Integer[] acc = validationService.accountNumberCaster(url, "0", "0");
        Account acc = validationService.accountNumberCaster(url, "0", "0");

        if(acc!=null){
            try{
                val = validationService.validateRule(acc.getUrl()) &&
                        validationService.checkMatch(acc.getUrl());

                resultMsg = validationService.getResultMsg();
                return val;

            } catch (Exception e){
                resultMsg = "Error: database " + e.getMessage();
                System.out.println("Error: database " + e.getMessage());
                return false;
            }
        } else {
            resultMsg = validationService.getResultMsg();
            return false;
        }
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    //public String
}
