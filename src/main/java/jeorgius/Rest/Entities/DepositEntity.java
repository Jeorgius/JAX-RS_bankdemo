package jeorgius.Rest.Entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DepositEntity {

    private String account_number;
    private String deposit;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
