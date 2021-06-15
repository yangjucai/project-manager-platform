package cn.edu.ecnu.projectmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Integer pro_id;

    @Column(name = "pro_name", nullable = false)
    private String pro_name; // 项目名称

    @Column(name = "status", nullable = false)
    private String status; // current status of the project

    @Column(name = "grades")
    private Integer grades; // 项目分数

    @Column(name = "type", nullable = false)
    private String type; // the type of the project

    @Column(name = "team_id")
    private Integer team_id;

    @Column(name = "exp_id")
    private String exp_id;

    @Column(name = "exp_comment")
    private String exp_comment;

    @Column(name = "tea_id")
    private String tea_id;

    @Column(name = "tea_comment")
    private String tea_comment;



}
