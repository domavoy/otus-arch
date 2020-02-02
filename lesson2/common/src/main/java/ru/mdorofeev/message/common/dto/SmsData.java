package ru.mdorofeev.message.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SmsData extends UuidData {

    private String phone;

    private String message;
}
