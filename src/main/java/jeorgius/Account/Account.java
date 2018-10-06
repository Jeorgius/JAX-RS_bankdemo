package jeorgius.Account;

import java.io.Serializable;

public class Account implements Serializable {

    public Account(){}
    public Account(String url, String account_number, Integer sum){
        this.url = url;
        this.account_number = account_number;
        this.sum = sum;
    }

    private Integer id;
    private String account_number;
    private String url;
    private Integer balance;
    private Integer sum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
