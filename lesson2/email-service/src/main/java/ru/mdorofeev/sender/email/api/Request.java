package ru.mdorofeev.sender.email.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Request {

    private String email;

    private String title;

    private String message;
}
