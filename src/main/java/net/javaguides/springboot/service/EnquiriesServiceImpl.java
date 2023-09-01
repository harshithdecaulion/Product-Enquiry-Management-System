package net.javaguides.springboot.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.exception.EnquiryUpdateException;
import net.javaguides.springboot.model.Enquiries;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.repository.EnquiriesRepository;

@Service
public class EnquiriesServiceImpl implements EnquiriesService {

	@Autowired
	EnquiriesRepository enquiriesRepository;

	public void saveEnquiry(Enquiries enquiries) {
		enquiriesRepository.save(enquiries);
	}

	@Override
	public List<Enquiries> getAllEnquiries() {
		return enquiriesRepository.findAll();
	}

	@Override
	public List<Enquiries> getUserEnquiries(User user) {
		List<Enquiries> userEnquiries = enquiriesRepository.findByUser(user);
		;
		userEnquiries = userEnquiries.stream().filter(enquiry -> enquiry.isStatus()) // Only keep enquiries with status
																						// true
				.collect(Collectors.toList());

		return userEnquiries;
	}

//	@Override
//	public void updateEnquiry(Enquiries enquiry) throws EnquiryUpdateException {
//	    LocalDateTime currentTime = LocalDateTime.now();
//	    LocalDateTime creationTime = enquiry.getEnquiryDate();
//	    Duration duration = Duration.between(creationTime, currentTime);
//	    
//	    if (duration.toHours() > 24) {
//	        throw new EnquiryUpdateException("Enquiry cannot be updated after 24 hours.");
//	    }
//	    
//	}
	@Override
	public Enquiries getEnquiryById(Long enquiryId) {
		return enquiriesRepository.findById(enquiryId).orElse(null);
	}

//	@Override
//	public boolean isEnquiryEditable(Long enquiryId, User user) {
//	    Optional<Enquiries> optionalEnquiry = enquiriesRepository.findById(enquiryId);
//	    
//	    if (optionalEnquiry.isPresent()) {
//	        Enquiries enquiry = optionalEnquiry.get();
//	        
//	        if (enquiry.getUser().equals(user)) {
//	            LocalDateTime creationTime = enquiry.getEnquiryDate();
//	            LocalDateTime now = LocalDateTime.now();
//	            
//	            // Allow editing within 24 hours of creation
//	            return Duration.between(creationTime, now).toHours() < 24;
//	        }
//	    }
//	    return false;
//	}

	@Override
	public boolean isEnquiryEditable(Long enquiryId, String userEmail) {
		Enquiries enquiry = enquiriesRepository.findById(enquiryId).orElse(null);
		if (enquiry != null) {
			LocalDateTime creationTime = enquiry.getEnquiryDate();
			LocalDateTime now = LocalDateTime.now();
			// Allow editing within 24 hours of creation and if the user's email matches
			return Duration.between(creationTime, now).toHours() < 24 && userEmail.equals(enquiry.getUser().getEmail());
		}
		return false;
	}

	@Override
	public void updateEnquiryQuantity(Long enquiryId, String userEmail, int newQuantity) {
		if (isEnquiryEditable(enquiryId, userEmail)) {
			Enquiries enquiry = enquiriesRepository.findById(enquiryId).orElse(null);
			if (enquiry != null) {
				enquiry.setQuantity(newQuantity);
				enquiriesRepository.save(enquiry);
			}
		}
	}

	@Override
	public void cancelEnquiry(Long enquiryId, String userEmail) {
		if (isEnquiryEditable(enquiryId, userEmail)) {
			Enquiries enquiry = enquiriesRepository.findById(enquiryId).orElse(null);
			if (enquiry != null) {
				// Update the status to cancelled (false)
				enquiry.setStatus(false);
				enquiriesRepository.save(enquiry);
			}
		}
	}

//
//    @Override
//    public List<Enquiries> getEnquiries24HoursOldAndActive() {
//        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
//        return enquiriesRepository.findByEnquiryDateBeforeAndStatusIsTrue(twentyFourHoursAgo);
//    }
	@Override
	public List<Enquiries> getEnquiries24HoursOldAndActive() {
		LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
		return enquiriesRepository.findByEnquiryDateBeforeAndStatusIsTrueAndQuotationStatusIsFalse(twentyFourHoursAgo);
	}

}
