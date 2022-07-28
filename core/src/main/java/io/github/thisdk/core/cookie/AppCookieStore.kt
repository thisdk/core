package io.github.thisdk.core.cookie

import io.github.thisdk.core.ds.AppDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Cookie
import okhttp3.HttpUrl

class AppCookieStore(private val appDataStore: AppDataStore) {

    fun save(url: HttpUrl, cookies: List<Cookie>) {
        runBlocking(Dispatchers.IO) {
            val keySet = cookies.asFlow()
                .filter { it.expiresAt > System.currentTimeMillis() }
                .map {
                    AppCookie(
                        name = it.name,
                        value = it.value,
                        expiresAt = it.expiresAt,
                        domain = it.domain,
                        path = it.path,
                        secure = it.secure,
                        httpOnly = it.httpOnly,
                        persistent = it.persistent,
                        hostOnly = it.hostOnly
                    )
                }.onEach {
                    appDataStore.putString("${it.name}@${url.host}", Json.encodeToString(it))
                }.map {
                    it.name
                }.toSet()
            appDataStore.putStringSet(url.host, keySet)
        }
    }

    fun load(url: HttpUrl): List<Cookie> {
        return appDataStore.getStringSet4Sync(url.host)
            .asSequence()
            .mapNotNull { appDataStore.getString4Sync("${it}@${url.host}") }
            .map { Json.decodeFromString(AppCookie.serializer(), it) }
            .filter { it.expiresAt > System.currentTimeMillis() }
            .map {
                val builder = Cookie.Builder()
                builder.name(it.name)
                builder.value(it.value)
                builder.expiresAt(it.expiresAt)
                builder.domain(it.domain)
                builder.path(it.path)
                if (it.secure) {
                    builder.secure()
                }
                if (it.hostOnly) {
                    builder.hostOnlyDomain(it.domain)
                } else {
                    builder.domain(it.domain)
                }
                if (it.httpOnly) {
                    builder.httpOnly()
                }
                builder.build()
            }.toMutableList()
    }


}