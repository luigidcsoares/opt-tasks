package br.com.opttasks.data

data class Simulation(
    val name: String,
    val tasks: List<Task>,
    val students: List<Student>,
    val allocations: List<Allocation>? = null,
    val Z: Int? = null
)

data class Allocation(
    val student: String,
    val tasks: List<String>
)
