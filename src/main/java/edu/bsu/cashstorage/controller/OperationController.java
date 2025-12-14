package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
import edu.bsu.cashstorage.dto.operation.SimpleOperationDTO;
import edu.bsu.cashstorage.dto.operation.filter.OperationFilterDTO;
import edu.bsu.cashstorage.service.OperationService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(APIs.Server.API_V1_OPERATIONS)
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping(APIs.Server.FILTER_PATH)
    public List<ListOperationDTO> filterOperations(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                                   @RequestBody OperationFilterDTO filter) {
        return operationService.filterOperations(userId, filter);
    }

    @GetMapping
    public List<ListOperationDTO> listOperations(@RequestParam(APIs.Params.USER_ID) UUID userId) {
        return operationService.listOperations(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleOperationDTO createOperation(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                              @RequestBody InputOperationDTO inputOperationDTO) {
        return operationService.createOperation(userId, inputOperationDTO);
    }

    @DeleteMapping(APIs.Server.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperation(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                @PathVariable(APIs.Params.ID) UUID operationId) {
        operationService.deleteOperation(userId, operationId);
    }

    @PatchMapping(APIs.Server.ID_PATH)
    public SimpleOperationDTO updateOperation(@PathVariable(APIs.Params.ID) UUID operationId,
                                              @RequestParam(APIs.Params.USER_ID) UUID userId,
                                              @RequestBody InputOperationDTO inputOperationDTO) {
        return operationService.updateOperation(userId, operationId, inputOperationDTO);
    }
}
