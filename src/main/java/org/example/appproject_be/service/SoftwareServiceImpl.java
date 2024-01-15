package org.example.appproject_be.service;

import lombok.RequiredArgsConstructor;
import org.example.appproject_be.dto.SoftwareDto;
import org.example.appproject_be.model.Hardware;
import org.example.appproject_be.model.Software;
import org.example.appproject_be.repository.AssetRepository;
import org.example.appproject_be.repository.HardwareRepository;
import org.example.appproject_be.repository.SoftwareRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoftwareServiceImpl implements SoftwareService{
    private final SoftwareRepository softwareRepository;
    private final AssetRepository assetRepository;
    @Override
    public Software saveSoftware(Software software){

        try {
            assetRepository.save(software.getAsset());
            software.setSwidx(software.getAsset().getAssetidx());
            softwareRepository.save(software);
            return software;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 등록된 S/N입니다.");
        }
    }

    @Override
    public void deleteSoftware(Long Id) {
        assetRepository.deleteById(Id);
        softwareRepository.deleteById(Id);
    }

    @Override
    public List<SoftwareDto> getSoftwares() {
        List<Software> softwareList = softwareRepository.findAll();

        List<SoftwareDto> softwareDtos = softwareList.stream()
                .sorted((software1, software2) -> software2.getAsset().getAssetname().compareTo(software1.getAsset().getAssetname()))
                .map(software -> {
                    SoftwareDto softwareDto = new SoftwareDto();
                    softwareDto.setAsset_idx(software.getAsset().getAssetidx());
                    softwareDto.setAssetType(software.getAsset().getAssettype());
                    softwareDto.setSn(software.getAsset().getSn());
                    softwareDto.setDept(software.getAsset().getDept());
                    softwareDto.setManufacturer(software.getAsset().getManufacturer());
                    softwareDto.setAssetName(software.getAsset().getAssetname());
                    softwareDto.setExpiryDate(software.getExpirydate());

                    return softwareDto;
                })
                .collect(Collectors.toList());

        return softwareDtos;
    }

    @Override
    public SoftwareDto getSoftware(Long id) {
        try {
            Optional<Software> softwareOptional = softwareRepository.findById(id);

            if (softwareOptional.isPresent()) {
                Software software = softwareOptional.get();
                SoftwareDto softwareDto = new SoftwareDto();

                // set asset
                softwareDto.setAsset_idx(software.getAsset().getAssetidx());
                softwareDto.setAssetType(software.getAsset().getAssettype());
                softwareDto.setSn(software.getAsset().getSn());
                softwareDto.setDept(software.getAsset().getDept());
                softwareDto.setManufacturer(software.getAsset().getManufacturer());
                softwareDto.setAssetName(software.getAsset().getAssetname());

                // set software-specific properties
                softwareDto.setExpiryDate(software.getExpirydate());

                return softwareDto;
            } else {
                // Handle the case where software with the given ID is not found
                throw new DataRetrievalFailureException("Software not found with ID: " + id);
                // Or return null, depending on your application's logic
            }
        } catch (Exception e) {
            // Handle other exceptions such as database errors
            throw new RuntimeException("Error retrieving software with ID: " + id, e);
        }
    }

}
