package com.Subzy.demo.Repository;

import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findAllByCompanies_Id(Long companyId);

    @Query("SELECT COUNT(c) FROM Client c JOIN c.companies comp WHERE comp.id = :companyId")
    long countByCompanyId(@Param("companyId") Long companyId);
}
