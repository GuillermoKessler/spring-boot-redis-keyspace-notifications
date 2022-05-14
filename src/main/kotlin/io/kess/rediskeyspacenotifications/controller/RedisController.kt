package io.kess.rediskeyspacenotifications.controller

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis")
class RedisController(
    private val redisTemplate: StringRedisTemplate
) {

    @PostMapping
    fun setExpiredVal(@RequestBody body: Body): ResponseEntity<String> {

        redisTemplate.opsForValue().set(body.key, body.value)

        return ResponseEntity.ok().body("Value set OK")
    }
}

data class Body(
    val key: String,
    val value: String
)
