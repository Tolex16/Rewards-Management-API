package com.balancee.Balancee.Service.ServiceImpl;

;
import com.balancee.Balancee.Domain.Dto.CashbackHistoryDTO;
import com.balancee.Balancee.Domain.Dto.CustomerDTO;
import com.balancee.Balancee.Domain.Dto.RewardsDTO;
import com.balancee.Balancee.Domain.Entity.CashbackHistory;
import com.balancee.Balancee.Domain.Entity.Customer;
import com.balancee.Balancee.Domain.Entity.Rewards;
import com.balancee.Balancee.Domain.Entity.Role;
import com.balancee.Balancee.ExpectionHandling.CustomerAlreadyExistsException;
import com.balancee.Balancee.ExpectionHandling.CustomerNotFoundException;
import com.balancee.Balancee.Repository.CashbackHistoryRepository;
import com.balancee.Balancee.Repository.CustomerRepository;
import com.balancee.Balancee.Repository.RewardsRepository;
import com.balancee.Balancee.Service.JwtService;
import com.balancee.Balancee.Service.RewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardsServiceImpl implements RewardsService {

    private final JwtService jwtService;
    private final RewardsRepository rewardsRepository;
    private final CustomerRepository customerRepository;
    private final CashbackHistoryRepository cashbackHistoryRepository;

    public RewardsDTO addRewards(RewardsDTO dto) {
        Long customerId = jwtService.getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not present"));

        Rewards rewards = new Rewards();
        rewards.setCustomer(customer);
        rewards.setTotalCashback(dto.getTotalCashback());
        rewards.setCurrentBalance(dto.getCurrentBalance());

        Rewards savedRewards = rewardsRepository.save(rewards);

        return new RewardsDTO(customerId, savedRewards.getTotalCashback(), savedRewards.getCurrentBalance());
    }

    public CashbackHistoryDTO addCashbackHistory(CashbackHistoryDTO dto) {
        Long customerId = jwtService.getCustomerId();
        Rewards rewards = rewardsRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        CashbackHistory cashbackHistory = new CashbackHistory();
        cashbackHistory.setTransactionDate(dto.getTransactionDate());
        cashbackHistory.setAmountEarned(dto.getAmountEarned());
        cashbackHistory.setDescription(dto.getDescription());
        cashbackHistory.setRewards(rewards);

        // Update customer balance
        rewards.setCurrentBalance(rewards.getCurrentBalance() + dto.getAmountEarned());
        rewards.setTotalCashback(rewards.getTotalCashback() + dto.getAmountEarned());

        cashbackHistoryRepository.save(cashbackHistory);
        rewardsRepository.save(rewards);

        return new CashbackHistoryDTO(dto.getTransactionDate(), dto.getAmountEarned(), dto.getDescription());
    }

    public RewardsDTO getRewards(Long customerId) {
        Rewards rewards = rewardsRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return new RewardsDTO(customerId, rewards.getTotalCashback(), rewards.getCurrentBalance());
    }

    public List<CashbackHistoryDTO> getCashbackHistory(Long customerId) {
        List<CashbackHistory> cashbackHistories = cashbackHistoryRepository.findByRewards_Customer_CustomerId(customerId);

        return cashbackHistories.stream()
                .map(history -> new CashbackHistoryDTO(
                        history.getTransactionDate(),
                        history.getAmountEarned(),
                        history.getDescription()))
                .collect(Collectors.toList());
    }
}
