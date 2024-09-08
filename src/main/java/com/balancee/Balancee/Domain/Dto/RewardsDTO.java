package com.balancee.Balancee.Domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardsDTO {

    private Long customerId;
    private Double totalCashback;
    private Double currentBalance;
}
