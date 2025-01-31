# 💻 _Loja Online Simplificada - Java Spring Boot e RabbitMQ_

![image](https://github.com/user-attachments/assets/a05604df-9d54-410a-822a-fec2d349aa04)


![GitHub repo size](https://img.shields.io/github/repo-size/jardeylsonJacinto/NLW-setup)
![GitHub](https://img.shields.io/github/license/jardeylsonJacinto/NLW-setup)
![jardeylson](https://img.shields.io/static/v1?label=Taught%20by&message=Andre%20Fonseca,%0AHenrique%20Jorge,%0AJardeylson%20Jacinto&color=gray&labelColor=8257E5)

## _Objetivo_ :thought_balloon:

Este projeto tem como objetivo a implementação de uma loja online simplificada utilizando Java Spring Boot e RabbitMQ. O foco é demonstrar a comunicação assíncrona entre diferentes componentes da aplicação, além de explorar conceitos como produtor, consumidor, exchange e fila no RabbitMQ. O sistema utiliza diferentes tipos de exchanges (direct, fanout, topic) para gerenciar a comunicação entre os módulos.

## _Estrutura da Aplicação_
A aplicação é dividida em módulos que se comunicam via RabbitMQ, cada um com responsabilidades específicas. O fluxo entre eles garante que as operações do sistema ocorram de forma desacoplada, robusta e eficiente.


### Módulos

#### 1. **Módulo de Pedidos**
- Recebe os pedidos e envia uma mensagem para o RabbitMQ com os detalhes do pedido.
- Utiliza um **exchange do tipo direct** para enviar a mensagem para a fila específica de pedidos.

#### 2. **Módulo de Pagamento**
- Consome mensagens da fila de pedidos.
- Simula o processamento de pagamentos e envia uma mensagem para a fila de **status do pedido**, indicando o progresso do pedido.

#### 3. **Módulo de Estoque**
- Consome mensagens da fila de pedidos.
- Atualiza o estoque com base nos itens do pedido, garantindo a integridade do inventário.

#### 4. **Módulo de Envio**
- Consome mensagens da fila de **status do pedido**.
- Envia um e-mail de confirmação para o cliente, avisando sobre o status do pedido.

## Extensões e Melhorias

Este projeto pode ser expandido com as seguintes funcionalidades adicionais:

- **Módulo de Notificações**: Enviar notificações por e-mail ou SMS em diferentes etapas do processo do pedido.
- **Sistema de Rastreamento de Pedidos**: Permitir que o cliente acompanhe o status do seu pedido em tempo real.
- **Banco de Dados**: Integrar um banco de dados para armazenar informações sobre pedidos, produtos e clientes.
- **Painel de Administração**: Implementar uma interface para monitoramento do sistema, visualizando estatísticas e realizando operações administrativas.

## Benefícios do Exercício

- **Aprendizado Prático**: Consolidar os conhecimentos sobre RabbitMQ e integração de microsserviços em um projeto real.
- **Compreensão de Microsserviços**: Demonstrar como diferentes serviços podem se comunicar de forma assíncrona e desacoplada.
- **Desenvolvimento de Habilidades**: Resolver problemas de configuração e lógica de mensagens assíncronas.
- **Trabalho em Equipe**: Incentivar a colaboração e divisão de responsabilidades entre os membros da equipe.

## Bibliotecas Java para RabbitMQ

- **Spring AMQP**: Framework para integração do Spring com RabbitMQ, facilitando a configuração e uso do RabbitMQ.
- **RabbitMQ Client**: Biblioteca oficial que fornece uma API de baixo nível para interação com RabbitMQ.

## Modelo Lógico da Aplicação

A aplicação é composta por várias entidades e relacionamentos, conforme descrito abaixo.

### Entidade Vendedor

- **IDvendedor**: Número sequencial e inteiro, gerado automaticamente pelo banco (campo obrigatório).
- **VendedorNome**: Nome do vendedor (campo obrigatório, string de até 45 caracteres).
- **VendedorSetor**: Setor ou equipe ao qual o vendedor pertence.

### Entidade StatusPedido

- **idStatusPedido**: Identificador único para o status do pedido (campo obrigatório).
- **statusPedidoDescricao**: Descrição do status (por exemplo, "Aguardando Pagamento", "Enviado", "Entregue").

### Entidade Pedido

- **idPedido**: Identificador único para o pedido (campo obrigatório).
- **PedidoDescricao**: Descrição breve do pedido (campo obrigatório).
- **PedidoValor**: Valor total do pedido.
- **PedidoQuantidade**: Quantidade de produtos no pedido.
- **PedidoDataHora**: Data e hora do pedido (campo obrigatório).
- **Vendedor_idVendedor**: Chave estrangeira para o vendedor responsável pelo pedido.

### Entidade Produto

- **idProduto**: Identificador único do produto (campo obrigatório).
- **ProdutoDescricao**: Descrição do produto (campo obrigatório).
- **ProdutoValor**: Valor unitário do produto.
- **ProdutoDataHoraSaida**: Data e hora de saída do produto do estoque.

### Entidade Pedido_historico_status

- **Pedido_idPedido**: Chave estrangeira para o pedido.
- **statusPedido_idstatusPedido**: Chave estrangeira para o status do pedido.
- **DataHoraStatusPedido**: Data e hora em que o pedido atingiu aquele status.
- **DataHoraPagamento**: Data e hora do pagamento do pedido.

### Entidade Pedido_tem_Produtos

- **Pedido_idPedido**: Chave estrangeira para o pedido.
- **Produto_idProduto**: Chave estrangeira para o produto que pertence ao pedido.

## Guia para Execução do Projeto

### Fluxo de Execução

A sequência para iniciar e executar o projeto é a seguinte:

1. **eureka-service**: Inicialize o serviço de descoberta.
2. **Módulos**:
   - **cliente-service**
   - **email-service**
   - **estoque-service**
   - **login-service**
   - **pagamento-service**
   - **relatoria-service**
   - **vendedor-service**
   - **pedido-service**
3. **api-gateway**: Inicie o gateway para roteamento das requisições.

### Fluxo de Execução (Imagem)

![image](https://github.com/user-attachments/assets/ff5dc88c-8fc8-40e2-92cf-5becd69f4a4c)


### Swagger (EndPoints)

O projeto está integrado ao **Swagger** para documentação e teste dos endpoints. Acesse a documentação da API em:

[http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)

# Endpoints da API

## Cliente

| Método | Endpoint                                | Descrição                                   |
|--------|-----------------------------------------|---------------------------------------------|
| GET    | `/api/clientes/{id}`                    | Buscar cliente por ID                       |
| PUT    | `/api/clientes/{id}`                    | Atualizar cliente por ID                    |
| DELETE | `/api/clientes/{id}`                    | Deletar cliente por ID                      |
| GET    | `/api/clientes/`                        | Listar todos os clientes                    |
| POST   | `/api/clientes/`                        | Criar um novo cliente                       |
| POST   | `/api/clientes/pagamento/{idCliente}/{idPedido}` | Processar pagamento de um pedido para um cliente |
| GET    | `/api/clientes/rastreio/{idCliente}`    | Obter informações de rastreio do cliente    |
| GET    | `/api/clientes/rastreio/{idCliente}/{idPedido}` | Obter informações de rastreio de um pedido de um cliente |

## Email

| Método | Endpoint            | Descrição                             |
|--------|---------------------|---------------------------------------|
| POST   | `/send-email`        | Enviar um e-mail                      |

## Produto

| Método | Endpoint            | Descrição                             |
|--------|---------------------|---------------------------------------|
| GET    | `/api/produtos/{id}` | Buscar produto por ID                 |
| PUT    | `/api/produtos/{id}` | Atualizar produto por ID              |
| DELETE | `/api/produtos/{id}` | Deletar produto por ID                |
| GET    | `/api/produtos/`     | Listar todos os produtos              |
| POST   | `/api/produtos/`     | Criar um novo produto                 |

## Login

| Método | Endpoint             | Descrição                             |
|--------|----------------------|---------------------------------------|
| POST   | `/auth/register`      | Registrar um novo usuário             |
| POST   | `/auth/login`         | Realizar login                       |

## Pagamento

| Método | Endpoint            | Descrição                             |
|--------|---------------------|---------------------------------------|
| POST   | `/api/pagamentos`    | Processar um pagamento                |

## Pedido

| Método | Endpoint                            | Descrição                                     |
|--------|-------------------------------------|-----------------------------------------------|
| GET    | `/api/pedidos`                      | Listar todos os pedidos                       |
| POST   | `/api/pedidos`                      | Criar um novo pedido                          |
| PATCH  | `/api/pedidos/{id}/status/{novoStatus}` | Atualizar o status de um pedido               |
| GET    | `/api/pedidos/{id}`                 | Buscar pedido por ID                          |

## Relatório

| Método | Endpoint                                | Descrição                                   |
|--------|-----------------------------------------|---------------------------------------------|
| GET    | `/api/relatorios/relatorioVendasPorVendedor` | Relatório de vendas por vendedor             |
| GET    | `/api/relatorios/relatorioVendasPorProduto`  | Relatório de vendas por produto              |
| GET    | `/api/relatorios/relatorioVendasPorPeriodo`  | Relatório de vendas por período              |
| GET    | `/api/relatorios/relatorioPagamentos`        | Relatório de pagamentos                      |

## Vendedor

| Método | Endpoint            | Descrição                             |
|--------|---------------------|---------------------------------------|
| GET    | `/api/vendedores/{id}` | Buscar vendedor por ID                 |
| PUT    | `/api/vendedores/{id}` | Atualizar vendedor por ID              |
| DELETE | `/api/vendedores/{id}` | Deletar vendedor por ID                |
| GET    | `/api/vendedores`     | Listar todos os vendedores             |
| POST   | `/api/vendedores`     | Criar um novo vendedor                 |


### RabbitMQ

O RabbitMQ está configurado com os seguintes exchanges e filas:

#### Exchanges
- **Pedido.exchanges.grupo4** (Direct)

#### Filas
- **Equipe4.email**
- **Equipe4.pagamento**
- **Equipe4.pedido**
- **pedido.queue.grupo4**

## Como Rodar o Projeto

1. Clone o repositório:

```bash
git clone git@github.com:jardeylsonJacinto/Accenture_Project.git
```
2. Selecione o repositório
   
```bash
  cd Accenture_project
```
3. Siga o fluxo de execução supracitado.


## _Nosso Contato_ :mailbox_with_mail:

### _André Fonseca_
[![image](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/andresfonsec)
[![image](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/andresfonsec)
[![image](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](social.andrefonseca@gmail.com)

### _Henrique Jorge_
[![image](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/henrique-jorge-silva-a82a6814a/)
[![image](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/henrique.jorge.dev)
[![image](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](henrique.jorge.dev@gmail.com)

### _Jardeylson Jacinto_
[![image](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/jardeylson-jacinto-769769156)
[![image](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/jardeylsonjacinto/)
[![image](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](jardeylsong.m@gmail.com)




