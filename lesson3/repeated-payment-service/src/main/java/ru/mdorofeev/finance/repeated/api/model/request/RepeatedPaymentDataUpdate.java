package ru.mdorofeev.finance.repeated.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
public class RepeatedPaymentDataUpdate extends RepeatedPaymentData {

    private Long repeatedPaymentId;
}
