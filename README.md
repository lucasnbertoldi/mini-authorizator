# Mini autorizador

API Web para autorizar para transações da VR Benefícios.

[Histórico de Mudanças](https://github.com/lucasnbertoldi/mini-authorizator/blob/main/CHANGELOG.md)

## Requisitos

| Ferramenta | Desenvolvido na Versão |
|--|--|
| Docker | 20.10.21 |
| Docker Compose | 1.29.2 |


## Executando

Navegue até a pasta do projeto e siga os seguintes passos:

Primeiro **crie a rede database_network** para o serviço mysql:

    docker network create database_network

Depois execute:

    docker-compose up -d

Caso queira acompanhar os logs:

    docker-compose logs -f

## Dicas para desenvolver no VSCode

**Extensões para facilitar o desenvolvimento**

- [https://marketplace.visualstudio.com/items?itemName=vmware.vscode-spring-boot](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-spring-boot)
- [Spring Boot Snippets](https://marketplace.visualstudio.com/items?itemName=developersoapbox.vscode-springboot-snippets)
- [Spring Code Generator](https://marketplace.visualstudio.com/items?itemName=SonalSithara.spring-code-generator)

**Auto deploy**

Instale a seguinte extensão: [File Watcher](https://marketplace.visualstudio.com/items?itemName=appulate.filewatcher)

Crie uma **pasta** chamada "**.vscode**" na raiz do projeto, caso não exista.
Dentro dessa pasta, crie um **arquivo** chamado "**settings.json**", caso não exista.

No arquivo **settings.json** adicione a seguinte configuração:

    {
	    "filewatcher.commands": [
			{
			    "match": "\\.java*",
			    "isAsync": true,
			    "cmd": "docker exec -i api bash -c 'cd /api && mvn compile'",
			    "event": "onFileChange"
		    }
	    ]
	}
