package com.balancee.Balancee.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cashback_history")
public class CashbackHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private LocalDate transactionDate;

    private Double amountEarned;

    private String description;

    @ManyToOne
    @JoinColumn(name = "reward_id")
    @JsonBackReference
    private Rewards rewards;
}
