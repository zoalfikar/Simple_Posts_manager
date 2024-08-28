package simple_posts_manager.app.helpers;

import org.springframework.cache.interceptor.KeyGenerator;

public class PostCacheKey implements KeyGenerator {

    @Override
    public String generate(Object target, java.lang.reflect.Method method, Object... params) {
        return "";
    }
}