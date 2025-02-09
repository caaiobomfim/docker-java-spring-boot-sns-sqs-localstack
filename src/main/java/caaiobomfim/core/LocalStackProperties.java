package caaiobomfim.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties
public class LocalStackProperties {

    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    @Value("${aws.sqs.queue.url}")
    private String sqsQueueUrl;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.localstack.url}")
    private String localstackUrl;
}
