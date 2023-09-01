package net.javaguides.springboot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Enquiries;
import net.javaguides.springboot.model.User;

public interface EnquiriesRepository extends JpaRepository<Enquiries, Long> {

    List<Enquiries> findByUser(User user);


	List<Enquiries> getUserEnquiriesByUser(User user);


	List<Enquiries> findByEnquiryDateBeforeAndStatusIsTrue(LocalDateTime twentyFourHoursAgo);


	List<Enquiries> findByEnquiryDateBeforeAndStatusIsTrueAndQuotationStatusIsFalse(LocalDateTime twentyFourHoursAgo);
}
