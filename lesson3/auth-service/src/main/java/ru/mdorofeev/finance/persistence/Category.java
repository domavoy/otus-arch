package ru.mdorofeev.finance.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

@Entity
@Table(name = "category", schema = "public")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    private User user;

    private Integer transactionType;

    private String name;
}
