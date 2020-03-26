package a1824jj.jp.ac.aiit.roomdemo_ktx.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SubscriberDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteALL()

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers():LiveData<List<Subscriber>>

}