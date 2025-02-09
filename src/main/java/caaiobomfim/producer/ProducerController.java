package caaiobomfim.producer;

import caaiobomfim.core.LocalStackProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@RestController
@RequestMapping("/sns")
public class ProducerController {

    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private final SnsClient snsClient;
    private final LocalStackProperties localStackProperties;

    public ProducerController(LocalStackProperties localStackProperties, SnsClient snsClient) {
        this.localStackProperties = localStackProperties;
        this.snsClient = snsClient;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody String message) {
        logger.info("Publishing message to SNS: {}", message);

        PublishResponse response = snsClient.publish(PublishRequest.builder()
                .topicArn(localStackProperties.getSnsTopicArn())
                .message(message)
                .build());

        logger.info("Message published successfully. MessageId: {}", response.messageId());

        return ResponseEntity.ok("Message published! ID: " + response.messageId());
    }
}