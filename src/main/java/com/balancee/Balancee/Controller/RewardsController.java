package com.balancee.Balancee.Controller;

import com.balancee.Balancee.Domain.Dto.CashbackHistoryDTO;
import com.balancee.Balancee.Domain.Dto.RewardsDTO;
import com.balancee.Balancee.Domain.Entity.CashbackHistory;
import com.balancee.Balancee.Domain.Entity.Rewards;
import com.balancee.Balancee.Service.RewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final RewardsService rewardsService;

    @PostMapping("/add-reward")
    public ResponseEntity<?> addRewards(@RequestBody RewardsDTO rewardsDTO) {
        try {
            RewardsDTO savedRewards = rewardsService.addRewards(rewardsDTO);
            return ResponseEntity.ok(savedRewards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving rewards: " + e.getMessage());
        }
    }

    @PostMapping("/cashback-history/add")
    public ResponseEntity<?> addCashbackHistory(@RequestBody CashbackHistoryDTO cashbackHistoryDTO) {
        try {
            CashbackHistoryDTO savedCashback = rewardsService.addCashbackHistory(cashbackHistoryDTO);
            return ResponseEntity.ok(savedCashback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving cashback history: " + e.getMessage());
        }
    }

    @GetMapping("/balance/{customerId}")
    public ResponseEntity<RewardsDTO> getRewards(@PathVariable Long customerId) {
        return ResponseEntity.ok(rewardsService.getRewards(customerId));
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<CashbackHistoryDTO>> getCashbackHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(rewardsService.getCashbackHistory(customerId));
    }
}
