package bsmgg.bsmgg_backend.global.mapping;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MappingService {

    private Map<Integer, String> perkMap;
    private Map<Integer, String> spellMap;
    private Map<Integer, String> queueMap;

    public MappingService() {
        initPerkMap();
        initSpellMap();
        initQueueMap();
    }

    private void initPerkMap() {
        perkMap = new HashMap<>();

        perkMap.put(8369, "First Strike");
        perkMap.put(8446, "Demolish");
        perkMap.put(8126, "Cheap Shot");
        perkMap.put(8321, "Future's Market");
        perkMap.put(8415, "The Arcane Colossus");
        perkMap.put(8410, "Approach Velocity");
        perkMap.put(8232, "Waterwalking");
        perkMap.put(8299, "Last Stand");
        perkMap.put(8112, "Electrocute");
        perkMap.put(8234, "Celerity");
        perkMap.put(8453, "Revitalize");
        perkMap.put(8360, "Unsealed Spellbook");
        perkMap.put(8004, "The Brazen Perfect");
        perkMap.put(8128, "Dark Harvest");
        perkMap.put(8220, "The Calamity");
        perkMap.put(8016, "The Merciless Elite");
        perkMap.put(8473, "Bone Plating");
        perkMap.put(8339, "Celestial Body");
        perkMap.put(8214, "Summon Aery");
        perkMap.put(8237, "Scorch");
        perkMap.put(8139, "Taste of Blood");
        perkMap.put(8008, "Lethal Tempo");
        perkMap.put(9105, "Legend: Tenacity");
        perkMap.put(8010, "Conqueror");
        perkMap.put(8106, "Ultimate Hunter");
        perkMap.put(8017, "Cut Down");
        perkMap.put(8224, "Nullifying Orb");
        perkMap.put(8210, "Transcendence");
        perkMap.put(8005, "Press the Attack");
        perkMap.put(8435, "Mirror Shell");
        perkMap.put(8115, "The Aether Blade");
        perkMap.put(8359, "Kleptomancy");
        perkMap.put(8352, "Time Warp Tonic");
        perkMap.put(5003, "Magic Resist");
        perkMap.put(8135, "Treasure Hunter");
        perkMap.put(8120, "Ghost Poro");
        perkMap.put(8134, "Ingenious Hunter");
        perkMap.put(8351, "Glacial Augment");
        perkMap.put(8242, "Unflinching");
        perkMap.put(8401, "Shield Bash");
        perkMap.put(9111, "Triumph");
        perkMap.put(8105, "Relentless Hunter");
        perkMap.put(8454, "The Leviathan");
        perkMap.put(8275, "Nimbus Cloak");
        perkMap.put(8207, "The Cryptic");
        perkMap.put(5012, "Resist Scaling");
        perkMap.put(8439, "Aftershock");
        perkMap.put(8109, "The Wicked Maestro ");
        perkMap.put(5002, "Armor");
        perkMap.put(5011, "Health");
        perkMap.put(5013, "Tenacity and Slow Resist");
        perkMap.put(8414, "The Behemoth");
        perkMap.put(5008, "Adaptive Force");
        perkMap.put(8320, "The Timeless");
        perkMap.put(8319, "The Stargazer");
        perkMap.put(5001, "Health Scaling");
        perkMap.put(8430, "Iron Skin");
        perkMap.put(8014, "Coup de Grace");
        perkMap.put(5007, "Ability Haste");
        perkMap.put(8021, "Fleet Footwork");
        perkMap.put(8226, "Manaflow Band");
        perkMap.put(8451, "Overgrowth");
        perkMap.put(8313, "Triple Tonic");
        perkMap.put(9103, "Legend: Bloodline");
        perkMap.put(8114, "The Immortal Butcher");
        perkMap.put(8230, "Phase Rush");
        perkMap.put(8318, "The Ruthless Visionary");
        perkMap.put(8316, "Minion Dematerializer");
        perkMap.put(8463, "Font of Life");
        perkMap.put(7000, "Template");
        perkMap.put(8304, "Magical Footwear");
        perkMap.put(8236, "Gathering Storm");
        perkMap.put(8009, "Presence of Mind");
        perkMap.put(8006, "The Eternal Champion");
        perkMap.put(9104, "Legend: Alacrity");
        perkMap.put(8416, "The Enlightened Titan");
        perkMap.put(5005, "Attack Speed");
        perkMap.put(8306, "Hextech Flashtraption");
        perkMap.put(8465, "Guardian");
        perkMap.put(8138, "Eyeball Collection");
        perkMap.put(5010, "Move Speed");
        perkMap.put(8127, "The Twisted Surgeon");
        perkMap.put(8143, "Sudden Impact");
        perkMap.put(8345, "Biscuit Delivery");
        perkMap.put(8444, "Second Wind");
        perkMap.put(8205, "The Incontestable Spellslinger");
        perkMap.put(8437, "Grasp of the Undying");
        perkMap.put(9923, "Hail of Blades");
        perkMap.put(8429, "Conditioning");
        perkMap.put(8124, "Predator");
        perkMap.put(8233, "Absolute Focus");
        perkMap.put(8007, "The Savant");
        perkMap.put(8136, "Zombie Ward");
        perkMap.put(8208, "The Ancient One");
        perkMap.put(8347, "Cosmic Insight");
        perkMap.put(8472, "Chrysalis");
        perkMap.put(8229, "Arcane Comet");
        perkMap.put(8344, "The Elegant Duelist ");
        perkMap.put(9101, "Overheal");
    }

    private void initQueueMap() {
        queueMap = new HashMap<>();

        queueMap.put(400, "일반");
        queueMap.put(420, "솔로랭크");
        queueMap.put(430, "일반");
        queueMap.put(440, "자유랭크");
        queueMap.put(450, "칼바람");
        queueMap.put(490, "빠른대전");
        queueMap.put(700, "격전");
        queueMap.put(800, "AI대전");
        queueMap.put(810, "AI대전");
        queueMap.put(820, "AI대전");
        queueMap.put(830, "AI대전");
        queueMap.put(840, "AI대전");
        queueMap.put(850, "AI대전");
        queueMap.put(900, "URF");
        queueMap.put(920, "포로왕");
        queueMap.put(1020, "단일모드");
        queueMap.put(1300, "돌격 넥서스");
        queueMap.put(1400, "궁극기 주문서");
        queueMap.put(1700, "아레나");
        queueMap.put(2000, "튜토리얼");
        queueMap.put(2010, "튜토리얼");
        queueMap.put(2020, "튜토리얼");
    }

    private void initSpellMap() {
        spellMap = new HashMap<>();

        spellMap.put(1, "SummonerBoost");
        spellMap.put(3, "SummonerExhaust");
        spellMap.put(4, "SummonerFlash");
        spellMap.put(6, "SummonerHaste");
        spellMap.put(7, "SummonerHeal");
        spellMap.put(11, "SummonerSmite");
        spellMap.put(12, "SummonerTeleport");
        spellMap.put(13, "SummonerMana");
        spellMap.put(14, "SummonerDot");
        spellMap.put(21, "SummonerBarrier");
        spellMap.put(30, "SummonerPoroRecall");
        spellMap.put(31, "SummonerPoroThrow");
        spellMap.put(32, "SummonerSnowball");
        spellMap.put(39, "SummonerSnowURFSnowball_Mark");
        spellMap.put(54, "Summoner_UltBookPlaceholder");
        spellMap.put(55, "Summoner_UltBookSmitePlaceholder");
        spellMap.put(2201, "SummonerCherryHold");
        spellMap.put(2202, "SummonerCherryFlash");
    }

    public String getPerk(Integer id) {
        return perkMap.get(id);
    }

    public String getSpell(Integer id) {
        return spellMap.get(id);
    }

    public String getQueue(Integer id) {
        return queueMap.get(id);
    }

}
