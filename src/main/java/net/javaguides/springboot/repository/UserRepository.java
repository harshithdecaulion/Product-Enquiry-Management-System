package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
//	User findUserByEnquiryId(Long enquiryId);

	@Query("SELECT e.user FROM Enquiries e WHERE e.enquiryId = :enquiryId")
	User findUserByEnquiryId(@Param("enquiryId") Long enquiryId);
}
