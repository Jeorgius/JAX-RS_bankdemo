package jeorgius.Rest.Entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WithdrawEntity {
    private String account_number;
    private String withdraw;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }
}
