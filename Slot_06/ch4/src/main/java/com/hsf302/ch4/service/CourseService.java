package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;

import java.util.*;

public interface CourseService {

    //TODO 7
    long count();
    List<Course> findAllOrderByCode();
    Optional<Course> findById(Long id);

    //TODO 8
    Optional<Course> findByCode(String code);
    List<Course> findBySemester(String semester);
    long countBySemester(String semester);

    //TODO 9
    List<Course> findCoursesOfStudent(String studentCode);
    List<Course> findCoursesOfDepartment(String deptCode, boolean distinct);

    //TODO 11
    List<Course> findCoursesWithoutStudents();
}
