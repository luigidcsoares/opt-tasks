package br.com.opttasks

import br.com.opttasks.data.Simulation
import br.com.opttasks.data.SimulationResume
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @GET("simulations/")
    fun resumesAsync(): Deferred<List<SimulationResume>>

    @GET("simulations/{id}")
    fun detailAsync(@Path("id") id: String): Deferred<Simulation>

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