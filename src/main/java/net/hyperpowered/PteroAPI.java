package net.hyperpowered;

import lombok.Getter;
import net.hyperpowered.logger.PteroLogger;
import net.hyperpowered.manager.Manager;
import net.hyperpowered.utils.ManagerPolicy;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PteroAPI {

    private static final List<Manager> managers = new ArrayList<>();

    private static final PteroLogger LOGGER = new PteroLogger("API");

    @Getter
    private static String url;

    @Getter
    private static String applicationToken;

    public static void initPteroAPI(String urlApplication, String applicationTokenApplication, ManagerPolicy... managerPolicy) {
        url = urlApplication;
        applicationToken = applicationTokenApplication;
        startupManagers(managerPolicy);
    }

    public static <T extends Manager> T getManager(Class<T> managerClass) {
        if (managerClass == null || !Manager.class.isAssignableFrom(managerClass)) {
            return null;
        }

        Manager managerResponse = managers.stream().filter(manager -> managerClass.isAssignableFrom(manager.getClass())).findFirst().orElse(null);
        return managerResponse != null ? managerClass.cast(managerResponse) : null;
    }

    private static void startupManagers(ManagerPolicy... managerPolicy) {
        for (ManagerPolicy manager : managerPolicy) {
            if (manager == ManagerPolicy.ALL) {
                Arrays.stream(ManagerPolicy.values()).map(PteroAPI::loadClass).filter(Objects::nonNull).forEach(managers::add);
                return;
            }

            managers.add(loadClass(manager));
        }

    }

    private static Manager loadClass(ManagerPolicy managerPolicy) {
        try {
            Class<? extends Manager> managerClass = managerPolicy.getClassManager();

            if (managerClass == null) {
                return null;
            }

            Constructor<? extends Manager> constructor = managerClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            LOGGER.severe("OCORREU UM ERRO AO INICIALIZAR AS CLASSES GERENCIADORAS");
            throw new RuntimeException(e);
        }
    }

}
