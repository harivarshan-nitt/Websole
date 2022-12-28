# Websole

MERN Stack Application which renders CLI UI template, enabling clients to execute shell commands in the server.

## Setup
- Clone this repo
- Create .env from .env.example inside express-app/

```bash
sudo docker-compose --env-file=express-app/.env up
```


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

## Screenshots

![alt text](https://drive.google.com/uc?export=view&id=1rFVu1QKWj-ykmezRQcnmE_H3qGlpUBdT)

![alt text](https://drive.google.com/uc?export=view&id=1jzDR3UcRQquro-aJaWlhhx-XA_e-wJiW)
