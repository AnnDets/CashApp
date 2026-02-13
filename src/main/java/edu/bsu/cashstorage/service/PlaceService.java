package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.dto.place.SimplePlaceDTO;
import edu.bsu.cashstorage.entity.Place;
import edu.bsu.cashstorage.mapper.PlaceMapper;
import edu.bsu.cashstorage.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public void removePlace(UUID placeId) {
        if (!placeRepository.existsById(placeId)) {
            throw new EntityNotFoundException("Place not found");
        }
        placeRepository.deleteById(placeId);
    }

    public List<SimplePlaceDTO> searchPlaces(String search, UUID userId) {
        return placeMapper.toSimpleDTO(placeRepository.findByDescriptionContainingIgnoreCaseAndUsedBy_Id(search, userId));
    }

    public SimplePlaceDTO createPlace(UUID userId, SimplePlaceDTO simplePlaceDTO) {
        Place entity = placeMapper.toEntity(simplePlaceDTO, userId);
        entity = placeRepository.save(entity);
        return placeMapper.toSimpleDTO(entity);
    }

    public SimplePlaceDTO patchPlace(UUID userId, UUID placeId, SimplePlaceDTO simplePlaceDTO) {
        Place fromDB = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));

        placeMapper.patchEntity(placeMapper.toEntity(simplePlaceDTO, userId), fromDB);

        return placeMapper.toSimpleDTO(placeRepository.save(fromDB));
    }
}
