package net.javaguides.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Quotations;
import net.javaguides.springboot.repository.QuotationsRepository;

@Service
public class QuotationServiceImpl implements QuotationService {

	@Autowired
	QuotationsRepository quotationsRepository;

	
	@Override
	public void saveQuotation(Quotations quotation) {
		quotationsRepository.save(quotation);
	}

}
