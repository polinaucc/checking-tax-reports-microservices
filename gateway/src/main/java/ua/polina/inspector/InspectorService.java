package ua.polina.inspector;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
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

    @ResponseBody
    @PostMapping("/delete/{id}")
    void deleteById(@PathVariable("id") Long id);
}


