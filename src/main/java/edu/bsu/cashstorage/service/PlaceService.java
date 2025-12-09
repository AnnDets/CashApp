package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.entity.Place;
import edu.bsu.cashstorage.mapper.PlaceMapper;
import edu.bsu.cashstorage.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public List<Place> getPlaces(UUID userId) {
        return placeRepository.findByAuthorId(userId);
    }

    public Place createPlace(Place place) {
        return placeRepository.save(place);
    }

    public Place patchPlace(UUID placeId, Place update) {
        Place fromDB = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));
        
        placeMapper.patchEntity(update, fromDB);

        return placeRepository.save(fromDB);
    }

    public void removePlace(UUID placeId) {
        if (!placeRepository.existsById(placeId)) {
            throw new EntityNotFoundException("Place not found");
        }
        placeRepository.deleteById(placeId);
    }

    public List<Place> searchPlaces(String search, UUID userId) {
        return placeRepository.findByDescriptionContainingIgnoreCaseAndAuthor_Id(search, userId);
    }

}
