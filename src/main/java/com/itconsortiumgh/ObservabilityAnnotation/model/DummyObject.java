package com.itconsortiumgh.ObservabilityAnnotation.model;


import lombok.Data;
import org.springframework.stereotype.Component;




@Data
@Component
public class DummyObject {
    private String merchantId;
    private String productId;
    private String amountToDebit;
    private String clientPhone;
    private String apiKey;
    private String frequencyType;
    private String debitDay;
    private String frequency;
    private String startDate;
    private String endDate;
    private String thirdPartyReferenceNo;
    private String network;

}




