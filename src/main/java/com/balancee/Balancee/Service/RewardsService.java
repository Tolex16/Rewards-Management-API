package com.balancee.Balancee.Service;


import com.balancee.Balancee.Domain.Dto.CashbackHistoryDTO;
import com.balancee.Balancee.Domain.Dto.RewardsDTO;
import java.util.List;

public interface RewardsService {
    RewardsDTO addRewards(RewardsDTO dto);

    CashbackHistoryDTO addCashbackHistory(CashbackHistoryDTO dto);

    RewardsDTO getRewards(Long customerId) ;

    List<CashbackHistoryDTO> getCashbackHistory(Long customerId);

}
