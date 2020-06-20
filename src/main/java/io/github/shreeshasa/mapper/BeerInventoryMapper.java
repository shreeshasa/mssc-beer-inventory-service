package io.github.shreeshasa.mapper;

import io.github.shreeshasa.domain.BeerInventory;
import io.github.shreeshasa.model.BeerInventoryDto;
import org.mapstruct.Mapper;

/**
 * @author shreeshasa
 */
@Mapper (uses = DateMapper.class)
public interface BeerInventoryMapper {

  BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

  BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
