# Gerenciamento de Estoque - Projeto Bom Carro

Este projeto foi desenvolvido como parte do trabalho da disciplina de Programação II do curso de Ciência da Computação, no terceiro semestre. O objetivo era criar um software para gerenciamento de estoque de carros usados em uma revenda.

## Funcionalidades

O software possui as seguintes funcionalidades:

1. **Base de Dados**:
   - Utiliza o PostgreSQL como SGBD.
   - Composta por duas tabelas:
     - `Marca`: contém o nome da marca e seu país de origem.
     - `Veiculo`: contém o nome do modelo, ano de fabricação, quilometragem rodada, valor e vínculo com a marca.

2. **Script SQL para Criação das Tabelas**:
   - Antes de executar a aplicação, é necessário rodar o script SQL fornecido (`bom-carro.sql`) para criar as tabelas no banco de dados.

3. **Interfaces Gráficas**:
   - Tela de inserção de marcas.
   - Tela de inserção de veículos.
   - Tela de edição de marcas.
   - Tela de edição de veículos.
   - Tela de listagem de marcas.
   - Tela de listagem de veículos (tela inicial).
   - Tela de listagem simplificada.

## Tecnologias Utilizadas

- Java Swing para implementação das interfaces gráficas.
- PostgreSQL como SGBD.

## Como Executar

1. Certifique-se de ter o PostgreSQL instalado e configurado.
2. Clone este repositório.
3. Use o script SQL (`bom-carro.sql`) para criar as tabelas no banco de dados.
4. Abra o projeto em uma IDE que suporte Java.
5. Execute a classe principal (`TelaPrincipal.java`) para iniciar o aplicativo.
