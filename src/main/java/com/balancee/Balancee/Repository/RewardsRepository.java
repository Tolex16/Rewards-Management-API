package com.balancee.Balancee.Repository;

import com.balancee.Balancee.Domain.Entity.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Long> {
    Optional<Rewards> findByCustomer_CustomerId(Long customerId);
}

