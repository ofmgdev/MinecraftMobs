# 🔥 MinecraftMobs - Custom Boss Plugin 🔥

![Minecraft](https://img.shields.io/badge/Minecraft-1.20+-brightgreen?style=for-the-badge&logo=minecraft)
![Spigot](https://img.shields.io/badge/Spigot-Paper-orange?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17+-red?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

> 🎮 **Advanced Modular Boss System** with custom abilities, particle effects, and AI!

---

## 🌟 Features

### 🏰 **Epic Boss Encounters**
| Boss | Type | Special Abilities | Difficulty |
|------|------|------------------|------------|
| 🧟‍♂️ **Zombie Knight** | Mounted Warrior | Diamond Armor + Horse Combat | ⭐⭐⭐ |
| 🐲 **Igneel Fire Dragon** | Ender Dragon | Fire Aura + Environmental Fire + Fireballs | ⭐⭐⭐⭐⭐ |
| 💀 **Skeleton Archer Lord** | Teleporting Boss | Rapid Arrows + Guard Army + Elite Mounted Units | ⭐⭐⭐⭐ |

### ⚙️ **Modular Architecture**
- 🎯 **Ability System** - TeleportAbility, RapidArrowAbility, HelperSpawning
- ✨ **Particle Effects** - Fire, Lightning, Ice auras with environmental impact  
- 👑 **Boss Bars** - Real-time health tracking for all bosses
- 🏇 **Mounted Combat** - Advanced horse + rider mechanics with AI
- 🛡️ **Custom Armor Sets** - Leather to Netherite gear system
- 🤖 **Guard Leash** - Intelligent minion management

---

## 🎯 Boss Details

### 🧟‍♂️ **Zombie Knight**
```
❤️  Health: 80 HP
🏇 Mount: Diamond Armored Horse (30 HP)
⚔️  Weapon: Diamond Sword
🛡️  Armor: Full Diamond Set
🏃 Speed: 0.20 (Rider) + 0.5 (Horse)
🎯 AI: Advanced mounted combat tracking
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

### 💀 **Skeleton Archer Lord**
```
❤️  Health: 100 HP (1.35x Scale)
⚡ Teleport: Every 8 seconds (10 block radius)
🏹 Rapid Fire: Precise arrows every 0.75s
👥 Guards: 3x Chain Armor Warriors with Iron Swords
🏇 Elite Unit: 1x Mounted Skeleton (Iron Armor + Diamond Sword)
🛡️ Guard Leash: Auto-teleport guards within 15 blocks
```

---

## 🎮 Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/spawnzombieknight` | 🧟‍♂️ Spawn Zombie Knight | `minecraftmobs.spawn.zombie` |
| `/spawnigneel` | 🐲 Spawn Igneel Fire Dragon | `minecraftmobs.spawn.dragon` |
| `/spawnskeletonlord` | 💀 Spawn Skeleton Archer Lord | `minecraftmobs.spawn.skeleton` |

---

## 📸 Screenshots

> 🖼️ *Screenshots coming soon!*

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

## 🔧 Technical Architecture

### 📁 **Modular Package Structure**
```
me.ofmgdev.MinecraftMobsAddon/
├── ability/           - TeleportAbility, RapidArrowAbility, HelperSpawning
├── auxiliaryfunctions/ - MountedMob, GuardLeash
├── command/           - Boss spawner commands
├── effects/           - CustomParticles, CustomBossBar
├── mobs/              - Individual boss implementations
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
