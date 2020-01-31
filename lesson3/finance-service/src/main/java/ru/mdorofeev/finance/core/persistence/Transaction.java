package ru.mdorofeev.finance.core.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "transaction", schema = "public")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Integer transactionType;

    private Long userId;

    @OneToOne
    private Account account;

    @OneToOne
    private Account toAccount;

    private BigDecimal amount;
    private BigDecimal toAmount;

    @OneToOne
    private Category category;

    private String comment;
}
