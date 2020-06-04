package ua.polina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.dto.ApiInspector;
import ua.polina.dto.InspectorDto;
import ua.polina.entity.Inspector;
import ua.polina.service.InspectorService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class InspectorController {
    @Autowired
    InspectorService inspectorService;

    @ResponseBody
    @PostMapping("admin/save-inspector")
    public void saveInspector(@RequestBody ApiInspector apiInspector) {
        inspectorService.saveNewInspector(apiInspector.getInspectorDto(), apiInspector.getUserId());
    }

    @ResponseBody
    @PostMapping("/inspectors")
    public List<Inspector> getInspectors(){
        return inspectorService.getAllInspectors();
    }

    @ResponseBody
    @PostMapping("/find-by-id")
    public Inspector getByInspectorById(@RequestBody Long id){
        return inspectorService.getById(id)
                .orElseThrow(()->new IllegalArgumentException("No such inspector"));
    }

    @ResponseBody
    @PostMapping("/find-by-user-id")
    public Inspector getInspectorByUserId(@RequestBody Long userId){
        return inspectorService.getByUserId(userId);
    }

}
