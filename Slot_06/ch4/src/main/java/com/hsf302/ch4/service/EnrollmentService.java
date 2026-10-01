package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.*;

import java.util.*;

public interface EnrollmentService {

    List<Course> getCoursesOfStudent(String studentCode);
    List<Student> getStudentsOfCourse(String courseCode);

    //TODO 9
    List<Student> findStudentsInCourse(String courseCode);
    long countStudentsInCourse(String courseCode);
    List<Student> findActiveStudentsInCourse(String courseCode);
}
