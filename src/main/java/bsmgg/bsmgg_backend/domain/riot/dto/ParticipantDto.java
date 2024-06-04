package bsmgg.bsmgg_backend.domain.riot.dto;

public record ParticipantDto(
        Integer allInPings,
        Integer assistMePings,
        Integer assists,
        Integer baronKills,
        Integer bountyLevel,
        Integer champExperience,
        Integer champLevel,
        Integer championId,
        String championName,
        Integer commandPings,
        Integer championTransform,
        Integer consumablesPurchased,
        ChallengesDto challenges,
        Integer damageDealtToBuildings,
        Integer damageDealtToObjectives,
        Integer damageDealtToTurrets,
        Integer dangerPings,
        Integer damageSelfMitigated,
        Integer deaths,
        Integer detectorWardsPlaced,
        Integer doubleKills,
        Integer dragonKills,
        Boolean eligibleForProgression,
        Integer enemyMissingPings,
        Integer enemyVisionPings,
        Boolean firstBloodAssist,
        Boolean firstBloodKill,
        Boolean firstTowerAssist,
        Boolean firstTowerKill,
        Boolean gameEndedInEarlySurrender,
        Boolean gameEndedInSurrender,
        Integer holdPings,
        Integer getBackPings,
        Integer goldEarned,
        Integer goldSpent,
        String individualPosition,
        Integer inhibitorKills,
        Integer inhibitorTakedowns,
        Integer inhibitorsLost,
        Integer item0,
        Integer item1,
        Integer item2,
        Integer item3,
        Integer item4,
        Integer item5,
        Integer item6,
        Integer itemsPurchased,
        Integer killingSprees,
        Integer kills,
        String lane,
        Integer largestCriticalStrike,
        Integer largestKillingSpree,
        Integer largestMultiKill,
        Integer longestTimeSpentLiving,
        Integer magicDamageDealt,
        Integer magicDamageDealtToChampions,
        Integer magicDamageTaken,
        MissionsDto missions,
        Integer neutralMinionsKilled,
        Integer needVisionPings,
        Integer nexusKills,
        Integer nexusTakedowns,
        Integer nexusLost,
        Integer objectivesStolen,
        Integer objectivesStolenAssists,
        Integer onMyWayPings,
        Integer participantId,
        Integer pentaKills,
        PerksDto perks,
        Integer physicalDamageDealt,
        Integer physicalDamageDealtToChampions,
        Integer physicalDamageTaken,
        Integer placement,
        Integer playerAugment1,
        Integer playerAugment2,
        Integer playerAugment3,
        Integer playerAugment4,
        Integer playerSubteamId,
        Integer pushPings,
        Integer profileIcon,
        String puuid,
        Integer quadraKills,
        String riotIdGameName,
        String riotIdName,
        String riotIdTagline,
        String role,
        Integer sightWardsBoughtInGame,
        Integer spell1Casts,
        Integer spell2Casts,
        Integer spell3Casts,
        Integer spell4Casts,
        Integer subteamPlacement,
        Integer summoner1Casts,
        Integer summoner1Id,
        Integer summoner2Casts,
        Integer summoner2Id,
        Integer summonerId,
        Integer summonerLevel,
        String summonerName,
        Boolean teamEarlySurrendered,
        Integer teamId,
        String teamPosition,
        Integer timeCCingOthers,
        Integer timePlayed,
        Integer totalAllyJungleMinionsKilled,
        Integer totalDamageDealt,
        Integer totalDamageDealtToChampions,
        Integer totalDamageShieldedOnTeammates,
        Integer totalDamageTaken,
        Integer totalEnemyJungleMinionsKilled,
        Integer totalHeal,
        Integer totalHealsOnTeammates,
        Integer totalMinionsKilled,
        Integer totalTimeCCDealt,
        Integer totalTimeSpentDead,
        Integer totalUnitsHealed,
        Integer tripleKills,
        Integer trueDamageDealt,
        Integer trueDamageDealtToChampions,
        Integer trueDamageTaken,
        Integer turretKills,
        Integer turretTakedowns,
        Integer turretsLost,
        Integer unrealKills,
        Integer visionScore,
        Integer visionClearedPings,
        Integer visionWardsBoughtInGame,
        Integer wardsKilled,
        Integer wardsPlaced,
        Integer win
) {
}
