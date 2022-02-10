package net.roaringmind.propertiescommands;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

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
      if (!dedicated) {
        LOGGER.warn("did not register commands because this is not a dedicated server");
        return;
      }
      // dispatcher.register(literal("opme")
      //   .executes((ctx) -> {
      //     ctx.getSource().getPlayer().getServer().getPlayerManager().addToOperators(ctx.getSource().getPlayer().getGameProfile());
      //     return 0;
      //   })
      // );

      dispatcher.register(literal("server-properties")
      .requires(source -> source.hasPermissionLevel(4))
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
            .executes(this::maxPlayers)
          )
        )
        .then(literal("online-mode").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::onlineMode)
          )
        )
        .then(literal("enable-status").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::enableStatus)
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
        .then(literal("hide-online-players").then(
          argument("bool", BoolArgumentType.bool())
            .executes(this::hideOnlinePlayers)
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

  Boolean setProperty(String name, String value) {
    Path path = Paths.get("server.properties", new String[0]);
    Properties properties = new Properties();
    FileReader reader;

    try {
      reader = new FileReader(path.toFile());
    } catch (FileNotFoundException e) {
      LOGGER.error("failed to create server.properties reader");
      return true;
    }

    try {
      properties.load(reader);
    } catch (IOException e) {
      LOGGER.error("failed to load properties");
      return true;
    }

    try {
      reader.close();
    } catch (IOException e1) {
      LOGGER.error("failed to close reader");
      return true;
    }

    if (!properties.containsKey(name)) {
      return true;
    }

    properties.setProperty(name, value);

    FileWriter writer;

    try {
      writer = new FileWriter(path.toFile());
    } catch (IOException e) {
      LOGGER.error("failed to create server.properties writer");
      return true;
    }

    try {
      properties.store(writer, "");
    } catch (IOException e) {
      LOGGER.error("failed to write server.properties");
      return true;
    }

    try {
      writer.close();
    } catch (IOException e) {
      LOGGER.error("failed to close writer");
      return true;
    }
    return false;
  }

  Integer enableJMXMonitoring(CommandContext<ServerCommandSource> ctx) {
    return setProperty("enable-jmx-monitoring", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer rconPort(CommandContext<ServerCommandSource> ctx) {
    return setProperty("rcon.port", String.valueOf(IntegerArgumentType.getInteger(ctx, "port"))) ? 1 : 0;
  }

  Integer gamemode(CommandContext<ServerCommandSource> ctx, GameMode mode) {
    return setProperty("gamemode", mode.getName()) ? 1 : 0;
  }

  Integer enableCommandBlock(CommandContext<ServerCommandSource> ctx) {
    return setProperty("enable-command-block", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer enableQuery(CommandContext<ServerCommandSource> ctx) {
    return setProperty("enable-query", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer levelName(CommandContext<ServerCommandSource> ctx) {
    return setProperty("level-name", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer motd(CommandContext<ServerCommandSource> ctx) {
    return setProperty("motd", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer queryPort(CommandContext<ServerCommandSource> ctx) {
    return setProperty("query.port", String.valueOf(IntegerArgumentType.getInteger(ctx, "port"))) ? 1 : 0;
  }

  Integer pvp(CommandContext<ServerCommandSource> ctx) {
    return setProperty("pvp", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer difficulty(CommandContext<ServerCommandSource> ctx, Difficulty difficulty) {
    return setProperty("difficulty", difficulty.getName()) ? 1 : 0;
  }

  Integer networkCompressionTreshold(CommandContext<ServerCommandSource> ctx) {
    return setProperty("network-compression-threshold", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1
        : 0;
  }

  Integer requireResourcePack(CommandContext<ServerCommandSource> ctx) {
    return setProperty("require-resource-pack", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer maxTickTime(CommandContext<ServerCommandSource> ctx) {
    return setProperty("max-tick-time", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer useNativeTransport(CommandContext<ServerCommandSource> ctx) {
    return setProperty("use-native-transport", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer maxPlayers(CommandContext<ServerCommandSource> ctx) {
    return setProperty("max-players", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer onlineMode(CommandContext<ServerCommandSource> ctx) {
    return setProperty("online-mode", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer enableStatus(CommandContext<ServerCommandSource> ctx) {
    return setProperty("enable-status", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer allowFlight(CommandContext<ServerCommandSource> ctx) {
    return setProperty("allow-flight", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer broadcastRconToOps(CommandContext<ServerCommandSource> ctx) {
    return setProperty("broadcast-rcon-to-ops", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer viewDistance(CommandContext<ServerCommandSource> ctx) {
    return setProperty("view-distance", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer serverIp(CommandContext<ServerCommandSource> ctx) {
    return setProperty("server-ip", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer resourcePackPrompt(CommandContext<ServerCommandSource> ctx) {
    return setProperty("resource-pack-prompt", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer allowNether(CommandContext<ServerCommandSource> ctx) {
    return setProperty("allow-nether", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer serverPort(CommandContext<ServerCommandSource> ctx) {
    return setProperty("server-port", String.valueOf(IntegerArgumentType.getInteger(ctx, "port"))) ? 1 : 0;
  }

  Integer enableRcon(CommandContext<ServerCommandSource> ctx) {
    return setProperty("enable-rcon", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer syncChunkWrites(CommandContext<ServerCommandSource> ctx) {
    return setProperty("sync-chunk-writes", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer opPermissionLevel(CommandContext<ServerCommandSource> ctx) {
    return setProperty("op-permission-level", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer preventProxyConnections(CommandContext<ServerCommandSource> ctx) {
    return setProperty("prevent-proxy-connections", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer hideOnlinePlayers(CommandContext<ServerCommandSource> ctx) {
    return setProperty("hide-online-players", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer resourcePack(CommandContext<ServerCommandSource> ctx) {
    return setProperty("resource-pack", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer entityBroadcastRangePercentage(CommandContext<ServerCommandSource> ctx) {
    return setProperty("entity-broadcast-range-percentage", String.valueOf(IntegerArgumentType.getInteger(ctx, "int")))
        ? 1
        : 0;
  }

  Integer simulationDistance(CommandContext<ServerCommandSource> ctx) {
    return setProperty("simulation-distance", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer rconPassword(CommandContext<ServerCommandSource> ctx) {
    return setProperty("rcon.password", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer playerIdleTimeout(CommandContext<ServerCommandSource> ctx) {
    return setProperty("player-idle-timeout", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer forceGamemode(CommandContext<ServerCommandSource> ctx) {
    return setProperty("force-gamemode", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer rateLimit(CommandContext<ServerCommandSource> ctx) {
    return setProperty("rate-limit", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer hardcore(CommandContext<ServerCommandSource> ctx) {
    return setProperty("hardcore", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer whiteList(CommandContext<ServerCommandSource> ctx) {
    return setProperty("white-list", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer broadcastConsoleToOps(CommandContext<ServerCommandSource> ctx) {
    return setProperty("broadcast-console-to-ops", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer spawnNpcs(CommandContext<ServerCommandSource> ctx) {
    return setProperty("spawn-npcs", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer spawnAnimals(CommandContext<ServerCommandSource> ctx) {
    return setProperty("spawn-animals", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer snooperEnabled(CommandContext<ServerCommandSource> ctx) {
    return setProperty("snooper-enabled", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer functionPermissionLevel(CommandContext<ServerCommandSource> ctx) {
    return setProperty("function-permission-level", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer textFilteringConfig(CommandContext<ServerCommandSource> ctx) {
    return setProperty("text-filtering-config", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer spawnMonsters(CommandContext<ServerCommandSource> ctx) {
    return setProperty("spawn-monsters", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer enforceWhitelist(CommandContext<ServerCommandSource> ctx) {
    return setProperty("enforce-whitelist", String.valueOf(BoolArgumentType.getBool(ctx, "bool"))) ? 1 : 0;
  }

  Integer resourcePackSHA1(CommandContext<ServerCommandSource> ctx) {
    return setProperty("resource-pack-sha1", StringArgumentType.getString(ctx, "string")) ? 1 : 0;
  }

  Integer spawnProtection(CommandContext<ServerCommandSource> ctx) {
    return setProperty("spawn-protection", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }

  Integer maxWorldSize(CommandContext<ServerCommandSource> ctx) {
    return setProperty("max-world-size", String.valueOf(IntegerArgumentType.getInteger(ctx, "int"))) ? 1 : 0;
  }
}
