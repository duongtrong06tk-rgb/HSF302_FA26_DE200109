package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;

import java.util.*;

public interface CourseService {

    long count();
    List<Course> findAllOrderByCode();
    Optional<Course> findById(Long id);

}
