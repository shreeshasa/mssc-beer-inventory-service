package io.github.shreeshasa.listener;

import io.github.shreeshasa.config.JmsConfig;
import io.github.shreeshasa.domain.BeerInventory;
import io.github.shreeshasa.model.event.NewInventoryEvent;
import io.github.shreeshasa.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author shreeshasa
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {

  private final BeerInventoryRepository beerInventoryRepository;

  @JmsListener (destination = JmsConfig.NEW_INVENTORY_QUEUE)
  public void listen(NewInventoryEvent event) {
    log.debug("Got Inventory: {}", event.toString());
    beerInventoryRepository.save(BeerInventory.builder()
                                     .beerId(event.getBeerDto().getId())
                                     .upc(event.getBeerDto().getUpc())
                                     .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                                     .build());
  }
}
