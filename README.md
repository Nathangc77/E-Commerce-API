# E-Commerce-API

## Descrição
E-Commerce-API é uma API REST desenvolvida para atender ao segmento de comércio eletrônico. O projeto fornece funcionalidades para gerenciamento de produtos e pedidos, com um sistema de autenticação robusto.

## Tecnologias Utilizadas
- **Java**
- **Maven**
- **Spring Boot**
- **Postman**

## Instalação
Siga o passo a passo abaixo para configurar e rodar o projeto localmente:

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Nathangc77/E-Commerce-API.git
   ```

2. **Certifique-se de ter as tecnologias necessárias instaladas:**
    - Java (versão 11 ou superior)
    - Maven

3. **Configure o projeto:**
    - Importe o projeto em sua IDE favorita (como IntelliJ ou Eclipse).
    - Execute o comando abaixo para baixar as dependências:
      ```bash
      mvn install
      ```

4. **Execute o projeto:**
    - Utilize o comando:
      ```bash
      mvn spring-boot:run
      ```

5. **Testes no Postman:**
    - A coleção e o environment do Postman estão disponíveis na raiz do projeto para facilitar os testes.

## Funcionalidades

### Recursos principais:
1. **Autenticação e Autorização:**
    - Implementação de autenticação via JWT e OAuth2.
2. **CRUD de Produtos:**
    - Inserção, atualização e remoção de produtos (restrito a administradores).
    - Obtenção de lista de produtos (disponível para clientes e administradores).
3. **Gerenciamento de Pedidos:**
    - Criação de pedidos por clientes.
    - Visualização de pedidos associados ao cliente logado.

### Contas pré-definidas:
- **Admin:**
    - Email: maria@gmail.com
    - Senha: 123456

- **Cliente:**
    - Email: alex@gmail.com
    - Senha: 123456

## Público-Alvo
Este projeto é destinado a:
- Desenvolvedores que desejam aprender mais sobre APIs REST com Spring Boot.
- Empresas interessadas em avaliar minhas habilidades como desenvolvedor Java.

## Licença
Este projeto é licenciado sob a licença MIT. Consulte o arquivo `LICENSE` para mais informações.

## Contato
- **Email:** nathangc7@gmail.com
- **LinkedIn:** [Nathan Moreira](https://www.linkedin.com/in/nathan-moreira-a07037191/)

