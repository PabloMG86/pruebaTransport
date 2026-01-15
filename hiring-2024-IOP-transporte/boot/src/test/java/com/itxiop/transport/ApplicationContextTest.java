package com.itxiop.transport;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@AllArgsConstructor
public class ApplicationContextTest {

    private ApplicationContext ctx;
    @Test
    void checkContextLoads() {

        assertNotNull(ctx.getBean("findShipmentUseCase"));
        assertNotNull(ctx.getBean("findShipmentsUseCase"));
        assertNotNull(ctx.getBean("processPendingShipmentsUseCase"));
        assertNotNull(ctx.getBean("loadShipmentUseCase"));
        assertNotNull(ctx.getBean("purgeShipmentUseCase"));


    }
}
