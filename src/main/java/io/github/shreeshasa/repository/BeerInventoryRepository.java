package io.github.shreeshasa.repository;

import io.github.shreeshasa.domain.BeerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author shreeshasa
 */
public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

  List<BeerInventory> findAllByBeerId(UUID beerId);

  List<BeerInventory> findAllByUpc(String upc);
}
