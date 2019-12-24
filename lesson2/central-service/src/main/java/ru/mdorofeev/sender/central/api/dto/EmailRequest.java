package ru.mdorofeev.sender.central.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailRequest {

    private String email;

    private String title;

    private String message;
}
