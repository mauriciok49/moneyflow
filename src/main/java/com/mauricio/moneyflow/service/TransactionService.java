package com.mauricio.moneyflow.service;

import com.mauricio.moneyflow.dto.TransactionRequestDTO;
import com.mauricio.moneyflow.dto.TransactionResponseDTO;
import com.mauricio.moneyflow.entity.Category;
import com.mauricio.moneyflow.entity.Transaction;
import com.mauricio.moneyflow.entity.User;
import com.mauricio.moneyflow.exception.EntityNotFoundException;
import com.mauricio.moneyflow.repository.CategoryRepository;
import com.mauricio.moneyflow.repository.TransactionRepository;
import com.mauricio.moneyflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO){
        User user = userRepository.findById(transactionRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
        Category category = categoryRepository.findById(transactionRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        Transaction transaction = Transaction.builder()
                .value(transactionRequestDTO.getAmount())
                .type(transactionRequestDTO.getType())
                .description(transactionRequestDTO.getDescription())
                .paymentMethod(transactionRequestDTO.getPaymentMethod())
                .category(category)
                .user(user)
                .build();
        transactionRepository.save(transaction);
        return new TransactionResponseDTO(transaction.getId(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getPaymentMethod(),
                category.getId(),
                transaction.getCreatedAt(),
                user.getId());
    }

    public List<TransactionResponseDTO> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getValue(),
                        transaction.getType(),
                        transaction.getDescription(),
                        transaction.getPaymentMethod(),
                        transaction.getCategory().getId(),
                        transaction.getCreatedAt(),
                        transaction.getUser().getId()))
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO findById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getPaymentMethod(),
                transaction.getCategory().getId(),
                transaction.getCreatedAt(),
                transaction.getUser().getId());
    }

    public TransactionResponseDTO update(UUID id, TransactionRequestDTO transactionRequestDTO) {
        User user = userRepository.findById(transactionRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
        Category category = categoryRepository.findById(transactionRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        transaction.setValue(transactionRequestDTO.getAmount());
        transaction.setType(transactionRequestDTO.getType());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setPaymentMethod(transactionRequestDTO.getPaymentMethod());
        transaction.setCategory(category);
        transaction.setUser(user);
        transactionRepository.save(transaction);

        return new TransactionResponseDTO(transaction.getId(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getPaymentMethod(),
                transaction.getCategory().getId(),
                transaction.getCreatedAt(),
                transaction.getUser().getId());

    }

    public void delete(UUID id) {
        transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        transactionRepository.deleteById(id);

    }

}
