package caaiobomfim.producer;

import caaiobomfim.core.LocalStackProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class ProducerConfiguration {

    private final LocalStackProperties localStackProperties;

    public ProducerConfiguration(LocalStackProperties localStackProperties) {
        this.localStackProperties = localStackProperties;
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.of(localStackProperties.getRegion()))
                .endpointOverride(URI.create(localStackProperties.getLocalstackUrl()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")
                ))
                .build();
    }
}
