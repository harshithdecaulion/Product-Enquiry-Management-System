package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "prod_name"))
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prodId;

	@Column(name = "prod_name")
	private String prodName;

	@Column(name = "prod_description")
	private String description;

	@Column(name = "no_of_prod_in_stock")
	private int noOfProductsInStock;

	private Boolean availability;

	private Boolean deleted = false;

	public Product() {
	}

	public Product(String prodName, String description, int noOfProductsInStock, Boolean availability,
			Boolean deleted) {
		super();
		this.prodName = prodName;
		this.description = description;
		this.noOfProductsInStock = noOfProductsInStock;
		this.availability = availability;
		this.deleted = deleted;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNoOfProductsInStock() {
		return noOfProductsInStock;
	}

	public void setNoOfProductsInStock(int noOfProductsInStock) {
		this.noOfProductsInStock = noOfProductsInStock;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
