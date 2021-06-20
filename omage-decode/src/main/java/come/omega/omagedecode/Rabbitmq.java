package come.omega.omagedecode;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;



@Configuration
@Component
@EnableConfigurationProperties(RabbitmqItem.class)
public class Rabbitmq {
    @Autowired
    public RabbitmqItem rabbitmqItem;
    @Bean
    public Channel channel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqItem.host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(rabbitmqItem.queueName, false, false, false, null);
        return channel;
    }

    @Autowired
    private Channel channel;
    public String getInfo() throws IOException, InterruptedException {
        while (true) {
            GetResponse response = channel.basicGet("encode", true);
            if (response != null) {
                byte[] body = response.getBody();
                return new String(body, StandardCharsets.UTF_8);
            }
            Thread.sleep(200);
        }
    }
}