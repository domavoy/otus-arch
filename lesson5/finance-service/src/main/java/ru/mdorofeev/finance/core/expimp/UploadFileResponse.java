package ru.mdorofeev.finance.core.expimp;

import ru.mdorofeev.finance.common.api.model.response.Response;

public class UploadFileResponse extends Response {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}