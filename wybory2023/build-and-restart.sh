docker build -t Wybory2023 .
docker stop Wyboru2023 || true
docker rm Wybory2023 || true
docker run -d -p 8080:8080 --name Wybory2023 Wybory2023
