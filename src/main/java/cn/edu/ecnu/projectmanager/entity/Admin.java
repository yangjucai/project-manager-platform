package cn.edu.ecnu.projectmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//primary key
    @Column(name = "adm_id")//login id
    private String adm_id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "adm_name", nullable = false)
    private String adm_name;

    @Column(name = "phone")
    private String phone;
}
