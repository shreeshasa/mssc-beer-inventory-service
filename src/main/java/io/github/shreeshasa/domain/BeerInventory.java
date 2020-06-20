package io.github.shreeshasa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author shreeshasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerInventory {

  @Id
  @GeneratedValue (generator = "UUID")
  @GenericGenerator (
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Type (type = "org.hibernate.type.UUIDCharType")
  @Column (length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
  private UUID id;

  @Version
  private Long version;

  @CreationTimestamp
  @Column (updatable = false)
  private Timestamp createdDate;

  @UpdateTimestamp
  private Timestamp lastModifiedDate;

  @Type (type = "org.hibernate.type.UUIDCharType")
  @Column (length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
  private UUID beerId;

  private String upc;
  private Integer quantityOnHand = 0;
}
