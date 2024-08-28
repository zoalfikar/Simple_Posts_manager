package simple_posts_manager.app.services;

import org.springframework.stereotype.Service;

import simple_posts_manager.models.entities.Post;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
@Service
public class PostCacheService {
    final String pattern = "post::";
    final Integer postTime = 10;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void push(Post post) {
        redisTemplate.opsForValue().set(pattern+post.getId(), post,postTime,TimeUnit.MINUTES);
    }

    public Object retrive(Long id) {
       return redisTemplate.opsForValue().get(pattern+id.toString());
    }
}