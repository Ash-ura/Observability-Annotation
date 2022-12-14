package com.itconsortiumgh.ObservabilityAnnotation.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "message")
@Data
@Component
public class ApplicationProperties {

    private String preapproval;
    private String debitRequest;
    private String mandateCreationCallback;
    private String saveToDB;
    private String updateMandateRequest;


}
