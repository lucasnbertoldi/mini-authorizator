
# Change Log
Documentação das mudanças do projeto
 
Baseado em [Keep a Changelog](http://keepachangelog.com/)
e esse projeto adere [Semantic Versioning](http://semver.org/).

## [0.1.0] 2023-02-06
### Added
- Adicionando Swagger em http://localhost:8080/swagger-ui/index.html

### Fixed
- Downgrade versão spring da 3.0.2 para 2.7.9-SNAPSHOT por que o springdoc-openapi-ui não funciona na versão >=3.0.0
- Correção de importações.

## [0.0.12] 2023-02-06

### Added
- Arquivo com Action para executar testes no Github.

## [0.0.11] 2023-02-06

### Added
- Adicionando teste de cartão inexistente.
- Criação do controller e DTO de transação.
- Criação de Enum TransactionResponse com os tipos de resposta da transação.
- Criação do teste de integração da transação. 

### Fixed
- Status retornado pela requisição de criar cartão (201).
- Exceção de cartão não encontrado no CardService alterada para IllegalArgumentException.

## [0.0.10] 2023-02-06

### Added
- Tratamento de erros da transação (senha incorreta, saldo insuficiente e parâmetros incorretos).
- Testes unitários para a transação.

### Fixed
- Código que verifica a senha estava incorreto.

## [0.0.9] 2023-02-06

### Added
- Consulta do saldo do cartão no controller e service do cartão.
- Teste de integração da consulta do cartão.

## [0.0.8] 2023-02-06

### Changed
- Alterando timezone da api para America/Bahia.

### Added
- Serviço SystemDate para recolher a data do sistema.
- Interface para armazenar constante referente ao valor inicial do cartão.
- Criação da transação inicial quando se salva um cartão.
- Teste unitário para valor inicial do cartão.

## [0.0.7] 2023-02-06

### Added
- Entidade, repositório e serviço da transação.
- Início do teste unitário de transação.

## [0.0.6] 2023-02-06

### Added
- Controller para cadastro do cartão
- Criação de interface e implementação para geração do hash para senha do cartão.

### Changed
- Criação de inteface para conversor do JSON para desaclopar a dependência com.fasterxml do conversor.

## [0.0.5] 2023-02-06

### Added
- DTO e Controller para cadastro do cartão.
- Service para converter objetos JSON.

### Changed
- Adicionando extensão do pacote java para VSCode no README.md.
- Função de salvar o cartão retorna a entidade.
- Substituição do objeto de teste para DTO.

## [0.0.4] 2023-02-05

### Added
- Entidade, repositório e service do Cartão.
- Teste unitário e de integração do Cartão.
- Banco de dados em memória para teste (h2database).
- Arquivo de properties para teste.

### Changed
- Separação dos testes unitários e de integração em dois pacotes.

### Fixed
- Havia mudado a versão da imagem da api por engano. Retornei para a versão correta.


## [0.0.3] 2023-02-05

### Added
- Adicionando dependência spring-boot-starter-actuator para verificar a saúde da aplicação.
- Adicionando teste de saúde da aplicação.
- Adicionando no container da api a etapa de teste antes de iniciar a aplicação

## [0.0.2] 2023-02-05

### Added
- Link para o CHANGELOG.md no README.md.

### Changed
- Nome para links das extensões no README.md.

## [0.0.1] 2023-02-05

### Added
- Criação dos arquivos necessários para desenvolver um projeto Spring Boot.
- Adição das dependências necessárias para o Spring Web (spring-boot-starter-web) e Spring Data (spring-boot-starter-data-jpa e mysql-connector-j).
- Adição das dependências de desenvolvimento (spring-boot-starter-test e spring-boot-devtools).
- Criação de um arquivo README.md com instruções de como executar o projeto e informações úteis.
- Ignorando alguns arquivos no git.

### Changed
- Docker compose:
    - Criação de uma rede chamada database_network para conectar os serviços que precisarem se comunicar com o banco de dados.
    - Criação de um serviço denominado api para executar o Spring Boot.
    - Serviço do phpmyadmin para acompanhar o banco de dados.

 ### Removed
- Remoção da exposição das portas do serviço do mysql do docker-compose por motivos de segurança.
