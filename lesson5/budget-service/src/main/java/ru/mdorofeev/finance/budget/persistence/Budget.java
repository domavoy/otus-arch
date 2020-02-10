package ru.mdorofeev.finance.budget.persistence;

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
@Table(name = "budget", schema = "public")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userId;

    private Long categoryId;

    private Double amount;
}
