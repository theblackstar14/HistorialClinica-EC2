package com.historias.clinicas.entity;

import jakarta.persistence.*;
import java.time.*;
import com.historias.clinicas.entity.enums.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @Column(name = "hash_password", nullable = false, length = 512)
    private String hashPassword;

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private LocalDate dateOfBirth;
    private String   phone;
    private String   email;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /* ----- constructores ----- */
    public UserEntity() {}

    public UserEntity(String username, String hashPwd,
                      String first,   String last,
                      Sex sex,        LocalDate dob,
                      String phone,   String email) {
        this.username     = username;
        this.hashPassword = hashPwd;
        this.firstName    = first;
        this.lastName     = last;
        this.sex          = sex;
        this.dateOfBirth  = dob;
        this.phone        = phone;
        this.email        = email;
    }

    /* ----- getters / setters ----- */
    public Integer getUserId()                  { return userId; }
    public void    setUserId(Integer id)        { this.userId = id; }

    public String  getUsername()                { return username; }
    public void    setUsername(String u)        { this.username = u; }

    public String  getHashPassword()            { return hashPassword; }
    public void    setHashPassword(String h)    { this.hashPassword = h; }

    public String  getFirstName()               { return firstName; }
    public void    setFirstName(String f)       { this.firstName = f; }

    public String  getLastName()                { return lastName; }
    public void    setLastName(String l)        { this.lastName = l; }

    public Sex     getSex()                     { return sex; }
    public void    setSex(Sex s)                { this.sex = s; }

    public LocalDate getDateOfBirth()           { return dateOfBirth; }
    public void      setDateOfBirth(LocalDate d){ this.dateOfBirth = d; }

    public String  getPhone()                   { return phone; }
    public void    setPhone(String p)           { this.phone = p; }

    public String  getEmail()                   { return email; }
    public void    setEmail(String e)           { this.email = e; }

    public LocalDateTime getCreatedAt()         { return createdAt; }
    public void          setCreatedAt(LocalDateTime t){ this.createdAt = t; }
}
