package ru.mdorofeev.finance.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.core.persistence.Transaction;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class ExportService {

    @Autowired
    TransactionService mainService;

    @Autowired
    ConfigurationService configurationService;

    public static final String UTF8_BOM = "\uFEFF";

    public String export(String fileName, Long userId, Date fromDate) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(UTF8_BOM);

        CSVUtils.writeLine(writer, Arrays.asList("Year", "Month", "Day", "Year-Month",
                "TransactionType", "Account", "Category", "Amount", "Comment"));

        List<Transaction> transactions = mainService.getTransactions(userId, fromDate);
        transactions.stream().forEach(transaction -> {
            try {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(transaction.getDate());

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                TransactionType type = TransactionType.from(transaction.getTransactionType());


                if(type == TransactionType.INCOME || type == TransactionType.EXPENSE){
                    List<String> data = Arrays.asList(
                            String.valueOf(year), String.valueOf(month), String.valueOf(day),
                            year + "-" + month, type.name(), transaction.getAccount().getName(),
                            transaction.getCategory().getName(), transaction.getAmount().abs().toString(), transaction.getComment()
                    );

                    CSVUtils.writeLine(writer, data);
                }
            } catch (Exception e) {
                //TODO: P2: catch exception normally
                System.out.println("Failed to process: " + transaction);
                e.printStackTrace();
            }
        });

        writer.flush();
        writer.close();

        return fileName;
    }
}
