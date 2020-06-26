# Prova Finale Ingegneria del Software 2020

## Gruppo GC44
- Feng Yang ([@fengyang98](https://github.com/fengyang98))
- Zheng Maria Yu ([@Trixyz28](https://github.com/Trixyz28))
- Linda Zhu ([@lnd24](https://github.com/lnd24))

## Funzionalità implementate
| Requisiti | Stato |
|:-----------------------|:------------------------------------:|
| Regole Complete | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Partite Multiple | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Divinità Avanzate | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistenza | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

## Requisiti per l'avvio
Si richiede l'uso di Java 14.


## Creare .jar
I file eseguibili del Server e del Client possono essere creati con Maven Shade Plugin tramite il comando
```sh
mvn clean package
```

## Eseguire il programma

### Server
```sh
java -jar Server.jar [porta]
```
In assenza di parametro riguardante la porta, si ha il numero di porta di default 45000.

### Client

Client.jar può essere lanciato direttamente con il doppio click.
In alternativa è possibile eseguire da terminale
```sh
java -jar Client.jar [parametro]
```
Se il parametro viene lasciato vuoto si lancia la GUI, mentre compilato con un parametro esegue la CLI.






