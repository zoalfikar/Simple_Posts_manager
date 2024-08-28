package simple_posts_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.Collections;

@Configuration
public class RedisConfig {
    @Value("${app.root.URL}")
    private  String rootURL;

    @Value("${spring.redis.port}")
    private  Integer port;

    @Bean
    LettuceConnectionFactory connectionFactory() {
      return new LettuceConnectionFactory();
    }
  
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
      RedisTemplate<String, Object> template = new RedisTemplate<>();
      template.setKeySerializer(new StringRedisSerializer());
      template.setConnectionFactory(connectionFactory);
      return template;
    }

}