package ua.polina.report_renoucement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterObj {
    public Boolean accepted;
    public Boolean rejected;
    public Boolean notChecked;

}
