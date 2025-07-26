# 🔥 MinecraftMobs - Custom Boss Plugin 🔥

![Minecraft](https://img.shields.io/badge/Minecraft-1.20+-brightgreen?style=for-the-badge&logo=minecraft)
![Spigot](https://img.shields.io/badge/Spigot-Paper-orange?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17+-red?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-2.0-purple?style=for-the-badge)

> 🎮 **Advanced Modular Boss System** with custom abilities, particle effects, and AI!

---

## 🌟 Features

### 🏰 **Epic Boss Encounters**
| Boss | Type | Special Abilities | Difficulty |
|------|------|------------------|------------|
| 🛡️ **Iron Golem Guardian** | Tank Boss | Self-Repair + Ground Slam + Villager Guards | ⭐⭐ |
| 🧟‍♂️ **Zombie Knight** | Mounted Warrior | Diamond Armor + Horse Combat | ⭐⭐⭐ |
| 🕷️ **Spider Queen** | Giant Spider | Poison Attacks + Cave Spider Army | ⭐⭐⭐⭐ |
| 🐺 **Alpha Werewolf** | Pack Leader | Howl Summon + Pack Hunt + Fear Aura | ⭐⭐⭐⭐ |
| 💀 **Skeleton Archer Lord** | Teleporting Boss | Rapid Arrows + Guard Army + Elite Mounted Units | ⭐⭐⭐⭐ |
| 🐲 **Igneel Fire Dragon** | Ender Dragon | Fire Aura + Environmental Fire + Fireballs | ⭐⭐⭐⭐⭐ |

### ⚙️ **Modular Architecture**
- 🎯 **Ability System** - TeleportAbility, RapidArrowAbility, HelperSpawning, PoisonAttackAbility, GroundSlamAbility, HowlSummonAbility, PackHuntAbility, SelfRepairAbility
- ✨ **Particle Effects** - Fire, Lightning, Ice, Fear auras with environmental impact  
- 👑 **Boss Bars** - Real-time health tracking for all bosses
- 🏇 **Mounted Combat** - Advanced horse + rider mechanics with AI
- 🛡️ **Custom Armor Sets** - Leather to Netherite gear system
- 🤖 **Guard Leash** - Intelligent minion management

---

<details>
<summary>🎯 Click to view detailed Boss Stats & Abilities</summary>

## 🎯 Boss Details

### 🛡️ **Iron Golem Guardian**
```
❤️  Health: 200 HP (3x Scale)
🏃 Speed: 0.15 (Very Slow Tank)
💥 Ground Slam: 8-block radius every 6s (12 damage)
🔧 Self Repair: Heals 25 HP when below 50% health
👥 Guards: Aggressive toward players and villagers
✨ Aura: Lightning particle effects
📊 Boss Bar: White
```

### 🧟‍♂️ **Zombie Knight**
```
❤️  Health: 80 HP
🏇 Mount: Diamond Armored Horse (30 HP)
⚔️  Weapon: Diamond Sword
🛡️  Armor: Full Diamond Set
🏃 Speed: 0.20 (Rider) + 0.5 (Horse)
🎯 AI: Advanced mounted combat tracking
```

### 🕷️ **Spider Queen** 
```
❤️  Health: 135 HP (2.5x Scale)
☠️  Poison Attack: 8-block range every 2s
🕸️  Effects: Poison (5s) + Slowness (3s)
👶 Minions: 10x Cave Spiders (12 HP each)
🏃 Speed: 0.35 + 0.55 (minions)
🎯 Range: 25-block leash distance
✨ Aura: Green particle effects
```

### 🐺 **Alpha Werewolf**
```
❤️  Health: 120 HP (4x Scale)
🌙 Howl Summon: 2 pack wolves every 8s (max 6 total)
🩸 Pack Hunt: Damage and speed increase with pack size
😱 Fear Aura: Causes slowness, blindness, and nausea
⚔️  Attack: 10 base damage (scales with pack)
🏃 Speed: 0.4 base (increases with pack size)
✨ Effects: Red flame and fear particles
```

### 💀 **Skeleton Archer Lord**
```
❤️  Health: 100 HP (1.35x Scale)
⚡ Teleport: Every 8 seconds (10 block radius)
🏹 Rapid Fire: Precise arrows every 0.75s
👥 Guards: 3x Chain Armor Warriors with Iron Swords
🏇 Elite Unit: 1x Mounted Skeleton (Iron Armor + Diamond Sword)
🛡️ Guard Leash: Auto-teleport guards within 15 blocks
💙 Effects: Blue fire trail for mounted units
```

### 🐲 **Igneel Fire Dragon** 
```
❤️  Health: 250 HP
🔥 Fire Aura: Burns ground + particle effects
💥 Attacks: Explosive Fireballs every 3s
✨ Effects: Lava, smoke, enchant particles
🎯 AI: Aggressive charge behavior
📊 Boss Bar: Real-time health tracking
```

</details>

---

## 🎮 Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/spawnironguardian` | 🛡️ Spawn Iron Golem Guardian | `minecraftmobs.spawn.ironguardian` |
| `/spawnzombieknight` | 🧟‍♂️ Spawn Zombie Knight | `minecraftmobs.spawn.zombie` |
| `/spawnspiderqueen` | 🕷️ Spawn Spider Queen | `minecraftmobs.spawn.spider` |
| `/spawnwerewolfwarrior` | 🐺 Spawn Alpha Werewolf | `minecraftmobs.spawn.werewolf` |
| `/spawnskeletonlord` | 💀 Spawn Skeleton Archer Lord | `minecraftmobs.spawn.skeleton` |
| `/spawnigneel` | 🐲 Spawn Igneel Fire Dragon | `minecraftmobs.spawn.dragon` |

---

## 🏰 Future Vision

### 🎯 **Long-term Goal: Village Defense Game**
This plugin is designed as **Phase 1** of a planned **Tower Defense + Village Building** project.

**The Vision:**
- 🌙 **Night Rounds** - Waves of monsters attack villages
- 🏰 **Village Building** - Construct defenses  
- 💰 **Economy System** - Resources and upgrades
- 📊 **Player Progression** - Levels and skills
- 👥 **Cooperative Play** - Team-based challenges

**Current Status:** Boss & Monster system complete ✅  
**Next Phase:** Game mechanics and village systems 🔄

---

<details>
<summary>📋 Click to view Version History & Changelog</summary>

## 📋 Version History

### 🚀 **Version 3.0** - *Guardian & Alpha Update*
**Release Date:** *Current Development*

#### ✨ **NEW FEATURES**
- 🛡️ **Iron Golem Guardian** - Tank boss with self-repair and ground slam
- 🐺 **Alpha Werewolf** - Pack leader with howl summon and fear aura
- 💥 **Ground Slam System** - Area damage ability with knockback
- 🌙 **Pack Hunt Mechanics** - Dynamic stat scaling based on pack size
- 😱 **Fear Aura Effects** - Debuff system with blindness and nausea

#### 🔧 **IMPROVEMENTS**
- 🔧 **Self-Repair Ability** - Automatic healing when health drops below threshold
- 🐺 **Pack AI System** - Coordinated wolf pack behavior
- ⚡ **Enhanced Particle Effects** - Fear aura with multiple debuff effects
- 🛠️ **Deploy Script** - Automated build and deployment system

#### 📁 **NEW FILES ADDED**
- `IronGolemGuardian.java` + `IronGolemGuardianSpawner.java`
- `WerewolfWarrior.java` + `WerewolfWarriorSpawner.java`
- `GroundSlamAbility.java`, `SelfRepairAbility.java`
- `HowlSummonAbility.java`, `PackHuntAbility.java`
- Enhanced `CustomParticles.java` with `startFearAura()`

---

### 🕷️ **Version 2.0** - *Spider Queen Update*
**Release Date:** *Previous*

#### ✨ **NEW FEATURES**
- 🕷️ **Spider Queen Boss** - Giant spider with poison abilities
- ☠️ **Poison Attack System** - New ability type for close-range toxin attacks
- 👶 **Cave Spider Army** - 10 minions with intelligent targeting

#### 🔧 **IMPROVEMENTS**
- 🎯 **Smart Target Sharing** - All helpers now attack the same target
- 📏 **Flexible Guard Distance** - Customizable leash distance per boss type
- 🛡️ **Armor System Fix** - Null pointer protection for entities without armor support

#### 📁 **NEW FILES ADDED**
- `SpiderQueen.java` + `SpiderQueenSpawner.java`
- `PoisonAttackAbility.java`

---

### 🎉 **Version 1.0** - *Foundation Release*
**Release Date:** *Initial*

#### ✨ **CORE FEATURES**
- 🧟‍♂️ **Zombie Knight** - Mounted warrior with diamond gear
- 🐲 **Igneel Fire Dragon** - Ender dragon with fire abilities
- 💀 **Skeleton Archer Lord** - Teleporting archer with minions

#### 🏗️ **ARCHITECTURE**
- 🎯 **Modular Ability System** - TeleportAbility, RapidArrowAbility, HelperSpawning
- ✨ **Particle Effects** - Fire, Lightning, Ice auras
- 👑 **Boss Bar System** - Real-time health tracking
- 🏇 **Mounted Mob System** - Horse + rider mechanics

</details>

---

## 🔧 Technical Architecture

### 📁 **Current Package Structure**
```
me.ofmgdev.MinecraftMobsAddon/
├── ability/           - TeleportAbility, RapidArrowAbility, HelperSpawning, PoisonAttackAbility
├── auxiliaryfunctions/ - MountedMob, GuardLeash
├── command/           - ZombieKnightSpawner, RedDragonIgneelSpawner, SkeletonArcherLordSpawner, SpiderQueenSpawner
├── effects/           - CustomParticles, CustomBossBar
├── mobs/              - ZombieKnight, RedDragonIgneel, SkeletonArcherLord, SpiderQueen
└── rootfunctions/     - CustomMob, CustomArmor, CustomHorse
```

### ⚙️ **Core Systems**
- 🎨 **Particle Effects** - Fire, lightning, ice auras with environmental interaction
- 📊 **Boss Bars** - Dynamic health tracking for all entity types
- 🤖 **Advanced AI** - Smart combat, pathfinding, and target management
- 🏇 **Mounted Combat** - Seamless horse and rider mechanics with tracking
- 👥 **Helper Spawning** - Configurable minion waves with different spawn modes
- ⚡ **Ability System** - Modular, reusable boss abilities
- 🛡️ **Equipment System** - Comprehensive armor and weapon management

---

## 🚀 Installation

### **Requirements**
- ✅ Minecraft Server 1.20+
- ✅ Spigot/Paper Server
- ✅ Java 17+

### **Download & Install**
1. 📥 Download the latest `MinecraftMobs.jar` from [Releases](../../releases)
2. 📁 Place the file in your server's `plugins/` folder
3. 🔄 Restart your server
4. ✨ Start spawning epic bosses!

### **Building from Source**
```bash
git clone https://github.com/ofmgdev/MinecraftMobs.git
cd MinecraftMobs
./gradlew build
```
JAR file will be in `build/libs/MinecraftMobs.jar`

---

## 📸 Screenshots

> 🖼️ *Screenshots coming soon!*

---

## 🤝 Contributing

Contributions are welcome! Feel free to:
- 🐛 Report bugs
- 💡 Suggest new boss ideas  
- 🔧 Submit pull requests
- ⭐ Star this repository

---

## 📞 Support

Need help? Found a bug?
- 📧 Create an [Issue](../../issues)
- 💬 Contact: [Your Discord/Email]

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

### 🌟 **Made with ❤️ for the Minecraft Community** 🌟

**Built with modular architecture for easy expansion and customization**

**⭐ Don't forget to star this repo if you like it! ⭐**

</div>
