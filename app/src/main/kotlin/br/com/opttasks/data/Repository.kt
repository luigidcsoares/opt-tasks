package br.com.opttasks.data

import androidx.lifecycle.MutableLiveData
import br.com.opttasks.APIService

object Repository {

    suspend fun resumes() =
        APIService.retrofit.resumesAsync().await().let {
            MutableLiveData<List<SimulationResume>>().apply { value = it }
        }

    suspend fun detail(id: String) =
        APIService.retrofit.detailAsync(id).await().let {
            MutableLiveData<Simulation>().apply { value = it }
        }

    suspend fun save(simulation: Simulation): Simulation =
            APIService.retrofit.saveAsync(simulation).await()
}