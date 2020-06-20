package io.github.shreeshasa.service;

import io.github.shreeshasa.domain.BeerInventory;
import io.github.shreeshasa.model.BeerOrderDto;
import io.github.shreeshasa.model.BeerOrderLineDto;
import io.github.shreeshasa.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shreeshasa
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

  private final BeerInventoryRepository beerInventoryRepository;

  @Override
  public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
    log.debug("Allocating OrderId: {}", beerOrderDto.getId());
    AtomicInteger totalOrdered = new AtomicInteger();
    AtomicInteger totalAllocated = new AtomicInteger();

    beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
      Integer orderedQuantity = beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0;
      Integer allocatedQuantity = beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0;
      if (orderedQuantity - allocatedQuantity > 0) {
        allocateBeerOrderLine(beerOrderLine);
      }
      totalOrdered.set(totalOrdered.get() + (beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0));
      totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0));
    });

    log.debug("Total Ordered: {} Total Allocated: {}", totalOrdered.get(), totalAllocated.get());

    return totalOrdered.get() == totalAllocated.get();
  }

  @Override
  public void deallocateOrder(BeerOrderDto beerOrderDto) {
    beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
      BeerInventory beerInventory = BeerInventory.builder()
          .beerId(beerOrderLineDto.getBeerId())
          .upc(beerOrderLineDto.getUpc())
          .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
          .build();
      BeerInventory savedBeerInventory = beerInventoryRepository.save(beerInventory);
      log.debug("Saved Inventory for beer upc: {} inventory id: {}", savedBeerInventory.getUpc(), savedBeerInventory.getId());
    });
  }

  private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine) {
    List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLine.getUpc());

    beerInventoryList.forEach(beerInventory -> {
      int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
      int orderQty = (beerOrderLine.getOrderQuantity() == null) ? 0 : beerOrderLine.getOrderQuantity();
      int allocatedQty = (beerOrderLine.getQuantityAllocated() == null) ? 0 : beerOrderLine.getQuantityAllocated();
      int qtyToAllocate = orderQty - allocatedQty;

      if (inventory >= qtyToAllocate) { // full allocation
        inventory = inventory - qtyToAllocate;
        beerOrderLine.setQuantityAllocated(orderQty);
        beerInventory.setQuantityOnHand(inventory);

        beerInventoryRepository.save(beerInventory);
      } else if (inventory > 0) { //partial allocation
        beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
        beerInventory.setQuantityOnHand(0);
      }

      if (beerInventory.getQuantityOnHand() == 0) {
        beerInventoryRepository.delete(beerInventory);
      }
    });
  }
}
