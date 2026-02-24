package com.mauricio.moneyflow.dto;

import com.mauricio.moneyflow.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    private UUID id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private String paymentMethod;
    private UUID categoryId;
    private LocalDateTime createdAt;
    private UUID userId;
}
