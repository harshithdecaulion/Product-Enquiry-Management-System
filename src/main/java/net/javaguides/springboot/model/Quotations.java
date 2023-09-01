package net.javaguides.springboot.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quotations")
public class Quotations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quotationId;

    @ManyToOne
    @JoinColumn(name = "enquiry_id", nullable = false)
    private Enquiries enquiries;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal quotationAmount;

    @Enumerated(EnumType.STRING)
    private QuotationStatus status;

    private boolean quotationSent=false;

public enum QuotationStatus {
    APPROVED,
    STANDBY
}




public Quotations(Enquiries enquiries, User user, BigDecimal quotationAmount, QuotationStatus status,
		boolean quotationSent) {
	super();
	this.enquiries = enquiries;
	this.user = user;
	this.quotationAmount = quotationAmount;
	this.status = status;
	this.quotationSent = quotationSent;
}



public Quotations() {
}



public boolean isQuotationSent() {
	return quotationSent;
}



public void setQuotationSent(boolean quotationSent) {
	this.quotationSent = quotationSent;
}



public Long getQuotationId() {
	return quotationId;
}

public void setQuotationId(Long quotationId) {
	this.quotationId = quotationId;
}

public Enquiries getEnquiries() {
	return enquiries;
}

public void setEnquiries(Enquiries enquiries) {
	this.enquiries = enquiries;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public BigDecimal getQuotationAmount() {
	return quotationAmount;
}

public void setQuotationAmount(BigDecimal quotationAmount) {
	this.quotationAmount = quotationAmount;
}

public QuotationStatus getStatus() {
	return status;
}

public void setStatus(QuotationStatus status) {
	this.status = status;
}
}
