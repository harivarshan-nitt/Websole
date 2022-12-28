# Websole - WebSockets

Java Websocket Server enables clients to execute shell commands in executors. Clients and Executors are connected to server via websockets.

## Setup - Socket
- Create .env from .env.example inside socket/

```bash
sudo docker-compose -f docker-compose.socket.yml --env-file=socket/.env up
```

## Setup - Executor
- Create .env from .env.example inside executor/

```bash
sudo docker-compose -f docker-compose.executor.yml --env-file=executor/.env up
```