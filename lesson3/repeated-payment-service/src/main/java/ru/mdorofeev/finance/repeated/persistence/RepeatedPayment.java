package ru.mdorofeev.finance.repeated.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "repeated_payment", schema = "public")
public class RepeatedPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userId;

    private Long categoryId;

    private Long accountId;

    private Double amount;

    private Long granularity;

    private Date start;

    private Date end;

    private String comment;
}
