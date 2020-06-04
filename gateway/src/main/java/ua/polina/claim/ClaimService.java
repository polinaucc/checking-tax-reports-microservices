package ua.polina.claim;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "claim")
public interface ClaimService {
    @ResponseBody
    @PostMapping("/create-claim")
    void saveNewClaim(@RequestBody ApiClaim apiClaim);

    @ResponseBody
    @PostMapping("/my-claims")
    List<Claim> getClaimsPage(@RequestBody Long clientId);

    @ResponseBody
    @PostMapping("/claims")
    List<Claim> getClaims();

    @ResponseBody
    @GetMapping("/reject-claim/{id}")
    void rejectClaim(@PathVariable("id") Long id);

    @GetMapping("/accept-claim/{id}")
    @ResponseBody
    Claim getAcceptClaimForm(@PathVariable("id") Long id);

    @PostMapping("get-by-status}")
    @ResponseBody
    List<Claim> getClaimsByStatus(@RequestBody Status status);

}
