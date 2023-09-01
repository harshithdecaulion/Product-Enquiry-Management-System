package net.javaguides.springboot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Enquiries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enquiryId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private LocalDateTime enquiryDate;

	private int quantity;

	private boolean status = true;

	private boolean quotationStatus = false;

	public Enquiries() {
	}

	public Enquiries(User user, Product product, LocalDateTime enquiryDate, int quantity, boolean status,
			boolean quotationStatus) {
		super();
		this.user = user;
		this.product = product;
		this.enquiryDate = enquiryDate;
		this.quantity = quantity;
		this.status = status;
		this.quotationStatus = quotationStatus;
	}

	public boolean isQuotationStatus() {
		return quotationStatus;
	}

	public void setQuotationStatus(boolean quotationStatus) {
		this.quotationStatus = quotationStatus;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(Long enquiryId) {
		this.enquiryId = enquiryId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LocalDateTime getEnquiryDate() {
		return enquiryDate;
	}

	public void setEnquiryDate(LocalDateTime currentDate) {
		this.enquiryDate = currentDate;
	}

}
