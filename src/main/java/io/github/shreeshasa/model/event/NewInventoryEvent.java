package io.github.shreeshasa.model.event;

import io.github.shreeshasa.model.BeerDto;
import lombok.NoArgsConstructor;

/**
 * @author shreeshasa
 */
@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

  public NewInventoryEvent(BeerDto beerDto) {
    super(beerDto);
  }
}
