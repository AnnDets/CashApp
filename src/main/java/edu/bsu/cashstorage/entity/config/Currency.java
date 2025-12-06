package edu.bsu.cashstorage.entity.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "currencies")
@Accessors(chain = true)
public class Currency {
    @Id
    @Column(length = 3, nullable = false)
    private String id;

    @Column(nullable = false)
    private String displayName;

    private String symbol;
}
