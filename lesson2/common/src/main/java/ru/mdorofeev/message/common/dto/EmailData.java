package ru.mdorofeev.message.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailData extends UuidData {

    private String to;

    private String title;

    private String message;
}
