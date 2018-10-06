package jeorgius.Rest.Entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateEntity {
    private String account_number;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
}
