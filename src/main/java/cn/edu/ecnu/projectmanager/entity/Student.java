package cn.edu.ecnu.projectmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private String stu_id;

    @Column(name = "stu_name")
    private String stu_name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth")
    private String birth;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "grade")
    private String grade;

    @Column(name = "major")
    private String major;


}
