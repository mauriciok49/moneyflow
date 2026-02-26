package com.mauricio.moneyflow.controller;

import com.mauricio.moneyflow.dto.TransactionRequestDTO;
import com.mauricio.moneyflow.dto.TransactionResponseDTO;
import com.mauricio.moneyflow.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.create(transactionRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        return ResponseEntity.ok(transactionService.update(id, transactionRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
