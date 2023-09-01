package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.exception.EnquiryUpdateException;
import net.javaguides.springboot.model.Enquiries;
import net.javaguides.springboot.model.User;

public interface EnquiriesService {

	void saveEnquiry(Enquiries enquiry);

	List<Enquiries> getAllEnquiries();

	List<Enquiries> getUserEnquiries(User user);

	Enquiries getEnquiryById(Long enquiryId);

	boolean isEnquiryEditable(Long enquiryId, String userEmail);

	void updateEnquiryQuantity(Long enquiryId, String userEmail, int newQuantity);

	void cancelEnquiry(Long enquiryId, String userEmail);


	List<Enquiries> getEnquiries24HoursOldAndActive();

}