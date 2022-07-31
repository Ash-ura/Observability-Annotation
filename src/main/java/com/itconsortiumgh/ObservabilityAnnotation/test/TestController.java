package com.itconsortiumgh.ObservabilityAnnotation.test;

import com.itconsortiumgh.ObservabilityAnnotation.annotation.Observability;
import com.itconsortiumgh.ObservabilityAnnotation.model.ApplicationProperties;
import com.itconsortiumgh.ObservabilityAnnotation.model.DummyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class TestController {



        @PostMapping("test-creation")
        @Observability(message ="Pre-Approval", key="getClientPhone")
        public DummyObject dummyObjectTestForObservability(@RequestBody DummyObject dummyObject){
            return dummyObject;
        }
}
