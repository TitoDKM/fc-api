package me.daboy.fcapi.repositories;

import me.daboy.fcapi.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    Page<Student> findByFullNameContainingIgnoreCase(String filter, Pageable pageable);
    Page<Student> findByCityContainingOrFullNameContainingOrCountryContainingOrEmailContainingOrPhoneContaining(String city, String name, String country, String email, String phone, Pageable pageable);
}
