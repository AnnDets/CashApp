package edu.bsu.cashstorage.entity.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "colors")
@Accessors(chain = true)
public class Color {
    @Id
    private String name;
    @Column(nullable = false)
    private Short red;
    @Column(nullable = false)
    private Short green;
    @Column(nullable = false)
    private Short blue;
}
