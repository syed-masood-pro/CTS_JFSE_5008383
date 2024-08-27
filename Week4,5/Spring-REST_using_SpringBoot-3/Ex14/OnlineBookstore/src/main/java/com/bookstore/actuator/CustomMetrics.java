package com.example.bookstore.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomMetrics {

    @Autowired
    private MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        meterRegistry.counter("custom.book.count", "type", "book");
    }

    public void incrementBookCounter() {
        meterRegistry.counter("custom.book.count").increment();
    }
}
