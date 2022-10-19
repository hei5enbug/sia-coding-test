package sia.backendtest.dto


abstract class AreaRequestDTO(
    open val name: String,
    open val area: List<PointDTO>
)

