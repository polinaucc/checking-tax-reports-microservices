package ua.polina.inspector;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiInspector {
    InspectorDto inspectorDto;
    Long userId;
}
