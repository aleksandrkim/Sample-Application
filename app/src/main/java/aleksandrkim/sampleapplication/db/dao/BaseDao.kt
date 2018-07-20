package aleksandrkim.sampleapplication.db.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.FAIL
import android.arch.persistence.room.OnConflictStrategy.IGNORE

/**
 * Created by Aleksandr Kim on 21 Jul, 2018 12:14 AM for SampleApplication
 */
@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = FAIL)
    abstract fun add(t: T) : Long

    @Insert(onConflict = IGNORE)
    abstract fun add(vararg t: T) : LongArray

    @Update(onConflict = IGNORE)
    abstract fun update(t: T)

    @Transaction
    open fun addOrUpdate(t: T) {
        add(t)
        update(t)
    }

    @Delete
    abstract fun delete(t: T)

    open fun add(t: List<T>) {
        for (i in t) add(i)
    }

    abstract fun count() : Int
}
