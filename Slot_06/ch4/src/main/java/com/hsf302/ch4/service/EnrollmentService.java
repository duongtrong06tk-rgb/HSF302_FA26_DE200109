package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.*;

import java.util.*;

public interface EnrollmentService {

    List<Course> getCoursesOfStudent(String studentCode);
    List<Student> getStudentsOfCourse(String courseCode);

}
