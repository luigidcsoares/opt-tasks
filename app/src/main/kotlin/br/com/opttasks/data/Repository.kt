package br.com.opttasks.data

import br.com.opttasks.APIService

object Repository {

    suspend fun save(simulation: Simulation): Simulation =
            APIService.retrofit.saveAsync(simulation).await().let {
                android.util.Log.d("TESTE", "UIAHSUIE")
                it
            }
}