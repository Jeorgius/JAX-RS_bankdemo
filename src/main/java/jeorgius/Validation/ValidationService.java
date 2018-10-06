package jeorgius.Validation;



import jeorgius.Account.Account;
import jeorgius.DbAccess.DbAction;

import java.sql.SQLException;

public class ValidationService {

    private final String ErrorCode0 = "Account number and its URL don't match";
    private final String ErrorCode1 = "Account/Entered data must be a 5-digit number, but contains characters";
    private final String ErrorCode2 = "Account must be a 5-digit number without \"-\" sign";
    private final String ErrorCode3 = "Account doesn't exist: ";
    private final String ErrorCode4 = "Account already exists: ";
    private final String ErrorCode5 = "Deposit/Withdraw sum must be greater than null";


    public static final String Message0 = "OK: ";
    public static final String Message1 = "Account created";
    public static final String Message2 = "A deposit has been successfully transferred: ";
    public static final String Message3 = "Entered sum has been successfully withdrawn: ";

    private String resultMsg;


    //////// ACCOUNT CREATION
    public Account accountNumberCaster(String accNum, String accountNumber, String sum){

        try {
            int a = Integer.parseInt(accNum);
            int b = Integer.parseInt(accountNumber);
            int c = Integer.parseInt(sum);
            return new Account(accNum,accountNumber, c);
        } catch (Exception e) {
            //System.out.println(ErrorCode1);
            resultMsg = ErrorCode1;
            return null;
        }
    }

    public boolean url_Post_match (String url, String post_number){
        if(!url.equals(post_number)) /*System.out.println(ErrorCode0);*/ resultMsg = ErrorCode0;
        return url.equals(post_number);
    }

    public boolean validateRule(String url){
        //If false return
        if(url.length() != 5) /*System.out.println(ErrorCode1);*/ resultMsg = ErrorCode1;
        if(Integer.parseInt(url)<0) /*System.out.println(ErrorCode2);*/ resultMsg = ErrorCode2;
        return Integer.parseInt(url)>=0 && url.length() == 5;
    }

    public boolean checkMatch(String accNumber) throws SQLException, ClassNotFoundException {
        //true: "Account exists"
        //false: "Bank account created: OK" or operation failed due to lack of account
        DbAction dbAction = new DbAction();
        Account account = dbAction.getAccount(accNumber);
        if(account==null) /*System.out.println(ErrorCode3 +accNumber);*/ resultMsg = ErrorCode3+accNumber;
        if(account!=null) /*System.out.println(ErrorCode4 +accNumber);*/ resultMsg = ErrorCode4+accNumber;
        return account != null;
    }
    //////// DEPOSIT
    public boolean checkSumValue(Integer sum){
        if (sum<=0) /*System.out.println(ErrorCode5);*/ resultMsg = ErrorCode5;
        return sum>=0;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
