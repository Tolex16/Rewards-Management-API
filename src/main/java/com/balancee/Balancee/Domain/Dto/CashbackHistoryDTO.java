package com.balancee.Balancee.Domain.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CashbackHistoryDTO {
    private LocalDate transactionDate;
    private Double amountEarned;
    private String description;
}