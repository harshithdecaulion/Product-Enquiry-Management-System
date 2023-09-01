package net.javaguides.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	

	
	@GetMapping("/login")
	public String home() {
		return "login";
	}
	@GetMapping("/admin")
    public String adminDashboard() {
        return "admin/adminDashboard"; 
    }  
	
	}
   

