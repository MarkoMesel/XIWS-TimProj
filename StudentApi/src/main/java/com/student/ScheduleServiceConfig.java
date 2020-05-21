package com.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ScheduleServiceConfig {
    @Bean
    public Jaxb2Marshaller scheduleServiceMarshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("com.student.soap.scheduleservice.contract");
        return jaxb2Marshaller;
    }
}