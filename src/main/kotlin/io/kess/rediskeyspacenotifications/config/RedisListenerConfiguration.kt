package io.kess.rediskeyspacenotifications.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.Topic

@Configuration
class RedisListenerConfiguration {

    @Value("\${spring.redis.listen-pattern}")
    private lateinit var pattern: String

    @Bean
    fun listenerContainer(redisConnection: RedisConnectionFactory): RedisMessageListenerContainer {

        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnection)
        val topic: Topic = PatternTopic(pattern)
        container.addMessageListener(RedisMessageListener(), topic)

        return container
    }
}

class RedisMessageListener : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val body: ByteArray = message.body
        val channel: ByteArray = message.channel
        val key = String(body)

        logger().info("Event key: {}", key)
        logger().info("Event pattern config: {}", pattern?.let { String(it) })
        logger().info("Event channel: {}", String(channel))
        // TODO: con la key recuperar el valor y hacer lo necesario
    }
}
