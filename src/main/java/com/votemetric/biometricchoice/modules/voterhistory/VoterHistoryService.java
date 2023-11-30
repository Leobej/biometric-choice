package com.votemetric.biometricchoice.modules.voterhistory;


import com.votemetric.biometricchoice.modules.voterhistory.VoterHistoryDTO;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import com.votemetric.biometricchoice.exception.VoterHistoryNotFoundException;
import com.votemetric.biometricchoice.interfaces.IVoterHistoryService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public VoterHistoryDTO getVoterHistoryById(Long voterId) {
        VoterHistory voterHistory = findVoterHistoryById(voterId);
        return mapper.convertToType(voterHistory, VoterHistoryDTO.class);

    }

    @Override
    public VoterHistoryDTO saveVoterHistory(VoterHistoryDTO voterHistoryDTO) {
        VoterHistory voterHistory = mapper.convertToType(voterHistoryDTO, VoterHistory.class);
        VoterHistory savedVoterHistory = voterHistoryRepository.save(voterHistory);
        return mapper.convertToType(savedVoterHistory, VoterHistoryDTO.class);
    }

    public VoterHistoryDTO updateVoterHistory(VoterHistoryDTO voterHistoryDTO) {
        checkIfVoterHistoryExists(voterHistoryDTO.getHistoryId());
        VoterHistory voterHistory = mapper.convertToType(voterHistoryDTO, VoterHistory.class);
        VoterHistory updatedVoterHistory = voterHistoryRepository.save(voterHistory);
        return mapper.convertToType(updatedVoterHistory, VoterHistoryDTO.class);
    }


    @Override
    public void deleteVoterHistoryById(Long voterHistoryId) {
        checkIfVoterHistoryExists(voterHistoryId);
        voterHistoryRepository.deleteById(voterHistoryId);
    }

    VoterHistory findVoterHistoryById(Long voterHistoryId) {
        return voterHistoryRepository.findById(voterHistoryId).orElseThrow(
                () -> new VoterHistoryNotFoundException(voterHistoryId));
    }

    private void checkIfVoterHistoryExists(Long voterHistoryId) {
        boolean exists = voterHistoryRepository.existsById(voterHistoryId);
        if (!exists) {
            throw new VoterHistoryNotFoundException(voterHistoryId);
        }
    }
}

