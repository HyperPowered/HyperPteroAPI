# HyperPteroAPI - Gerencie suas máquinas no Pterodactyl de forma simples e fácil usando a HyperPteroAPI
Essa API gerencia todos os usários, servidores, nodes, portas e eggs através de conexões HTTP. Você consiguirá visualizar todas as informações do seu Pterodactyl através de Objects autoexplicativos, com diversas funcionalidades e altamente maleáveis.

Essa API foi criada como forma de gerenciamento simples e otimizado para os sistemas da HyperPowered, especificadamente da HyperHosthing.

## Iniciando com o HyperPteroAPI
Essa API é de fácil utilização, então você não terá problemas em utiliza-la

### Como puxar todas as funcionalidades da API:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON); //Dessa maneira, você instancia o object utilizando o DebugMode
}
```

### Como desabilitar o DebugMode:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    api.changeDebugMode(); //Dessa meneira, passará de OFF para ON, ou, de ON para OFF
}
```

Ou caso queria setar um valor diretamente, você pode garantir isso utilizando:

```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    api.setDebugMode(DebugMode.OFF);
}
```

Lembrando que o DebugMode serve para verificar as requisições que estão sendo executadas através da API.

## Gerenciando servidores com o HyperPteroAPI
Nessa API, assim como mencionado anteriormente, possui suporte a gerenciamento de servidores do painel Pterodactyl

### Como iniciar o gerencimento de servidores:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
}
```

### Como criar um servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    ServerCreatorBuilder builder = new ServerCreatorBuilder(); //Você consegue implementar várias configurações diramente no builder
    builder.appendServerName("Servidor Testes");
    builder.appendServerPortIndex(1L);
    builder.appendUserOwner(1);
    serverManager.createServer(builder); //Apenas configurando o builder e o utilizando como parâmetro, você consegue criar um servidor
}
```

### Como deletar um servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    serverManager.deleteServer("1"); //Coloque o ID do servidor
}
```

### Como forçar o delete de um servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    serverManager.forceDeleteServer("1"); //Coloque o ID do servidor
}
```

### Como reinstalar um servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    serverManager.reinstallServer("1"); //Coloque o ID do servidor
}
```

### Como suspender um servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    serverManager.suspendServer("1"); //Coloque o ID do servidor
}
```

### Como cancelar a suspenção de um servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    serverManager.unSuspendServer("1"); //Coloque o ID do servidor
}
```

### Como listar todos os servidores utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    List<ServerModel> servers = serverManager.listAllServers();
}
```

### Atualize informações básicas do seu servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    ServerModel server = serverManager.getServerByID("1"); //Busque o servidor alvo com seu ID
    ServerUpdaterDetailsBuilder builder = new ServerUpdaterDetailsBuilder(server); //Utilize o object do servidor para que, se caso não for alterar todas as configuções, se mantenha como está
    builder.appendName("Teste Novo"); //Atualizará o nome do servidor
    serverManager.updateServerDetails("1", builder); //Envie todas as configurações feita pelo builder para o Pterodactyl
}
```

### Atualize informações de build do seu servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    ServerModel server = serverManager.getServerByID("1"); //Busque o servidor alvo com seu ID
    ServerUpdaterBuildBuilder builder = new ServerUpdaterBuildBuilder(server); //Utilize o object do servidor para que, se caso não for alterar todas as configuções, se mantenha como está
    builder.appendMemoryLimit(1D, CapacityEnum.GIGA); //Atualizará a quantidade de memoria do servidor para 1 giga
    serverManager.updateServerBuild("1", builder); //Envie todas as configurações feita pelo builder para o Pterodactyl
}
```

### Atualize informações de startup do seu servidor utilizando o HyperPteroAPI:
```java
public void test() {
    PterodactylAPI api = new PterodactylAPI(DebugMode.ON);
    ServersManager serverManager = api.managerServer("API TOKEN DO PTERODACTYL", "https://painel.SEUDOMINIO.com");
    ServerModel server = serverManager.getServerByID("1"); //Busque o servidor alvo com seu ID
    ServerUpdaterStartupBuilder builder = new ServerUpdaterStartupBuilder(server); //Utilize o object do servidor para que, se caso não for alterar todas as configuções, se mantenha como está
    builder.appendEggID(1L); //Atualizará o egg do servidor para o Egg do ID 1
    serverManager.updateServerStartup("1", builder); //Envie todas as configurações feita pelo builder para o Pterodactyl
}
```


# Suporte e Dúvidas
Caso queria tirar alguma dúvida, me consulte em meu discord: ojvzinn;

Obrigado por acreditar e confiar em nós!
