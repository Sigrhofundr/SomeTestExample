package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

//https://www.youtube.com/watch?v=Geq60OVyBPg esiste anche una versione più rapida di mockito a circa 53:00

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    //private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
      //  autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }
    /*
    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }*/

    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();

    }

    @Test
    void canAddStudent() {
        //given
        Student student = new Student(
                "Jamila", "jamila@gmail.com", Gender.FEMALE
        );
        //when
        underTest.addStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        Student student = new Student(
                "Jamila", "jamila@gmail.com", Gender.FEMALE
        );

        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        //when
        //then
        assertThatThrownBy(()->underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        //this verify that no student is saved
        verify(studentRepository, never()).save(any());

    }

    @Test //missing
    void deleteStudent() {
        Student student = new Student(
                "Jamila", "jamila@gmail.com", Gender.FEMALE
        );
        //when
        underTest.addStudent(student);

        List<Student> students = new ArrayList<>(underTest.getAllStudents()) {
        };

        Long numero = null;
        for (Student x : students) {
            if (x.getEmail().equals("jamila@gmail.com")) {
                numero = x.getId();
                break;
            }
        }
        //underTest.deleteStudent(numero);
        //then
        Long finalNumero = numero;
        assertThatThrownBy(()->underTest.deleteStudent(finalNumero))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + numero + " does not exists");
    }
}