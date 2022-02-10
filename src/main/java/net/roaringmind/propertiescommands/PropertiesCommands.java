package net.roaringmind.propertiescommands;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;

public class PropertiesCommands implements ModInitializer {
  public static final Logger LOGGER = LoggerFactory.getLogger("propertiescommands");

  @Override
  public void onInitialize() {
// @formatter:off
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
      dispatcher.register(literal("server-properties")
        .then(literal("enable-jmx-monitoring").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::enableJMXMonitoring)
          )
        )
        .then(literal("rcon.port").then(
          argument("port", IntegerArgumentType.integer(0, 65535))
            .executes(this::rconPort)
          )
        )
        .then(literal("gamemode")
          .then(literal("adventure").executes((ctx) -> {return gamemode(ctx, GameMode.ADVENTURE);}))
          .then(literal("creative").executes((ctx) -> {return gamemode(ctx, GameMode.CREATIVE);}))
          .then(literal("spectator").executes((ctx) -> {return gamemode(ctx, GameMode.SPECTATOR);}))
          .then(literal("survival").executes((ctx) -> {return gamemode(ctx, GameMode.SURVIVAL);}))
        )
        .then(literal("enable-command-block").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::enableCommandBlock)
          )
        )
        .then(literal("enable-query").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::enableQuery)
          )
        )
        .then(literal("level-name").then(
          argument("string", StringArgumentType.string())
            .executes(this::levelName)
          )
        )
        .then(literal("motd").then(
          argument("string", StringArgumentType.string())
            .executes(this::motd)
          )
        )
        .then(literal("query.port").then(
          argument("port", IntegerArgumentType.integer(0, 65535))
            .executes(this::queryPort)
          )
        )
        .then(literal("pvp").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::pvp)
          )
        )
        .then(literal("difficulty")
          .then(literal("easy").executes((ctx) -> {return difficulty(ctx, Difficulty.EASY);}))
          .then(literal("hard").executes((ctx) -> {return difficulty(ctx, Difficulty.HARD);}))
          .then(literal("normal").executes((ctx) -> {return difficulty(ctx, Difficulty.NORMAL);}))
          .then(literal("peaceful").executes((ctx) -> {return difficulty(ctx, Difficulty.PEACEFUL);}))
        )
        .then(literal("network-compression-threshold").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::networkCompressionTreshold)
          )
        )
        .then(literal("require-resource-pack").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::requireResourcePack)
          )
        )
        .then(literal("max-tick-time").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::maxTickTime)
          )
        )
        .then(literal("use-native-transport").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::useNativeTransport)
          )
        )
        .then(literal("max-players").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::maxTickTime)
          )
        )
        .then(literal("online-mode").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::onlineMode)
          )
        )
        .then(literal("allow-flight").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::allowFlight)
          )
        )
        .then(literal("broadcast-rcon-to-ops").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::broadcastRconToOps)
          )
        )
        .then(literal("view-distance").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::viewDistance)
          )
        )
        .then(literal("server-ip").then(
          argument("string", StringArgumentType.string())
            .executes(this::serverIp)
          )
        )
        .then(literal("resource-pack-prompt").then(
          argument("string", StringArgumentType.string())
            .executes(this::resourcePackPrompt)
          )
        )
        .then(literal("allow-nether").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::allowNether)
          )
        )
        .then(literal("server-port").then(
          argument("port", IntegerArgumentType.integer(0, 65535))
            .executes(this::serverPort)
          )
        )
        .then(literal("enable-rcon").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::enableRcon)
          )
        )
        .then(literal("sync-chunk-writes").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::syncChunkWrites)
          )
        )
        .then(literal("op-permission-level").then(
          argument("int", IntegerArgumentType.integer(0, 4))
            .executes(this::opPermissionLevel)
          )
        )
        .then(literal("prevent-proxy-connections").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::preventProxyConnections)
          )
        )
        .then(literal("resource-pack").then(
          argument("string", StringArgumentType.string())
            .executes(this::resourcePack)
          )
        )
        .then(literal("entity-broadcast-range-percentage").then(
          argument("int", IntegerArgumentType.integer(10, 1000))
            .executes(this::entityBroadcastRangePercentage)
          )
        )
        .then(literal("simulation-distance").then(
          argument("int", IntegerArgumentType.integer(3, 32))
            .executes(this::simulationDistance)
          )
        )
        .then(literal("rcon.password").then(
          argument("string", StringArgumentType.string())
            .executes(this::rconPassword)
          )
        )
        .then(literal("player-idle-timeout").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::playerIdleTimeout)
          )
        )
        .then(literal("force-gamemode").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::forceGamemode)
          )
        )
        .then(literal("rate-limit").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::rateLimit)
          )
        )
        .then(literal("hardcore").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::hardcore)
          )
        )
        .then(literal("white-list").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::whiteList)
          )
        )
        .then(literal("broadcast-console-to-ops").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::broadcastConsoleToOps)
          )
        )
        .then(literal("spawn-npcs").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::spawnNpcs)
          )
        )
        .then(literal("spawn-animals").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::spawnAnimals)
          )
        )
        .then(literal("snooper-enabled").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::snooperEnabled)
          )
        )
        .then(literal("function-permission-level").then(
          argument("int", IntegerArgumentType.integer(0, 4))
            .executes(this::functionPermissionLevel)
          )
        )
        .then(literal("text-filtering-config").then(
          argument("string", StringArgumentType.string())
            .executes(this::textFilteringConfig)
          )
        )
        .then(literal("spawn-monsters").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::spawnMonsters)
          )
        )
        .then(literal("enforce-whitelist").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::enforceWhitelist)
          )
        )
        .then(literal("resource-pack-sha1").then(
          argument("string", StringArgumentType.string())
            .executes(this::resourcePackSHA1)
          )
        )
        .then(literal("spawn-protection").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::spawnProtection)
          )
        )
        .then(literal("max-world-size").then(
          argument("int", IntegerArgumentType.integer())
            .executes(this::maxWorldSize)
          )
        )
      );
    });
// @formatter:on
  }

  Integer enableJMXMonitoring(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer rconPort(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer gamemode(CommandContext<ServerCommandSource> ctx, GameMode mode) {
    return 0;
  }

  Integer enableCommandBlock(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer enableQuery(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer levelName(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer motd(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer queryPort(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer pvp(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer difficulty(CommandContext<ServerCommandSource> ctx, Difficulty difficulty) {
    return 0;
  }

  Integer networkCompressionTreshold(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer requireResourcePack(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer maxTickTime(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer useNativeTransport(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer onlineMode(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer enableStatus(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer allowFlight(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer broadcastRconToOps(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer viewDistance(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer serverIp(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer resourcePackPrompt(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer allowNether(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer serverPort(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer enableRcon(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer syncChunkWrites(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer opPermissionLevel(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer preventProxyConnections(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer resourcePack(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer entityBroadcastRangePercentage(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer simulationDistance(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer rconPassword(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer playerIdleTimeout(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer forceGamemode(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer rateLimit(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer hardcore(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer whiteList(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer broadcastConsoleToOps(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer spawnNpcs(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer spawnAnimals(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer snooperEnabled(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer functionPermissionLevel(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer textFilteringConfig(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer spawnMonsters(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer enforceWhitelist(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer resourcePackSHA1(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer spawnProtection(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }

  Integer maxWorldSize(CommandContext<ServerCommandSource> ctx) {
    return 0;
  }
}
