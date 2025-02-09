# Spring Boot API with Docker, SNS, and SQS (LocalStack) 🚀

This repository contains a **Spring Boot API** running inside a **Docker container**, integrated with **AWS SNS and SQS** using **LocalStack**.

This project aims to deploy a **Java 17** + **Spring Boot 3** container that functions as a **REST API** to **receive messages** and **publish** them to an **SNS topic**. Additionally, A separate LocalStack container will run to **simulate AWS SNS and SQS services**, automatically creating the SNS topic, the SQS queue, and subscribing the queue to the topic to receive messages. Finally, within the application container, a consumer service will continuously **poll the SQS queue every 10 seconds**, retrieving and processing any messages that have been published. This setup provides a fully containerized environment for testing event-driven communication with AWS SNS and SQS.

## 📦 Features
- Built with Java 17 and Spring Boot 3.
- Uses AWS SDK v2 for SNS and SQS integration.
- Containerized with Docker.
- Includes Docker Compose for easy setup.
- Uses LocalStack to simulate AWS SNS and SQS.
- Automatically creates SNS topics and SQS queues.

## 🔧 Tech Stack
- Java 17.
- Spring Boot 3.
- AWS SDK v2 (SNS & SQS).
- Docker & Docker Compose.
- LocalStack.
- Maven.

## 🚀 Getting Started

### 📥 **Cloning the Repository**
To get started, first **clone the repository**:

```sh
git clone https://github.com/caaiobomfim/docker-java-spring-boot-sns-sqs-localstack.git
```

### 🔥 Running the Application with Docker Compose
Run the following command to build and start the containers:

```sh
cd docker-java-spring-boot-sns-sqs-localstack
docker-compose up -d --build
```

This will start:
- ✅ LocalStack with SNS and SQS.
- ✅ Spring Boot Application.
- ✅ SNS topic & SQS queue creation.

### 🌍 Testing the API
Once the containers are running, you can test the API.

📌 Check if the application is running:

```sh
curl -X GET http://localhost:8080/actuator/health
```

Expected response:

```sh
{"status":"UP"}
```

📌 Publish a Message to SNS:

```sh
curl -X POST "http://localhost:8080/sns/publish" \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello from SNS!"}'
```

Expected response:

```sh
Message published! ID: 8b537fed-a5ac-48b2-8bc9-3c75cafc36e6
```

### 📜 Viewing Logs

Monitor the Spring Boot Application logs:

```sh
docker logs -f spring-boot-app
```

Expected logs:

- Polling messages from SQS.
- No messages found in SQS queue.
- Publishing message to SNS.
- Message published successfully.
- Received message.
- Message deleted from queue.

Monitor the LocalStack logs:

```sh
docker logs -f localstack
```

Expected logs:

- Waiting for LocalStack to start SNS and SQS.
- Creating SNS topic and SQS queue.
- Subscribing SQS to SNS.
- Configuration completed successfully.
- Ready.