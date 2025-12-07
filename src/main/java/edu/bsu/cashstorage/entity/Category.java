package edu.bsu.cashstorage.entity;

import edu.bsu.cashstorage.entity.config.Color;
import edu.bsu.cashstorage.entity.config.Icon;
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

import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "categories")
@Accessors(chain = true)
public class Category {
    @Id
    @Column(nullable = false)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "icon_id", nullable = false)
    private Icon icon;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(nullable = false)
    private Boolean forIncome;
    @Column(nullable = false)
    private Boolean forOutcome;

    @Column(nullable = false)
    private Boolean mandatoryOutcome;
}
