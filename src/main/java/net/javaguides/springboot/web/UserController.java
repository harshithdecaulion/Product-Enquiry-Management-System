package net.javaguides.springboot.web;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import net.javaguides.springboot.exception.EnquiryNotFoundException;
import net.javaguides.springboot.exception.EnquiryUpdateException;
import net.javaguides.springboot.model.Enquiries;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.EnquiriesService;
import net.javaguides.springboot.service.ProductService;
import net.javaguides.springboot.service.UserProductService;
import net.javaguides.springboot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	ProductService productService;

	@Autowired
	UserProductService userProductService;

	@Autowired
	EnquiriesService enquiriesService;

	@Autowired
	UserService userService;

	@GetMapping("/home")
	public String userHome(Model model, Principal principal) {
		UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
		String firstName = userDetails.getUsername();

		List<Product> availableProducts = userProductService.getAvailableProducts();

		model.addAttribute("user", userDetails);
		model.addAttribute("firstName", firstName);
		model.addAttribute("products", availableProducts); 
		return "user/userHome";
	}


	@PostMapping("/submit-enquiry")
	public String submitEnquiry(@RequestParam Long prodId, @RequestParam Integer quantity, Principal principal, Model model) {
	    Product product = productService.getProductById(prodId);
	    Enquiries enquiry = new Enquiries();
	    enquiry.setQuantity(quantity);
	    enquiry.setProduct(product);
	    String username = principal.getName();
	    User user = userService.getUserByEmail(username);
	    enquiry.setUser(user);
	    LocalDateTime currentDate = LocalDateTime.now();
	    enquiry.setEnquiryDate(currentDate);
	    enquiriesService.saveEnquiry(enquiry);
	    model.addAttribute("success", true);
	    return "redirect:/user/home";
	}
	
	   @GetMapping("/my-enquiries")
	    public String viewUserEnquiries(Model model, Principal principal) {
	        String username = principal.getName();
	        User user = userService.getUserByEmail(username);
	        List<Enquiries> userEnquiries = enquiriesService.getUserEnquiries(user);
	        model.addAttribute("userEnquiries", userEnquiries);
	        return "user/user-enquiries";
	    }
	
	
	   

	   @GetMapping("/modify-enquiries/{enquiryId}")
	   public String showModifyEnquiriesForm(Model model, @PathVariable Long enquiryId, Principal principal) {
	       String userEmail = principal.getName();
	       if (enquiriesService.isEnquiryEditable(enquiryId, userEmail)) {
	           Enquiries enquiry = enquiriesService.getEnquiryById(enquiryId);
	           model.addAttribute("enquiry", enquiry);
	           return "user/user-modify-enquiries"; // Replace with the actual view name
	       } else {
	           return "redirect:/user/home"; // Redirect to the enquiries page
	       }
	   }

	   // Update the quantity of the enquiry
	   @PostMapping("/modify-enquiries/{enquiryId}")
	   public String updateEnquiryQuantity(@PathVariable Long enquiryId, Principal principal, 
	                                       @RequestParam("quantity") int newQuantity) {
	       String userEmail = principal.getName();
	       enquiriesService.updateEnquiryQuantity(enquiryId, userEmail, newQuantity);
	       return "redirect:/user/home";
	   }

	   // Soft delete (cancel) the enquiry
	   @PostMapping("/cancel-enquiry/{enquiryId}")
	   public String cancelEnquiry(@PathVariable Long enquiryId, Principal principal) {
	       String userEmail = principal.getName();
	       enquiriesService.cancelEnquiry(enquiryId, userEmail);
	       return "redirect:/user/home";
	   }
	}
