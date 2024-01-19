import com.hyperpowered.ojvzinn.ptero.PterodactylAPI;
import com.hyperpowered.ojvzinn.ptero.builder.ServerUpdaterBuildBuilder;
import com.hyperpowered.ojvzinn.ptero.builder.ServerUpdaterDetailsBuilder;
import com.hyperpowered.ojvzinn.ptero.builder.ServerUpdaterStartupBuilder;
import com.hyperpowered.ojvzinn.ptero.manager.ServersManager;
import com.hyperpowered.ojvzinn.ptero.model.ServerModel;
import com.hyperpowered.ojvzinn.ptero.utils.CapacityEnum;
import com.hyperpowered.ojvzinn.ptero.utils.DebugMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

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
        PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
        ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
        ServerModel server = serverManager.getServerByID("1"); //Busque o servidor alvo com seu ID
        ServerUpdaterStartupBuilder builder = new ServerUpdaterStartupBuilder(server); //Utilize o object do servidor para que, se caso não for alterar todas as configuções, se mantenha como está
        builder.appendEggID(1L); //Atualizará o egg do servidor para o Egg do ID 1
        serverManager.updateServerStartup("1", builder); //Envie todas as configurações feita pelo builder para o Pterodactyl
    }


}

