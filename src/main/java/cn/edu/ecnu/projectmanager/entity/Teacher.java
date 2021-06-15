package cn.edu.ecnu.projectmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tea_id")
    private String tea_id;

    @Column(name = "tea_name", nullable = false)
    private String tea_name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

}
