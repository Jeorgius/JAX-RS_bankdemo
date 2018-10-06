package jeorgius.DbAccess;


import jeorgius.Account.Account;

import java.sql.SQLException;
import java.util.List;

public class DbAction {

    public void addAccount(String accountNum) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.createAccount(Sql.INSERT(accountNum));
    }

    public Account getAccount(String accountNum) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        List<Account> accounts = dbAccess.dbSelectAcc(Sql.SELECT(accountNum));
        if(accounts.size() != 0) return dbAccess.dbSelectAcc(Sql.SELECT(accountNum)).get(0);
        else return null;
    }

    public String updateAccount(String accountNum, Integer newSum) {
        DbAccess dbAccess = new DbAccess();
        return dbAccess.updateBalance(Sql.UPDATE(accountNum,newSum));
    }

    public String deleteAccount(String accountNum) {
        DbAccess dbAccess = new DbAccess();
        return dbAccess.updateBalance(Sql.DELETE(accountNum));
    }


}
