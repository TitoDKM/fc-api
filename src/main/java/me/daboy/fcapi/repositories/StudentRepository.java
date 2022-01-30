package me.daboy.fcapi.repositories;

import me.daboy.fcapi.entities.Student;
import me.daboy.fcapi.utils.WorkType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    Page<Student> findByFullNameContainingIgnoreCase(String filter, Pageable pageable);
    Page<Student> findByCityContainingOrFullNameContainingOrCountryContainingOrEmailContainingOrPhoneContaining(String city, String name, String country, String email, String phone, Pageable pageable);

    @Query(value = "SELECT * FROM students WHERE (country = :country AND city = :city AND work_type = :workType AND can_move = :canMove)", nativeQuery = true)
    Page<Student> customSearch(String country, String city, String workType, Boolean canMove, Pageable pageable);
}
