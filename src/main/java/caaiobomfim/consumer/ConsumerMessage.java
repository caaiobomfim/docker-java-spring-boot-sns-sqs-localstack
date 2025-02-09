package caaiobomfim.consumer;

import caaiobomfim.core.LocalStackProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@Component
public class ConsumerMessage {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerMessage.class);

    private final SqsClient sqsClient;
    private final LocalStackProperties localStackProperties;

    public ConsumerMessage(LocalStackProperties localStackProperties, SqsClient sqsClient) {
        this.localStackProperties = localStackProperties;
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedRate = 10000)
    public void pollMessages() {
        logger.info("Polling messages from SQS...");

        List<Message> messages = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(localStackProperties.getSqsQueueUrl())
                .maxNumberOfMessages(5)
                .waitTimeSeconds(2)
                .visibilityTimeout(5)
                .build()).messages();

        if (messages.isEmpty()) {
            logger.info("No messages found in SQS queue.");
            return;
        }

        for (Message message : messages) {
            logger.info("📩 Received message: {}", message.body());

            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(localStackProperties.getSqsQueueUrl())
                    .receiptHandle(message.receiptHandle())
                    .build());

            logger.info("🗑️ Message deleted from queue: {}", message.messageId());
        }
    }
}