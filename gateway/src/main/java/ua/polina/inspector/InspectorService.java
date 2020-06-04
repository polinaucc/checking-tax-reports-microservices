package ua.polina.inspector;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.polina.client.Client;

import java.util.List;

@FeignClient(value = "inspector")
public interface InspectorService {
    @ResponseBody
    @PostMapping("admin/save-inspector")
    void saveInspector(@RequestBody ApiInspector apiInspector);

    @ResponseBody
    @PostMapping("/inspectors")
    List<Inspector> getInspectors();

    @ResponseBody
    @PostMapping("/find-by-id")
    Inspector getByInspectorById(@RequestBody Long id);

    @ResponseBody
    @PostMapping("/find-by-user-id")
    Inspector getInspectorByUserId(@RequestBody Long userId);

}


