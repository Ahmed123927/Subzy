package com.Subzy.demo.Repository;

import com.Subzy.demo.Enitiy.Invoice;
import com.Subzy.demo.Utils.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i " +
            "WHERE i.subscription.plan.company.id = :companyId AND i.status = :status")
    double sumByCompanyAndStatus(@Param("companyId") Long companyId,
                                 @Param("status") InvoiceStatus status);}
