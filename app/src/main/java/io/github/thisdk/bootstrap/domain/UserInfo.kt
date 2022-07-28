package io.github.thisdk.bootstrap.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(indices = [Index(value = ["name"], unique = true)])
data class UserInfo(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val age: Int
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfo

        if (uid != other.uid) return false
        if (name != other.name) return false
        if (age != other.age) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid
        result = 31 * result + name.hashCode()
        result = 31 * result + age
        return result
    }

}
