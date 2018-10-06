package jeorgius;

import jeorgius.DbAccess.DbAction;
import jeorgius.Validation.PreValidation;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BalanceTest {
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
    }

    @After
    public void tearDown() throws Exception {
        dbAction.deleteAccount("00000");
        server.stop();
    }


    @Test
    public void testBalance() {
        // 1 EXISTING ACCOUNT IS '00000'
        String responseMsg = target.path("00000/balance").request().get(String.class);
        assertEquals("Your current account balance is: 0", responseMsg);
        // 2 ACCOUNT DOESN'T EXIST
        String responseMsg1 = target.path("99999/balance").request().get(String.class);
        assertEquals("Error: Account doesn't exist: 99999", responseMsg1);
        // 3 NEGATIVE VALUE OF ACCOUNT
        String responseMsg2 = target.path("-0001/balance").request().get(String.class);
        assertEquals("Error: Account must be a 5-digit number without \"-\" sign", responseMsg2);
        // 4 CHARS IN ACCOUNT NUMBER
        String responseMsg3 = target.path("hh001/balance").request().get(String.class);
        assertEquals("Error: Account/Entered data must be a 5-digit number, but contains characters", responseMsg3);

    }

    @Test //various reactions on requests with STRING accountNum
    public void testBalanceValidation(){
        PreValidation preValidation = new PreValidation();
        //existing account
        assertTrue(preValidation.validateAccountBalance("00000"));
        //chars in account number
        assertFalse(preValidation.validateAccountBalance("hasdddda"));
        //negative 5-character number
        assertFalse(preValidation.validateAccountBalance("-1234"));
        //not existing account
        assertFalse(preValidation.validateAccountBalance("99999"));

    }
}
