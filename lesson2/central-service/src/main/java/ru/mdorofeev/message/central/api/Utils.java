package ru.mdorofeev.message.central.api;

import ru.mdorofeev.message.central.config.ModulesConfig;

import java.util.UUID;

public class Utils {

    // generate uuid with module prefix, example: sms-c230f655-6e6f-4ca2-8e03-78bd3c4e2870
    public static String moduleToUuid(String modulePrefix){
        return modulePrefix+ "-" + UUID.randomUUID().toString();
    }

    // parser uuid and find sustem
    public static String uuidToModuleStatusQueue(ModulesConfig config, String uuid){
        if(uuid.startsWith(config.getSmsUuidPrefix())){
            return config.getSmsStatusQueue();
        } else  if(uuid.startsWith(config.getEmailUuidPrefix())){
            return config.getEmailStatusQueue();
        }

        throw new ServiceException("Failed to find module for uuid: " + uuid);
    }
}
