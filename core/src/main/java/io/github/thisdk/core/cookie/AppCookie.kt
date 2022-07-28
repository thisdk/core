package io.github.thisdk.core.cookie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AppCookie(
    val name: String,
    val value: String,
    val expiresAt: Long,
    val domain: String,
    val path: String,
    val secure: Boolean,
    val httpOnly: Boolean,
    val persistent: Boolean,
    val hostOnly: Boolean
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppCookie

        if (name != other.name) return false
        if (value != other.value) return false
        if (expiresAt != other.expiresAt) return false
        if (domain != other.domain) return false
        if (path != other.path) return false
        if (secure != other.secure) return false
        if (httpOnly != other.httpOnly) return false
        if (persistent != other.persistent) return false
        if (hostOnly != other.hostOnly) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + expiresAt.hashCode()
        result = 31 * result + domain.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + secure.hashCode()
        result = 31 * result + httpOnly.hashCode()
        result = 31 * result + persistent.hashCode()
        result = 31 * result + hostOnly.hashCode()
        return result
    }
}
