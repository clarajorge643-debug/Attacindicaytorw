# Attack Indicator Ring (Fabric 1.20.1)

A client-side Fabric mod that replaces Minecraft's vanilla attack-indicator display with a circular cooldown ring directly below the crosshair. The ring uses the player's actual `getAttackCooldownProgress` value and does not alter combat mechanics.

## Requirements
- Minecraft Java Edition 1.20.1
- Fabric Loader 0.14.21 or newer
- Java 17

## Build
Run `gradle build` from this directory with Gradle installed. The resulting JAR will be in `build/libs/`.

## Install
Put the built `attack-indicator-ring-1.0.0.jar` in the Minecraft `mods` folder alongside Fabric Loader. No server installation is required.

## Notes
- The ring is shown when Minecraft's Attack Indicator option is not set to Off.
- The vanilla indicator is cancelled only when the ring is active, to avoid showing both at once.
- This project is source code and must be compiled before it can be installed as a `.jar`.
