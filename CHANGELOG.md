
# Change Log
Documentação das mudanças do projeto
 
Baseado em [Keep a Changelog](http://keepachangelog.com/)
e esse projeto adere [Semantic Versioning](http://semver.org/).

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
