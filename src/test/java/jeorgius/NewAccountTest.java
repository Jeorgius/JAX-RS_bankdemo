package jeorgius;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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

import static org.junit.Assert.*;

public class NewAccountTest {
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
        dbAction.deleteAccount("00001");
        server.stop();
    }


    @Test
    public void testCreation() {
        String e0 = "{\"account_number\":\"00000\"}";
        String e1 = "{\"account_number\":\"00001\"}";
        String e2 = "{\"account_number\":\"00001\"}";
        String e3 = "{\"account_number\":\"hhasd\"}";


        String responseMsg1 = target.path("00001").request().post(Entity.entity(e1,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("OK: Account created", responseMsg1);
        // EXISTS
        String responseMsg0 = target.path("00000").request().post(Entity.entity(e0,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("Error: Account already exists: 00000", responseMsg0);
        // don't match
        String responseMsg2 = target.path("-0001").request().post(Entity.entity(e2,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("Error: Account number and its URL don't match", responseMsg2);

        String responseMsg3 = target.path("hhasd").request().post(Entity.entity(e3,MediaType.APPLICATION_JSON_TYPE),String.class);
        assertEquals("Error: Account/Entered data must be a 5-digit number, but contains characters", responseMsg3);

       // System.out.println(target.path("00000").request());
    }

    @Test //various reactions on requests with STRING accountNum
    public void testCreationValidation(){
        PreValidation preValidation = new PreValidation();
        //VALID account
        assertTrue(preValidation.validateCreation("11111", "11111"));
        //starts with 0
        assertTrue(preValidation.validateCreation("01234", "01234"));
        //chars in account number
        assertFalse(preValidation.validateCreation("hasdddda", "hasdddda"));
        //negative 5-character number
        assertFalse(preValidation.validateCreation("-1234", "-1234"));
        //url and form number don't match
        assertFalse(preValidation.validateCreation("99999", "12354"));
        //account already exists
        assertFalse(preValidation.validateCreation("00000", "00000"));
    }

}
