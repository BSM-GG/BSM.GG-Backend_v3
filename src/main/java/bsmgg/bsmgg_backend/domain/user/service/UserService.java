package bsmgg.bsmgg_backend.domain.user.service;

import bsmgg.bsmgg_backend.domain.oauth.BsmOauthService;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerGetService;
import bsmgg.bsmgg_backend.domain.user.controller.dto.AssignRequestDto;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.repository.UserRepository;
import bsmgg.bsmgg_backend.global.jwt.dto.TokenDto;
import bsmgg.bsmgg_backend.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BsmOauthService bsmOauthService;
    private final SummonerGetService summonerGetService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RiotApiService riotApiService;

    public TokenDto assign(AssignRequestDto dto) {
        User oauthUser = bsmOauthService.oauth(dto.authCode());
        User user = userRepository.findByEmail(oauthUser.getEmail());
        if (user == null) {
            userRepository.save(oauthUser);
            return jwtUtil.createToken(oauthUser.getUuid(), "", "");
        }
        user.update(oauthUser);

        if (user.getSummoner() == null) {
            return jwtUtil.createToken(user.getUuid(), "", "");
        }
        Summoner summoner = summonerGetService.getSummonerByPuuid(user.getSummoner().getPuuid());
        return jwtUtil.createToken(user.getUuid(), summoner.getGameName(), summoner.getTagLine());

    }
}
