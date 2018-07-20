package aleksandrkim.sampleapplication.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Aleksandr Kim on 20 Jul, 2018 9:35 PM for SampleApplication
 */
object ServiceProvider {
    const val TAG = "ServiceProvider"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", ApiConstants.key).build()
                chain.proceed(request)
            }
            .build()
    }

    private val retrofitInstance: Retrofit by lazy {
        Log.d(TAG, "retrofit instance init")

        Retrofit.Builder()
            .baseUrl(ApiConstants.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun <S> getService(serviceClass: Class<S>): S {
        return retrofitInstance.create(serviceClass)
    }

}