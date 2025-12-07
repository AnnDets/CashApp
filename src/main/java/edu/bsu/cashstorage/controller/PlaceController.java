package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.place.SimplePlaceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(APIs.Server.API_V1_PLACES)
public class PlaceController {
    @GetMapping
    public List<SimplePlaceDTO> getPlaces() {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimplePlaceDTO createPlace(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                      @RequestBody SimplePlaceDTO simplePlaceDTO) {
        return null;
    }

    @PatchMapping(APIs.Server.ID_PATH)
    public SimplePlaceDTO patchPlace(@PathVariable(APIs.Params.ID) UUID placeId,
                                     @RequestParam(APIs.Params.USER_ID) UUID userId,
                                     @RequestBody SimplePlaceDTO simplePlaceDTO) {
        return null;
    }

    @DeleteMapping(APIs.Server.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlace(@PathVariable(APIs.Params.ID) UUID placeId) {
    }

    @GetMapping(APIs.Server.SEARCH_PATH)
    public List<SimplePlaceDTO> getPlaces(@RequestParam String search) {
        return null;
    }
}
