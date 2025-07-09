package com.Subzy.demo.Repository;

import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
