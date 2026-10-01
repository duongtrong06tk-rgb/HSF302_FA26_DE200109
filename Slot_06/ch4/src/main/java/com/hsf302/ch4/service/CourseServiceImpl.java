package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    // cài đặt dần từ TODO 6
    @Override
    public long count() {
        return courseRepository.count();
    }

    @Override
    public List<Course> findAllOrderByCode() {
        return courseRepository.findAll(Sort.by("code"));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
}
