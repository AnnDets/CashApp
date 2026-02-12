package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
import edu.bsu.cashstorage.dto.operation.SimpleOperationDTO;
import edu.bsu.cashstorage.dto.operation.filter.OperationFilterDTO;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.service.OperationService;
import edu.bsu.cashstorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(APIs.Server.API_V1_OPERATIONS)
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final UserService userService;

    @PostMapping(APIs.Server.FILTER_PATH)
    public List<ListOperationDTO> filterOperations(@RequestBody OperationFilterDTO filter) {
        User user = userService.getCurrentUser();
        return operationService.filterOperations(user.getId(), filter);
    }

    @GetMapping
    public List<ListOperationDTO> listOperations() {
        User user = userService.getCurrentUser();
        return operationService.listOperations(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleOperationDTO createOperation(@RequestBody InputOperationDTO inputOperationDTO) {
        User user = userService.getCurrentUser();
        return operationService.createOperation(user.getId(), inputOperationDTO);
    }

    @DeleteMapping(APIs.Server.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperation(@PathVariable(APIs.Params.ID) UUID operationId) {
        User user = userService.getCurrentUser();
        operationService.deleteOperation(user.getId(), operationId);
    }

    @PatchMapping(APIs.Server.ID_PATH)
    public SimpleOperationDTO updateOperation(@PathVariable(APIs.Params.ID) UUID operationId,
                                              @RequestBody InputOperationDTO inputOperationDTO) {
        User user = userService.getCurrentUser();
        return operationService.updateOperation(user.getId(), operationId, inputOperationDTO);
    }
}
