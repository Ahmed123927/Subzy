package com.Subzy.demo.Repository;

import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
