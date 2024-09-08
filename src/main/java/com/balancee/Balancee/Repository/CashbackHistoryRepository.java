package com.balancee.Balancee.Repository;

import com.balancee.Balancee.Domain.Entity.CashbackHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CashbackHistoryRepository extends JpaRepository<CashbackHistory, Long> {
    List<CashbackHistory> findByRewards_Customer_CustomerId(Long customerId);
}
