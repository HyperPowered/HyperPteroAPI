import net.hyperpowered.PteroAPI;
import net.hyperpowered.manager.NestManager;
import net.hyperpowered.manager.NodeManager;
import net.hyperpowered.manager.ServerManager;
import net.hyperpowered.manager.UserManager;
import net.hyperpowered.nest.Egg;
import net.hyperpowered.node.Allocation;
import net.hyperpowered.server.builder.ServerAllocationBuilder;
import net.hyperpowered.server.builder.ServerBuilder;
import net.hyperpowered.server.builder.ServerFutureLimitBuilder;
import net.hyperpowered.server.builder.ServerLimitBuilder;
import net.hyperpowered.user.User;
import net.hyperpowered.user.builder.UserBuilder;
import net.hyperpowered.utils.ManagerPolicy;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITest {

    private UserManager userManager;
    private ServerManager serverManager;
    private NodeManager nodeManager;
    private NestManager nestManager;

    @BeforeAll
    public void before() {

        if (!System.getenv().containsKey("PTERO_URL") || !System.getenv().containsKey("PTERO_KEY")) {
            throw new RuntimeException("PTERO_URL and PTERO_KEY are required");
        }

        String PTERO_URL = System.getenv("PTERO_URL");
        String PTERO_KEY = System.getenv("PTERO_KEY");

        PteroAPI.initPteroAPI(PTERO_URL, PTERO_KEY, ManagerPolicy.ALL);
        this.userManager = PteroAPI.getManager(UserManager.class);
        this.serverManager = PteroAPI.getManager(ServerManager.class);
        this.nodeManager = PteroAPI.getManager(NodeManager.class);
        this.nestManager = PteroAPI.getManager(NestManager.class);
    }

    private long userId;
    private User user;

    @Test
    @DisplayName("Create User")
    @Order(1)
    @Disabled
    public void createUser() throws Exception {
        CompletableFuture<JSONObject> future = userManager.createUser(new UserBuilder()
                .appendEmail("testesequencial@gmail.com")
                .appendUsername("teste")
                .appendFirstName("John")
                .appendLastName("Cena")
                .appendPassword("a@#%sPpsn9gy53gAaa3v")
                .appendExternalId("leitadaJunior"));

        User userPayload = userManager.parseUser(future.get().get("response").toString());
        assertNotNull(userPayload);
        this.userId = userPayload.getId();
        this.user = userPayload;
    }

    @Test
    @DisplayName("Update User")
    @Order(2)
    @Disabled
    public void updateUser() throws Exception {
        user.setUsername("userofnames");
        user.setFirstName("Joao");
        user.setLastName("Cena");
        userManager.updateUser(user);
    }

    @Test
    @DisplayName("Delete User")
    @Order(3)
    @Disabled
    public void deleteUser() throws Exception {
        userManager.deleteUser(userId);
    }

    private long serverId;

    @Test
    @DisplayName("Create Server")
    @Order(4)
//    @Disabled
    public void createServer() throws Exception {
        Egg egg = nestManager.getEgg(7, 18).get();
        System.out.println(egg);
        ServerAllocationBuilder serverAllocation = new ServerAllocationBuilder().appendDefault(57L);

        ServerFutureLimitBuilder serverFutureLimit = new ServerFutureLimitBuilder().appendBackups(1)
                .appendDatabase(0);

        ServerLimitBuilder serverLimit = new ServerLimitBuilder().appendCPU(100)
                .appendMemory(128)
                .appendDisk(512)
                .appendIO(500)
                .appendSwap(0);

        JSONObject env = new JSONObject();
        env.put("PGUSER", "ptero");
        env.put("PGPASSWORD", "Pl3453Ch4n63M3!");

        ServerBuilder builder = new ServerBuilder().appendName("Building")
                .appendServerAllocationLimit(serverAllocation)
                .appendStartup(egg.getStartup())
                .appendServerFutureLimit(serverFutureLimit)
                .appendEnvironment(env)
                .appendEgg(egg.getId())
                .appendUser(1)
                .appendDockerImage(egg.getDocker_image())
                .appendExternalId("abc123")
                .appendServerLimit(serverLimit);

        CompletableFuture<JSONObject> future = serverManager.createServer(builder);
        JSONObject serverPayload = (JSONObject) ((JSONObject) future.get().get("response")).get("attributes");
        assertNotNull(serverPayload);
        this.serverId = (long) serverPayload.get("id");
    }

    @Test
    @DisplayName("Get Allocations")
    @Order(5)
    @Disabled
    public void getAllocations() throws Exception {
        List<Allocation> allcs = nodeManager.listAllocations(1).get();
        int totalSize = nodeManager.getAllocationCount(1).get();
        assertEquals(totalSize, allcs.size());
    }

    @Test
    @DisplayName("Get Non Exists User")
    @Order(6)
    @Disabled
    public void userNonExists() throws Exception {
        assertThrows(CompletionException.class, () -> {
            User user = userManager.getUser(4).join();
        });
    }

}
