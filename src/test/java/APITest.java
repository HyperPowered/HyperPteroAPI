import net.hyperpowered.PteroAPI;
import net.hyperpowered.manager.UserManager;
import net.hyperpowered.user.builder.UserBuilder;
import net.hyperpowered.utils.ManagerPolicy;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITest {

    private UserManager userManager;

    @BeforeAll
    public void before() {

        if (!System.getenv().containsKey("PTERO_URL") || !System.getenv().containsKey("PTERO_KEY")) {
            throw new RuntimeException("PTERO_URL and PTERO_KEY are required");
        }

        String PTERO_URL = System.getenv("PTERO_URL");
        String PTERO_KEY = System.getenv("PTERO_KEY");

        PteroAPI.initPteroAPI(PTERO_URL, PTERO_KEY, ManagerPolicy.ALL);
        this.userManager = PteroAPI.getManager(UserManager.class);
    }

    private long userId;

    @Test
    @DisplayName("Create User")
    @Order(1)
    public void createUser() throws Exception {
        CompletableFuture<JSONObject> future = userManager.createUser(new UserBuilder().appendEmail("testesequencial@gmail.com").appendUsername("teste").appendFirstName("John").appendLastName("Cena").appendPassword("a@#%sPpsn9gy53gAaa3v"));

        JSONObject userPayload = (JSONObject) ((JSONObject) future.get().get("response")).get("attributes");
        assertNotNull(userPayload);
        this.userId = (long) userPayload.get("id");
    }

}
