# LogiTrack-EBAC
# LogiTrack - Sistema de Gestão de Logística

## Serviços
| Serviço              | Porta | Banco       |
|----------------------|-------|-------------|
| auth-service         | 8081  | MySQL       |
| user-service         | 8082  | MySQL       |
| order-service        | 8083  | MySQL       |
| delivery-service     | 8084  | MySQL       |
| tracking-service     | 8085  | MongoDB     |
| notification-service | 8086  | -           |

## Como rodar
```bash
docker-compose up --build
```

## Swagger
- Auth:     http://localhost:8081/swagger-ui.html
- Orders:   http://localhost:8083/swagger-ui.html
- Delivery: http://localhost:8084/swagger-ui.html
- Tracking: http://localhost:8085/swagger-ui.html

## Tópicos Kafka
- order-created
- delivery-assigned
- delivery-updated
- delivery-completed
