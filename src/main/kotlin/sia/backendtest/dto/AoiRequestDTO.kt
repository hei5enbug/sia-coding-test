package sia.backendtest.dto


data class AoiRequestDTO(
    override val name: String,
    override val area: List<PointDTO>
) : AreaRequestDTO(name, area)

