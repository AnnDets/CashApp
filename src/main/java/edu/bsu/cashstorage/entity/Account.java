package edu.bsu.cashstorage.entity;

import edu.bsu.cashstorage.entity.config.Bank;
import edu.bsu.cashstorage.entity.config.Currency;
import edu.bsu.cashstorage.entity.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "accounts")
@Accessors(chain = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private AccountType type;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    private BigDecimal creditLimit;
    @Column(nullable = false)
    private BigDecimal currentBalance;

    @Column(nullable = false)
    private Boolean includeInTotalBalance;

    @Column(nullable = false)
    private Boolean defaultAccount;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(length = 4)
    private String cardNumber1;
    @Column(length = 4)
    private String cardNumber2;
    @Column(length = 4)
    private String cardNumber3;
    @Column(length = 4)
    private String cardNumber4;

    @Column(nullable = false)
    private Boolean savingsAccount;

    @Column(nullable = false)
    private Boolean archiveAccount;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
