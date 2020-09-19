# fit-qa-gateway
Integrar seus sistemas internos com API's de parceiros é uma necessidade constante e crescente, e testar a resiliência destas integrações se torna cada vez mais um desafio. Pensando neste cenário, vou demonstrar como utilizar o Zuul Proxy para simular falhas nas integrações de maneira controlada e viabilizar cenários de teste em que hajam falhas específicas nas integrações.

### Antes de rodar suba uma instancia do redis localmente
docker run --name redis -p 6379:6379 -d redis
