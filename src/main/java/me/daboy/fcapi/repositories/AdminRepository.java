package me.daboy.fcapi.repositories;

import me.daboy.fcapi.entities.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findAdminByEmail(String email);
}
