package cn.edu.ecnu.projectmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer file_id;

    @Column(name = "file_name", nullable = false)
    private String file_name;

    @Column(name = "url", nullable = false)
    private String url;  // file path

    @Column(name="pro_id",nullable = false)
    private Integer pro_id;//project_id

    public File(){
    }
    public File(String file_name, String url){
        this.file_name = file_name;
        this.url = url;
    }
}
