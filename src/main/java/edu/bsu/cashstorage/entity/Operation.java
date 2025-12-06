package edu.bsu.cashstorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "operations")
@Accessors(chain = true)
public class Operation {
    @Id
    @Column(nullable = false)
    private UUID id;

    // for future integration
    @Column(nullable = false)
    private String externalSystemOperationId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_outcome_id")
    private Account accountOutcome;

    @ManyToOne
    @JoinColumn(name = "account_income_id")
    private Account accountIncome;

    @Column(nullable = false)
    private ZonedDateTime operationDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private BigDecimal income;

    private BigDecimal outcome;

    @Column(nullable = false)
    private Boolean deleted;
    @Column(nullable = false)
    private Boolean isProcessed;
    @Column(nullable = false)
    private Instant created;
    @Column(nullable = false)
    private Instant updated;
}
