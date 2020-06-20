package io.github.shreeshasa.listener;

import io.github.shreeshasa.config.JmsConfig;
import io.github.shreeshasa.model.event.AllocateOrderRequest;
import io.github.shreeshasa.model.event.AllocateOrderResult;
import io.github.shreeshasa.model.event.DeallocateOrderRequest;
import io.github.shreeshasa.service.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shreeshasa
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListener {

  private final AllocationService allocationService;


  @JmsListener (destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
  public void listen(DeallocateOrderRequest deallocateOrderRequest) {
    allocationService.deallocateOrder(deallocateOrderRequest.getBeerOrderDto());
  }
}
