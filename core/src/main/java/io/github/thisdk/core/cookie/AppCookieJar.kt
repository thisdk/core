package io.github.thisdk.core.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class AppCookieJar(private val appCookieStore: AppCookieStore) : CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return appCookieStore.load(url)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        appCookieStore.save(url, cookies)
    }

}