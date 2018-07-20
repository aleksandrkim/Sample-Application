package aleksandrkim.sampleapplication.network

import aleksandrkim.sampleapplication.db.models.TopHeadline
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Aleksandr Kim on 20 Jul, 2018 8:41 PM for SampleApplication
 */
interface TopHeadlinesApi {

    @GET("top-headlines")
    fun getBy(@Query("country") country: String, @Query("category") category: String, @Query("q") phrase: String): Single<TopHeadline>

    @GET("top-headlines")
    fun getAll(@Query("language") language: String): Single<TopHeadline>

}