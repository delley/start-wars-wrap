# Desafio Star Wars API

Este projeto foi criado como solução para o seguinte desafio:

>Nossos associados são aficionados por Star Wars e com isso, queremos crar um jogo com algumas informações da franquia.
>
>Para possibilitar a equipe de front criar essa aplicação, queremos desenvolver uma API que contenha os dados dos planetas, que podem ser obtidos pela API pública do Star Wars (https://swapi.co/)
>
>#### Requisitos:
>
>- A API deve ser REST
>- Para cada planeta, os seguintes dados devem ser obtidos do banco de dados da aplicação, que foram inseridos manualmente pela funcionalidade de adicionar planetas: 
>   - **Nome**;
>   - **Clima**;
>   - **Terreno**
>   - e para cada planeta também devemos ter a **quantidade de aparições** em filmes que deve ser obtida pela api do Star Wars na inserção do planeta.
>
>#### Funcionalidades desejadas:
> 
>- [x] Adicionar um planeta (com nome, clima e terreno)
>- [x] Listar planetas do banco de dados
>- [x] Listar planetas da API do Star Wars
>- [x] Buscar por nome no banco de dados
>- [x] Buscar por ID no banco de dados
>- [x] Remover planeta

## Tecnologias

Para o desenvolvimento da solução, foram utilizadas as seguintes tecnologias:

+ Kotlin
+ Sprint Boot 2
+ Spring WebFlux
+ Cassandra Database
+ Docker
+ IntelliJ IDEA 2018.3.5 (Community Edition)

## Passos para iniciar e testar a aplicação

1. Inicie um container com o banco de dados Cassandra:

```shell
$ docker container run --name cassandra-db -d -p 9042:9042 cassandra
```

2. Compile e inicie a aplicação:

```shell
$ mvn clean package
$ java -jar target/starwars-0.0.1-SNAPSHOT.jar
```
ou, simplesmente: 

```shell
$ mvn spring-boot:run
```

3. Para testar a funcionalidade **Adicionar um planeta**, execute o seguinte comando:

```shell
$ curl -i http://localhost:8080/planet \
  -H 'content-type: application/json' \
  -d '{
  "name":"tatooine",
  "climate":"temperate, tropical",
  "terrain":"jungle, rainforests"
}'
```

4. Para testar a funcionalidade **Listar planetas do banco de dados**, execute o seguinte comando:

```shell
$ curl -i -H "Accept: text/event-stream" http://localhost:8080/planet
```

5. Parat testar a funcionalidade **Listar planetas da API do Star Wars**, execute o seguinte comando:

```shell
$ curl -i -H "Accept: application/stream+json" http://localhost:8080/swapi/1
```

6. Para testar a funcionalidade **Buscar por nome no banco de dados**, execute o seguinte comando:

```shell
$ curl -i -H "Accept: application/event-stream" http://localhost:8080/planet/?nameFilter=oo
```

7. Para testar a funcionalidade **Buscar por ID no banco de dados**, execute o seguinte comando:

```shell
$ curl -i -H "Accept: application/event-stream" http://localhost:8080/planet/ea605ad2-bed7-482b-8d1d-a5717f522dc5
```

*onde `ea605ad2-bed7-482b-8d1d-a5717f522dc5` é o id que deve ser obtido por meio da funcionalidade **Listar planetas da API do Star Wars** ou **Buscar por nome no banco de dados***

8. Para testar a funcionalidade **Remover planeta**, execute o seguinte comando:

```shell
$ curl -i -X DELETE http://localhost:8080/planet/ea605ad2-bed7-482b-8d1d-a5717f522dc5
```
*onde `ea605ad2-bed7-482b-8d1d-a5717f522dc5` é o id que deve ser obtido por meio da funcionalidade **Listar planetas da API do Star Wars** ou **Buscar por nome no banco de dados***

---
