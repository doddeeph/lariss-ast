package id.lariss.service.mapper;

import static id.lariss.domain.OrderAsserts.*;
import static id.lariss.domain.OrderTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrderSample1();
        var actual = orderMapper.toEntity(orderMapper.toDto(expected));
        assertOrderAllPropertiesEquals(expected, actual);
    }
}