package edu.bsu.cashstorage.entity;

import edu.bsu.cashstorage.entity.config.Place;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    private String externalSystemOperationId;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "account_outcome_id")
    private Account accountOutcome;

    @OneToOne
    @JoinColumn(name = "account_income_id")
    private Account accountIncome;

    private ZonedDateTime operationDate;

    private String description;

    @OneToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private BigDecimal income;

    private BigDecimal outcome;

    private Boolean deleted;
    private Boolean isProcessed;
    private Instant created;
    private Instant updated;
}
