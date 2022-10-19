package sia.backendtest.dto


data class RegionRequestDTO(
    override val name: String,
    override val area: List<PointDTO>
) : AreaRequestDTO(name, area)

