package org.tangdao.config;

import java.io.Serializable;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableCaching
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为60秒
@EnableRedisHttpSession
public class RedisConfig {

	@Primary
	@Bean("redisTemplate")
	public RedisTemplate<String, Serializable> redisTemplate(
			RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setDefaultSerializer(this.redisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	public RedisSerializer<Object> redisSerializer() {
		return new RedisSerializer<Object>() {

			// 为了方便进行对象与字节数组的转换，所以应该首先准备出两个转换器
			private Converter<Object, byte[]> serializingConverter = new SerializingConverter();
			private Converter<byte[], Object> deserializingConverter = new DeserializingConverter();

			@Override
			public byte[] serialize(Object obj) throws SerializationException {
				if (obj == null) { // 这个时候没有要序列化的对象出现，所以返回的字节数组应该就是一个空数组
					return new byte[0];
				}
				return this.serializingConverter.convert(obj); // 将对象变为字节数组
			}

			@Override
			public Object deserialize(byte[] data) throws SerializationException {
				if (data == null || data.length == 0) { // 此时没有对象的内容信息
					return null;
				}
				return this.deserializingConverter.convert(data);
			}
		};
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		return container;
	}
	
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}
	
}