package ua.polina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.polina.dto.ApiClaim;
import ua.polina.entity.Claim;
import ua.polina.entity.Status;
import ua.polina.service.ClaimService;

import java.util.List;

@Controller
public class ClaimController {
    @Autowired
    ClaimService claimService;

    @ResponseBody
    @PostMapping("/create-claim")
    public void saveNewClaim(@RequestBody ApiClaim apiClaim) {
        this.claimService.saveNewClaim(apiClaim.getClaimDto(), apiClaim.getClientId(), apiClaim.getInspectorId());
    }

    @ResponseBody
    @PostMapping("/my-claims")
    public List<Claim> getClaimsPage(@RequestBody Long clientId) {
        return claimService.getClaimsByClient(clientId);
    }

    @ResponseBody
    @PostMapping("/claims")
    public List<Claim> getClaims() {
        return claimService.getAllClaims();
    }

    @ResponseBody
    @GetMapping("/reject-claim/{id}")
    public void rejectClaim(@PathVariable("id") Long id) {
        Claim claim = claimService.getClaimById(id)
                .orElseThrow(() -> new IllegalArgumentException("No claim"));

        claimService.update(claim, Status.REJECTED);
    }

    @GetMapping("/accept-claim/{id}")
    @ResponseBody
    public Claim getAcceptClaimForm(@PathVariable("id") Long id) {
        Claim claim = claimService.getClaimById(id)
                .orElseThrow(() -> new IllegalArgumentException("No claim"));
        System.out.println(claim);
        return claimService.update(claim, Status.ACCEPTED);
    }

    @PostMapping("get-by-status}")
    @ResponseBody
    public List<Claim> getClaimsByStatus(@RequestBody Status status) {
        return claimService.getByStatus(status);
    }




}
