package ru.mdorofeev.finance.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "session", schema = "public")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    private User user;

    private Long sessionId;

    private Integer status;
}