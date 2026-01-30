1. podman build -t spring-app .

1. podman run --name spring-app --network cash-app-network -e "SPRING_PROFILES_ACTIVE=docker" -p 8080:8080 spring-app

