package social.tangent.mobile.util

import co.touchlab.stately.concurrency.Lock

fun <K, V> safeCacheOf(creator: (key: K) -> V) = SafeCache(creator)

class SafeCache<K, V>(
    val creator: (key: K) -> V,
    private val map: MutableMap<K, V> = mutableMapOf()
) : Map<K, V> by map {

    private val lock = Lock()

    override fun get(key: K): V {
        var result = map[key]
        if (result == null) {
            lock.lock()
            result = map[key]
            if (result == null) {
                result = creator(key)
                map[key] = result
            }
        }
        return result!!
    }
}