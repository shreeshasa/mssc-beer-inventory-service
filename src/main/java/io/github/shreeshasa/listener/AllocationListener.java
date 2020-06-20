package io.github.shreeshasa.listener;

import io.github.shreeshasa.config.JmsConfig;
import io.github.shreeshasa.model.event.AllocateOrderRequest;
import io.github.shreeshasa.model.event.AllocateOrderResult;
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
public class AllocationListener {

  private final AllocationService allocationService;
  private final JmsTemplate jmsTemplate;

  @JmsListener (destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
  public void listen(AllocateOrderRequest request) {
    AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
    builder.beerOrderDto(request.getBeerOrderDto());

    try {
      Boolean allocationResult = allocationService.allocateOrder(request.getBeerOrderDto());
      builder.pendingInventory(!allocationResult);
      builder.allocationError(false);
    } catch (Exception e) {
      log.error("Allocation failed for Order Id: {}", request.getBeerOrderDto().getId());
      builder.allocationError(true);
    }

    jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE, builder.build());
  }
}
