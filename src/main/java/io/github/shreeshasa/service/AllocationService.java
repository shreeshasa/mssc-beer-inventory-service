package io.github.shreeshasa.service;

import io.github.shreeshasa.model.BeerOrderDto;

/**
 * @author shreeshasa
 */
public interface AllocationService {

  Boolean allocateOrder(BeerOrderDto beerOrderDto);

  void deallocateOrder(BeerOrderDto beerOrderDto);
}
