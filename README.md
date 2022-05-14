# Redis Keyspace Notifications (spring-boot)
 POC para suscribirse a los eventos de redis cuando se realiza una acción. 
 Usando como framework Spring Boot.

### Configuración de Redis

```sh
# Iniciamos redis
$ docker run --name redis -d -p 6379:6379 redis

# Ejecutamos cli de redis
$ docker exec -it redis redis-cli

# Configuramos redis de manera temporal para que solo envíe eventos del tipo 'set'
127.0.0.1:6379> CONFIG SET notify-keyspace-events E$

# Si queremos ver que valores tienen actualmente la config
127.0.0.1:6379> CONFIG GET notify-keyspace-events
```
### Configuración de proyecto (application.properties)

Este pattern es el que vamos a usar como tópico para suscribirnos a los eventos `set`. 
Podemos dejar como eventos `*` y asi recibir todos los eventos `__keyevent@0__:*`.
La base está identificada en este caso con `0`.

```properties
spring.redis.listen-pattern=__keyevent@0__:set
```

### Ejecutar pruebas

* Iniciar proyecto
* Setear un valor en redis. Se puede hacer de dos formas, por rest en el proyecto o directamente en redis.
  * Rest POST http://localhost:8080/redis
    ```json
    {
     "key": "user",
     "value": "John Doe"
    }
    ```
  * Redis
    ```sh
    127.0.0.1:6379> set user "John Doe"
    ```
* Una vez realizada cualquiera de las dos opciones nos va a llegar un evento en el **MessageListener** de que tenemos  
  configurado en el proyecto. Ejemplo:
  ```
  INFO i.k.r.config.RedisMessageListener  : Event key: user
  INFO i.k.r.config.RedisMessageListener  : Event pattern config: __keyevent@0__:set
  INFO i.k.r.config.RedisMessageListener  : Event channel: __keyevent@0__:set
  ```


### Links de referencia

* [Articulo con ejemplo de configuraciones de redis](https://www.codetd.com/en/article/12599498)
* [Documentación oficial redis sobre notificaciones](https://redis.io/docs/manual/keyspace-notifications/)