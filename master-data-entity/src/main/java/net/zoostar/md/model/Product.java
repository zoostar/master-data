package net.zoostar.md.model;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Product implements Persistable<UUID> {
	
	private String assetId;
	
	private String sku;
	
	private String desc;
	
	private UUID id;
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	@JsonIgnore
	@Override
	public UUID getId() {
		return this.id;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isNew() {
		return this.id == null;
	}
	
	@Column(name="assetId", length = 50, nullable = false, unique = true)
	public String getAssetId() {
		return this.assetId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assetId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product) obj;
		return Objects.equals(assetId, other.assetId);
	}

}
