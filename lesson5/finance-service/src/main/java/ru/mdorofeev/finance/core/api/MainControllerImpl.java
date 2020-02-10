package ru.mdorofeev.finance.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.api.Processor;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.auth.client.ProcessWithUserWrapper;
import ru.mdorofeev.finance.core.api.model.request.TransactionRequest;
import ru.mdorofeev.finance.core.api.model.request.TransactionRequestInternal;
import ru.mdorofeev.finance.core.api.model.request.TransactionTransferRequest;
import ru.mdorofeev.finance.core.api.model.response.AccountStatListResponse;
import ru.mdorofeev.finance.core.api.model.response.AccountStatResponse;
import ru.mdorofeev.finance.core.api.model.response.TransactionListResponse;
import ru.mdorofeev.finance.core.api.model.response.TransactionResponse;
import ru.mdorofeev.finance.core.expimp.UploadFileResponse;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.service.ConfigurationService;
import ru.mdorofeev.finance.core.service.TransactionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MainControllerImpl implements MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainControllerImpl.class);
    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private TransactionService mainService;

    @Autowired
    private AuthServiceClient authServiceClient;

    @Override
    public ResponseEntity<TransactionListResponse> getTransactions(Long sessionId, String fromDate) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, sessionId, user -> {
            Date date;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(fromDate);
            } catch (NullPointerException | ParseException e) {
                throw new ServiceException("Incorrect date format: " + fromDate + ". Expected: yyyy-MM-dd");
            }

            List<TransactionResponse> resp = mainService.getTransactions(user, date).stream().map(temp -> {
                TransactionResponse response = new TransactionResponse();
                response.setAccountName(temp.getAccount().getName());
                response.setCategoryName(temp.getCategory() != null ? temp.getCategory().getName() : "");
                response.setComment(temp.getComment());
                response.setMoney(temp.getAmount().doubleValue());
                response.setDate(temp.getDate());
                response.setToAccount(temp.getToAccount() != null ? temp.getToAccount().getName() : "");

                TransactionType type = TransactionType.fromWithNull(temp.getTransactionType());
                if (type != null) {
                    response.setTransactionType(type.name());
                }

                return response;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(new TransactionListResponse(resp), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> addTransaction(TransactionRequest request) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, request.getSessionId(), user -> {
            Account account = configurationService.getAccountByName(user, request.getAccountName());
            if (account == null) {
                throw new ServiceException("ACCOUNT_NOT_FOUND");
            }

            Category category = configurationService.getCategory(user, request.getCategoryName());
            if (category == null) {
                throw new ServiceException("CATEGORY_NOT_FOUND");
            }

            mainService.createTransaction(user, new Date(), account,
                    category, request.getMoney(), request.getComment());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> moneyTransfer(TransactionTransferRequest request) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, request.getSessionId(), user -> {
            Account fromAccount = configurationService.getAccountByName(user, request.getFromAccount());
            if (fromAccount == null) {
                throw new ServiceException("FROM_ACCOUNT_NOT_FOUND");
            }

            Account toAccount = configurationService.getAccountByName(user, request.getToAccount());
            if (toAccount == null) {
                throw new ServiceException("TO_ACCOUNT_NOT_FOUND");
            }

            mainService.moneyTransfer(user, new Date(), fromAccount, toAccount,
                    request.getMoney(), request.getComment());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<AccountStatListResponse> getAccountStat(Long sessionId) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, sessionId, user -> {
            List<AccountStatResponse> resp = mainService.getAccounts(user).stream().map(temp -> {
                AccountStatResponse response = new AccountStatResponse();
                response.setAccounName(temp.getName());
                response.setCurrency(temp.getCurrency().getName());
                response.setAmount(temp.getAmount());
                return response;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(new AccountStatListResponse(resp), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> addInternalTransaction(TransactionRequestInternal request) {
        return Processor.wrapExceptions(() -> {
            if(!authServiceClient.checkUserId(request.getUserId())){
                throw new ServiceException("USER_NOT_FOUND");
            }

            Account account = configurationService.getAccountById(request.getAccountId());
            Category category = configurationService.getCategoryById(request.getCategoryId());

            mainService.createTransaction(request.getUserId(), new Date(), account,
                    category, request.getMoney(), request.getComment());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    //https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
    @Override
    public UploadFileResponse importTransactions(Long sessionId, MultipartFile file) {
        return null;
    }

    // https://stackoverflow.com/questions/40344993/spring-boot-serving-image-from-file-containing-it-base64-encoded
    @Override
    public HttpEntity<byte[]> exportTransactions(@PathVariable Long sessionId) {
        byte[] file = new byte[1];

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(file.length);

        return new HttpEntity<byte[]>(file, headers);
    }
}