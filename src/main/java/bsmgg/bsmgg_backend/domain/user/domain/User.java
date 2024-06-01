package bsmgg.bsmgg_backend.domain.user.domain;

import bsmgg.bsmgg_backend.domain.user.controller.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String puuid;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer code;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
    private Boolean isGraduate;
    @Column(nullable = true)
    private Integer enrolledAt;
    @Column(nullable = true)
    private Integer grade;
    @Column(nullable = true)
    private Integer classNo;
    @Column(nullable = true)
    private Integer studentNo;

}
