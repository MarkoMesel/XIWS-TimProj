package com.student.scheduleservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class UserServiceConfig {
    @Bean
    public Jaxb2Marshaller userServiceMarshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("com.student.soap.contract.userservice");
        return jaxb2Marshaller;
    }
}
