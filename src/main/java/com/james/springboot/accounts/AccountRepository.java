package com.james.springboot.accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

//	Optional<Account> findbyEmail(String email);  // Caused error when creating beans
	Optional<Account> findByEmail(String email);
}
