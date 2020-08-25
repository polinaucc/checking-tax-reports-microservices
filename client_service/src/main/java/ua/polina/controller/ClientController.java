package ua.polina.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.polina.dto.ApiChangeInspector;
import ua.polina.dto.ApiRequest;
import ua.polina.dto.SignUpIndividualDto;
import ua.polina.dto.SignUpLegalEntityDto;
import ua.polina.entity.Client;
import ua.polina.entity.Individual;
import ua.polina.entity.LegalEntity;
import ua.polina.service.ClientService;
import ua.polina.service.IndividualService;
import ua.polina.service.LegalEntityService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class ClientController {
    @Autowired
    IndividualService individualService;

    @Autowired
    ClientService clientService;

    @Autowired
    LegalEntityService legalEntityService;

    @GetMapping("admin/individuals")
    @ResponseBody
    public List<Individual> getAllIndividuals() {
        return individualService.getAllIndividuals();
    }

    @GetMapping("admin/delete-individual/{id}")
    @ResponseBody
    public void deleteIndividual(@PathVariable("id") Long id) {
        System.out.println("OK");
        individualService.deleteById(id);
    }

    @GetMapping("admin/edit-individual/{id}")
    @ResponseBody
    public Individual getUpdateForm(@PathVariable("id") Long id) {
        return individualService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id" + id));

    }

    @PostMapping("admin/update-individual/{id}")
    @ResponseBody
    public void updateIndividual(@PathVariable("id") Long id, @RequestBody Individual individual) {
        System.out.println(individual.getLastName());
        System.out.println("tut");
        individualService.update(individual);
    }

    @PostMapping("/register-individual")
    @ResponseBody
    public Individual registerIndividual(@RequestBody ApiRequest<SignUpIndividualDto> apiRequest) {
        return individualService.saveNewIndividual(apiRequest.getDto(), apiRequest.getUserId());
    }

    @GetMapping("admin/legal-entities")
    @ResponseBody
    public List<LegalEntity> getAllEntities() {
        return legalEntityService.getAllLegalEntities();
    }

    @GetMapping("admin/delete-legal-entity/{id}")
    @ResponseBody
    public void deleteLegalEntity(@PathVariable("id") Long id) {
        legalEntityService.deleteById(id);
    }

    @GetMapping("admin/edit-legal-entity/{id}")
    @ResponseBody
    public LegalEntity getUpdateFormLegal(@PathVariable("id") Long id) {
        return legalEntityService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id" + id));
    }

    @PostMapping("admin/update-legal-entity/{id}")
    @ResponseBody
    public void updateLegal(@PathVariable("id") Long id, @RequestBody LegalEntity legalEntity) {
        legalEntityService.update(legalEntity);
    }

    @PostMapping("/register-legal-entity")
    @ResponseBody
    public LegalEntity registerLegal(@RequestBody ApiRequest<SignUpLegalEntityDto> apiRequest) {
        return legalEntityService.saveNewLegalEntity(apiRequest.getDto(), apiRequest.getUserId());
    }

    @PostMapping("/get-individual-by-client-id")
    @ResponseBody
    public Individual getByClientId(@RequestBody Long clientId) {
        return individualService.getByClientId(clientId)
                .orElseThrow(()->new IllegalArgumentException("No individual"));
    }

    @PostMapping("/get-legal-by-client-id")
    @ResponseBody
    public LegalEntity geLegaltByClientId(@RequestBody Long clientId) {
        return legalEntityService.getByClientId(clientId)
                .orElseThrow(()->new IllegalArgumentException("No individual"));
    }




    @PostMapping("/get-inspector-by-client")
    @ResponseBody
    public Long getInspectorByClient(@RequestBody Long clienId) {
        Client client = clientService.getById(clienId)
                .orElseThrow(() -> new IllegalArgumentException("No such client"));
        return client.getInspectorId();
    }

    @PostMapping("/get-current-client")
    @ResponseBody
    public Long getCurrentClient(@RequestBody Long userId) {
        Client client = clientService.getClientByUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("No client"));
        return client.getId();
    }

    @PostMapping("/accept-claim")
    @ResponseBody
    public void acceptClaim(@RequestBody ApiChangeInspector apiChangeInspector) {
        clientService.update(apiChangeInspector.getClient(),
                apiChangeInspector.getChangeInspectorDto().getInspectorId());
    }

    @PostMapping("/get-be-id")
    @ResponseBody
    public Client getClientById(@RequestBody Long id) {
        Client client =  clientService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("No client"));
        System.out.println(client);
        return client;
    }

    @PostMapping
    @ResponseBody
    public List<Individual> getIndividualsByInspector(@RequestBody Long inspectorId){
        return individualService.getIndividualsByInspector(inspectorId);
    }

    @PostMapping("/insp-legals")
    @ResponseBody
    public List<LegalEntity> getLegalsByInspector(@RequestBody Long inspectorId){
        return legalEntityService.getLegalsByInspector(inspectorId);
    }

    @PostMapping("/insp-clients")
    @ResponseBody
    public List<Client> getClientByInspector(@RequestBody Long inspectorId){
        return clientService.geClientsByInspector(inspectorId);
    }

}
