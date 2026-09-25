package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.*;

public interface StudentService {
    // Các method được bổ sung dần từ TODO 6
    long count();                                   // TODO 6
    Optional<Student> findById(Long id);            // TODO 6

    List<Student> findAllOrderByGpaDesc();                              // TODO 7a
    Page<Student> findPage(int pageIndex, int size, String sortField);  // TODO 7b
}
