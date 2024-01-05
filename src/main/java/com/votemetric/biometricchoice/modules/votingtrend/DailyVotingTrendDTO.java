package com.votemetric.biometricchoice.modules.votingtrend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyVotingTrendDTO {
    private LocalDateTime date;
    private Long totalVotes;
    private Long candidateIdResult;

    public DailyVotingTrendDTO(LocalDateTime date, Long totalVotes) {
        this.date = date;
        this.totalVotes = totalVotes;
    }


    // Constructor, getters, and setters
}

