# HyperPteroAPI
O HyperPteroAPI, é uma API feita em java, para gerenciamento de servidores utilizando o painel Pterodatly.
## Como Começar
Para utilizar esta API, é necessário você iniciar o módulos que você irá utilizar na sua aplicação. Com isso, basta você utilizar esse código:
<br/>
```java
public class Main {
    public static void main(String[] args) {
        PteroAPI.initPteroAPI("https://example.com.br", "TOKEN", ManagerPolicy.ALL);
    }
}
```
<br/>
Com isso, você inicia a API, com todos os seus módulos funcionando.
## Como um manager
Para utilizar algum gerenciador, é necessário que você a função "getManager", que pede como parametro uma classe, na qual estende a classe "Manager".
<br/>
```java
public class Main {
    public static void main(String[] args) {
        PteroAPI.initPteroAPI("https://example.com.br", "TOKEN", ManagerPolicy.ALL);
        UserManager userManager = PteroAPI.getManager(UserManager.class);
    }
}
```
<br/>
Assim, sendo possível gerenciar os usuários do painel.
