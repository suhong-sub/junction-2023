package com.example.security.jwt.global.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${spring.data.redis.port}")
    public int port;

    @Value("${spring.data.redis.host}")
    public String host;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

/*
RedisTemplate 사용 타입
//        opsForValue()	ValueOperations	Strings를 쉽게 Serialize / Deserialize 해주는 Interface
//        opsForList()	ListOperations	List를 쉽게 Serialize / Deserialize 해주는 Interface
//        opsForSet()	SetOperations	Set를 쉽게 Serialize / Deserialize 해주는 Interface
//        opsForZSet()	ZSetOperations	ZSet를 쉽게 Serialize / Deserialize 해주는 Interface
//        opsForHash()	HashOperations	Hash를 쉽게 Serialize / Deserialize 해주는 Interface

        // Redis와 연결
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        // 데이터 추가 시 set
        valueOperations.set("id",userDAO.getID());
        valueOperations.set("email",userDAO.getEmail());
        valueOperations.set("password",userDAO.getPassword());
        valueOperations.set("name",userDAO.getName());
        valueOperations.set("code",userDAO.getCode());

        // 데이터 출력 시 get
        return new GetUserInfoResponseDTO(
                Long.parseLong(String.valueOf(valueOperations.get("id"))),
                String.valueOf(valueOperations.get("email")),
                String.valueOf(valueOperations.get("password")),
                String.valueOf(valueOperations.get("name")),
                String.valueOf(valueOperations.get("code"))
        );

 */