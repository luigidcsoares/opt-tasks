package br.com.opttasks

import br.com.opttasks.data.Simulation
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("simulations/")
    fun saveAsync(@Body body: Simulation): Deferred<Simulation>

    // A singleton object for Enterprise Retrofit service.
    companion object {
        private const val BASE_URL = "http://opttasks.herokuapp.com/api/"
        val retrofit: APIService by lazy { create() }

        private fun create() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APIService::class.java)
    }
}