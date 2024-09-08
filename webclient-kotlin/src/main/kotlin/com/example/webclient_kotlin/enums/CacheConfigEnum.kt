package com.example.webclient_kotlin.enums

import java.time.Duration

enum class CacheConfigEnum(
    val cacheName: String,
    val ttl: Duration,
) {
    NOT_CREATE_CACHE(cacheName = "NotCreateCache", ttl = Duration.ofMinutes(0)),
    TEN_SECOND_CACHE_NAME(cacheName = "10SecondTTLCache", ttl = Duration.ofSeconds(10)),
    ONE_MINUTE_TTL_CACHE(cacheName = "1MinuteTTLCache", ttl = Duration.ofMinutes(1)),
    THREE_MINUTE_TTL_CACHE(cacheName = "3MinuteTTLCache", ttl = Duration.ofMinutes(3)),
    ETERNAL_CACHE(cacheName = "EternalCache", ttl = Duration.ZERO)
    ;
}
