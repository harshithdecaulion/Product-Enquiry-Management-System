package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Quotations;

public interface QuotationsRepository extends JpaRepository<Quotations, Long> {



}
