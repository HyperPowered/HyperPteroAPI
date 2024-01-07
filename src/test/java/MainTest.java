import com.hyperpowered.ojvzinn.ptero.PterodactylAPI;
import com.hyperpowered.ojvzinn.ptero.builder.ServerCreatorBuilder;
import com.hyperpowered.ojvzinn.ptero.manager.ServersManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    //Executa após o inicio do test
    @BeforeAll
    public void before() {
    }

    //Executa juntamente com o test
    @Test
    @DisplayName("test")
    public void test() {
        PterodactylAPI.changeDebugMode();
        ServersManager manager = PterodactylAPI.managerServer("", "");
    }


}

