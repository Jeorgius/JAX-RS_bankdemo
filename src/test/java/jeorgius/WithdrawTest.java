package jeorgius;

import jeorgius.DbAccess.DbAction;
import jeorgius.Validation.PreValidation;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WithdrawTest {
    private HttpServer server;
    private WebTarget target;
    private DbAction dbAction;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
        this.dbAction = new DbAction();
        dbAction.addAccount("00000");
        dbAction.updateAccount("00000", 9000);
    }

    @After
    public void tearDown() throws Exception {
        dbAction.deleteAccount("00000");
        server.stop();
    }


    @Test
    public void testWithdraw() {
        String e0 = "{\"account_number\":\"00000\",\"withdraw\":\"900\"}";
        String e1 = "{\"account_number\":\"00000\",\"withdraw\":\"-9000\"}";
        String e2 = "{\"account_number\":\"00000\",\"withdraw\":\"hhasd\"}";
        String e3 = "{\"account_number\":\"00000\",\"withdraw\":\"9001\"}";

        // Success!
        String responseMsg0 = target.path("00000/withdraw").request().put(Entity.entity(e0,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("OK: Entered sum has been successfully withdrawn: 900\n" + "Current balance is: 8100", responseMsg0);

        String responseMsg1 = target.path("00000/withdraw").request().put(Entity.entity(e1,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("Error: Deposit/Withdraw sum must be greater than null", responseMsg1);

        String responseMsg2 = target.path("00000/withdraw").request().put(Entity.entity(e2,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("Error: Account/Entered data must be a 5-digit number, but contains characters", responseMsg2);

        String responseMsg3 = target.path("00000/withdraw").request().put(Entity.entity(e3,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("Error: insufficient funds\n" + "Balance: 8100\n" + "Sum to withdraw: 9001", responseMsg3);

    }
}
