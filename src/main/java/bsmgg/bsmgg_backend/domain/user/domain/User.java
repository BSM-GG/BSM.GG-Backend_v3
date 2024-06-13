package bsmgg.bsmgg_backend.domain.user.domain;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import jakarta.persistence.*;
import leehj050211.bsmOauth.type.BsmUserRole;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="puuid")
    private Summoner summoner;

    @Column(nullable = false, unique = true, length = 32)
    private String email;
    @Column(nullable = false, length = 8)
    private Long code;
    @Column(nullable = false, length = 30)
    private String nickname;
    @Setter
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false)
    private String role;

    @Setter
    @Column
    private Boolean isGraduate = false;
    @Setter
    private Integer enrolledAt = 0;
    @Setter
    @Column(length = 1)
    private Integer grade = 0;
    @Setter
    @Column(length = 1)
    private Integer classNo = 0;
    @Setter
    @Column(length = 2)
    private Integer studentNo = 0;

    public void update(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.isGraduate = user.getIsGraduate();
        this.enrolledAt = user.getEnrolledAt();
        this.grade = user.getGrade();
        this.classNo = user.getClassNo();
        this.studentNo = user.getStudentNo();
    }
}
