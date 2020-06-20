package io.github.shreeshasa.model.event;

import io.github.shreeshasa.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shreeshasa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

  private static final long serialVersionUID = -2987555597206167164L;

  private BeerDto beerDto;
}
