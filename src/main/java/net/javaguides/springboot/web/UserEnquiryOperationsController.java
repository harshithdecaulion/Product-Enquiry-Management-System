//package net.javaguides.springboot.web;
//
//import java.security.Principal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import net.javaguides.springboot.model.Enquiries;
//
//import net.javaguides.springboot.model.Product;
//import net.javaguides.springboot.model.User;
//import net.javaguides.springboot.service.EnquiriesService;
//import net.javaguides.springboot.service.ProductService;
//import net.javaguides.springboot.service.UserProductService;
//import net.javaguides.springboot.service.UserService;
//
//@Controller
//@RequestMapping("/user")
//public class UserEnquiryOperationsController {
//
//	@Autowired
//	ProductService productService;
//	
//	@Autowired
//	UserProductService userProductService;
//	
//	@Autowired
//	EnquiriesService enquiriesService;
//	
//	@Autowired
//	UserService userService;
//	
//	@PostMapping("/submit-enquiry")
//	public String submitEnquiry(@RequestParam Long productId, @RequestParam Integer quantity, Principal principal) {
//	    Product product = productService.getProductById(productId);
//	    Enquiries enquiry = new Enquiries();
//	    enquiry.setQuantity(quantity);
//	    enquiry.setProduct(product);
//	    String username = principal.getName();
//	    User user = userService.getUserByEmail(username);
//	    enquiry.setUser(user);
//	    enquiriesService.saveEnquiry(enquiry);
//	    return "redirect:/user/home";
//	}
//	
//}
//
