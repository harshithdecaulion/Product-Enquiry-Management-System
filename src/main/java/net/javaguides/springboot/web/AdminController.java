package net.javaguides.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Quotations;
import net.javaguides.springboot.model.Enquiries;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.Quotations.QuotationStatus;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.EmailService;
import net.javaguides.springboot.service.EnquiriesService;
import net.javaguides.springboot.service.ProductService;
import net.javaguides.springboot.service.QuotationService;
import net.javaguides.springboot.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ProductService productService;

	@Autowired
	EnquiriesService enquiriesService;

	@Autowired
	UserService userService;

	@Autowired
	QuotationService quotationService;

	@Autowired
	EmailService emailService;
// ...

	public String formatLocalDateTime(LocalDateTime dateTime, String format) {
		if (dateTime != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return dateTime.format(formatter);
		}
		return "";
	}

	@GetMapping("/product-list")
	public String productList(Model model) {
		List<Product> products = productService.getAllUndeletedProducts();
		model.addAttribute("products", products);
		return "admin/product-page";
	}

	@GetMapping("/add")
	public String showAddProductForm(Model model) {
		model.addAttribute("product", new Product());
		return "/admin/add-product";
	}

	@PostMapping("/add")
	public String addProduct(@ModelAttribute("product") Product product) {
		productService.saveProduct(product);
		return "redirect:/admin/product-list";
	}

	@PostMapping("/soft-delete")
	public String softDeleteProduct(@RequestParam("prodId") Long prodId) {
		productService.softDeleteProduct(prodId);
		return "redirect:/admin/product-list";
	}

	@GetMapping("/update-product/{prodId}")
	public String showEditProductForm(@PathVariable Long prodId, Model model) {
		Product product = productService.getProductById(prodId);
		model.addAttribute("product", product);
		return "admin/edit-product";
	}

	@PostMapping("/update-product")
	public String updateProduct(@ModelAttribute Product product) {
		productService.updateProduct(product);
		return "redirect:/admin/product-list";
	}

	@GetMapping("/enquiries")
	public String viewEnquiries(Model model) {
		List<Enquiries> enquiries = enquiriesService.getAllEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "admin/admin-enquiries";
	}

	@GetMapping("/active-enquiries")
	public String viewActiveEnquiries(Model model) {
		List<Enquiries> activeEnquiries = enquiriesService.getEnquiries24HoursOldAndActive();
		model.addAttribute("activeEnquiries", activeEnquiries);
		return "admin/admin-active-enquiries"; // Replace with the actual view name
	}

  

	@GetMapping("/send-quotation/{enquiryId}") // Use "enquiryId" consistently here
	public String openQuotationFormForEmail(@PathVariable Long enquiryId, Model model) {
		Enquiries enquiry = enquiriesService.getEnquiryById(enquiryId);

		// Add the Enquiries object to the model
		model.addAttribute("enquiry", enquiry);

		// Return the view name for the form
		return "admin/send-quotation-form";
	}

//	@PostMapping("/send-quotation/{enquiryId}") // Use "enquiryId" consistently here as well
//	public String processQuotationForm(
//	    @PathVariable Long enquiryId,
//	    @RequestParam("approval") QuotationStatus approval,
//	    @RequestParam("amount") double amount,
//	    Principal principal
//	) {
//	    String adminEmail = principal.getName();
//	    User adminUser = userService.getUserByEmail(adminEmail);
//	    User user = userService.getUserByEnquiryId(enquiryId);
//	    Quotations quotation = new Quotations();
//	    quotation.setEnquiries(enquiriesService.getEnquiryById(enquiryId)); // Set the associated Enquiries object
//	    quotation.setUser(user); // Set the associated User object
//	    quotation.setQuotationAmount(BigDecimal.valueOf(amount)); // Set the quotation amount
//	    quotation.setStatus(approval); // Set the approval status
//	    quotationService.saveQuotation(quotation);
//	    String subject = "Quotation For Product";
//	    String message = "Your quotation has been updated. Approval: " + approval + ", Amount: " + amount;
//	    emailService.sendEmail(user.getEmail(), subject, message);
//		return "redirect:/admin/product-list";
//	}
	@PostMapping("/send-quotation/{enquiryId}")
	public String processQuotationForm(@PathVariable Long enquiryId, @RequestParam("approval") QuotationStatus approval,
			@RequestParam("amount") double amount, Principal principal) {

		String adminEmail = principal.getName();
		User adminUser = userService.getUserByEmail(adminEmail);
		User user = userService.getUserByEnquiryId(enquiryId);
		Quotations quotation = new Quotations();
		quotation.setEnquiries(enquiriesService.getEnquiryById(enquiryId));
		quotation.setUser(user);
		quotation.setQuotationAmount(BigDecimal.valueOf(amount));
		quotation.setStatus(approval);
		quotation.setQuotationSent(true); 
		quotationService.saveQuotation(quotation);

		   String subject;
		    String message;

		    if (approval == QuotationStatus.APPROVED) {
		        subject = "Quotation Approval";
		        message = "Your quotation has been approved. Amount: " + amount;
		    } else {
		        subject = "Quotation Standby";
		        message = "Your request is in standby. Once it is approved, you will be notified.";
		    }

		    emailService.sendEmail(user.getEmail(), subject, message);


		Enquiries enquiry = enquiriesService.getEnquiryById(enquiryId);
		enquiry.setQuotationStatus(true);
		enquiriesService.saveEnquiry(enquiry);

		return "redirect:/admin/product-list";
	}

}
