package br.com.opttasks.data

import com.google.gson.annotations.SerializedName

data class Simulation(
    val name: String,
    val tasks: List<Task>,
    val students: List<Student>,
    val allocations: List<Allocation>? = null,
    val Z: Int? = null,
    val status: String? = null
)

data class Allocation(
    val student: String,
    val tasks: List<String>
)

data class SimulationResume(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val Z: Int,
    val status: String
)
