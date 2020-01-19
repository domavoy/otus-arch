package ru.mdorofeev.finance.core.service;

import org.springframework.stereotype.Service;

//TODO: P2: check is normal pattern to call externtal webservices ?
@Service
public class AuthProxyService {

    public Long findBySession(Long sessionId){
        return 10L;
    }
}

