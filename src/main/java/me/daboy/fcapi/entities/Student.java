package me.daboy.fcapi.entities;

import me.daboy.fcapi.utils.WorkType;

import javax.persistence.*;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long admin_id;
    @Column(name="fullname")
    private String fullName;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String country;
    @Column
    private String city;
    @Column(name="can_move")
    private Boolean canMove;
    @Column(name="work_type")
    @Enumerated(EnumType.STRING)
    private WorkType workType;
    @Column
    private String photo;
    @Column
    private String curriculum;
    @Column
    private String tags;

    public Student() {
    }

    public Student(Long id, Long admin_id, String fullName, String email, String phone, String country, String city, Boolean canMove, WorkType workType, String photo, String curriculum, String tags) {
        this.id = id;
        this.admin_id = admin_id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.canMove = canMove;
        this.workType = workType;
        this.photo = photo;
        this.curriculum = curriculum;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getCanMove() {
        return canMove;
    }

    public void setCanMove(Boolean canMove) {
        this.canMove = canMove;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
