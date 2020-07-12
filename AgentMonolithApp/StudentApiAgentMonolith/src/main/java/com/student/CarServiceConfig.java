package com.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CarServiceConfig {
    @Bean
    public Jaxb2Marshaller carServiceMarshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("com.student.soap.contract.carservice");
        return jaxb2Marshaller;
    }
}