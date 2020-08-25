package ua.polina.client;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ua.polina.auth.ApiRequest;
import ua.polina.claim.ApiChangeInspector;

import java.util.List;

@FeignClient(value = "client")
public interface IndividualService {

    @GetMapping("admin/individuals")
    @ResponseBody
    List<Individual> getAllIndividuals();

    @GetMapping("admin/delete-individual/{id}")
    @ResponseBody
    void deleteIndividual(@PathVariable("id") Long id);

    @GetMapping("admin/edit-individual/{id}")
    @ResponseBody
    Individual getUpdateForm(@PathVariable("id") Long id);

    @PostMapping("admin/update-individual/{id}")
    @ResponseBody
    void updateIndividual(@PathVariable("id") Long id, @RequestBody Individual individual);

    @PostMapping("/register-individual")
    @ResponseBody
    Individual registerIndividual(@RequestBody ApiRequest request);

    @PostMapping("/get-current-client")
    @ResponseBody
    Long getCurrentClient(@RequestBody Long userId);

    @PostMapping("/get-inspector-by-client")
    @ResponseBody
    Long getInspectorByClient(@RequestBody Long clienId);

    @GetMapping("admin/legal-entities")
    @ResponseBody
    List<LegalEntity> getAllEntities();

    @GetMapping("admin/delete-legal-entity/{id}")
    @ResponseBody
    void deleteLegalEntity(@PathVariable("id") Long id);

    @PostMapping("/register-legal-entity")
    @ResponseBody
    LegalEntity registerLegal(@RequestBody ApiRequest<SignUpLegalEntityDto> apiRequest);

    @PostMapping("/accept-claim")
    @ResponseBody
    void acceptClaim(@RequestBody ApiChangeInspector apiChangeInspector);

    @PostMapping("/get-be-id")
    @ResponseBody
    Client getClientById(@RequestBody Long id);

    @PostMapping
    @ResponseBody
    List<Individual> getIndividualsByInspector(@RequestBody Long inspectorId);

    @PostMapping("/insp-legals")
    @ResponseBody
    List<LegalEntity> getLegalsByInspector(@RequestBody Long inspectorId);

    @PostMapping("/insp-clients")
    @ResponseBody
    List<Client> getClientByInspector(@RequestBody Long inspectorId);

    @PostMapping("/get-individual-by-client-id")
    @ResponseBody
    Individual getByClientId(@RequestBody Long clientId);

    @PostMapping("/get-legal-by-client-id")
    @ResponseBody
    LegalEntity geLegaltByClientId(@RequestBody Long clientId);
}
