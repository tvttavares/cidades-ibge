# Desafio Evoluum

API de desafio que consulta 2 endpoints do IBGE e retorna seus dados concatenados de estado/municipios em formato JSON e CSV.

## Endpoints

### /api/localizacao/todos/json
Endpoint que retorna todos os dados dos municipios/estados pelo endpoint conforme documentação. Retorno do servico em um arquivo em formato JSON.

### /api/localizacao/todos/csv
Endpoint que retorna todos os dados dos municipios/estados pelo endpoint conforme documentação. Retorno do servico em um arquivo em formato CSV para download.


### /api/localizacao/municipio/{nomeCidade}
Endpoint que retorna o id da cidade com base em seu nome e sua resposta eh armazenada em cache.

## Swagger
```
http://127.0.0.1:8080/api/swagger-ui.html
```

## Tecnologias
* Spring boot 
* Spring web para implementação da API
* Jackson Dataformat CSV para gerar csv
* Spring cache para implementar Cache
* Springfox Swagger para documentação e testes da API 
* Spring cloud Netflix Hystrix para implementar Circuit Breaker


Aproveite! 
