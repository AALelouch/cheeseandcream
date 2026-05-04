package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.invoice.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

}
