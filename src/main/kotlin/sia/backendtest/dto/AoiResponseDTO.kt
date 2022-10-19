package sia.backendtest.dto


data class AoiResponseDTO(
    val id: Int,
    val name: String,
    val area: List<PointDTO>
)

