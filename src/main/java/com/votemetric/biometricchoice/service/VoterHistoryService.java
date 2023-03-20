package com.votemetric.biometricchoice.service;


import com.votemetric.biometricchoice.dto.VoterHistoryDTO;
import com.votemetric.biometricchoice.entity.VoterHistory;
import com.votemetric.biometricchoice.interfaces.IVoterHistoryService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.VoterHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoterHistoryService implements IVoterHistoryService {

    @Autowired
    private VoterHistoryRepository voterHistoryRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<VoterHistoryDTO> getAllVoterHistory() {
        List<VoterHistory> voterHistoryList = voterHistoryRepository.findAll();
        return voterHistoryList.stream()
                .map(voterHistory -> mapper.convertToType(voterHistory, VoterHistoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VoterHistoryDTO getVoterHistoryById(long voterId) {
        Optional<VoterHistory> optionalVoterHistory = voterHistoryRepository.findById(voterId);
        return optionalVoterHistory.map(voterHistory -> mapper.convertToType(voterHistory, VoterHistoryDTO.class))
                .orElse(null);
    }

    @Override
    public VoterHistoryDTO saveVoterHistory(VoterHistoryDTO voterHistoryDTO) {
        VoterHistory voterHistory = mapper.convertToType(voterHistoryDTO, VoterHistory.class);
        VoterHistory savedVoterHistory = voterHistoryRepository.save(voterHistory);
        return mapper.convertToType(savedVoterHistory, VoterHistoryDTO.class);
    }

    public VoterHistoryDTO updateVoterHistory( VoterHistoryDTO voterHistoryDTO) {
        Optional<VoterHistory> optionalVoterHistory = voterHistoryRepository.findById(voterHistoryDTO.getHistoryId());
        if (optionalVoterHistory.isPresent()) {
            VoterHistory voterHistory = optionalVoterHistory.get();
            mapper.convertToType(voterHistoryDTO, VoterHistory.class);
            VoterHistory updatedVoterHistory = voterHistoryRepository.save(voterHistory);
            return mapper.convertToType(updatedVoterHistory, VoterHistoryDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public void deleteVoterHistoryById(long voterId) {
        voterHistoryRepository.deleteById(voterId);
    }
}

