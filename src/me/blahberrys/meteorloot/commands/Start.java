package me.blahberrys.meteorloot.commands;

import me.blahberrys.meteorloot.MeteorLoot;
import me.blahberrys.meteorloot.Settings;
import me.blahberrys.meteorloot.meteor.Meteor;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start {

	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) throws CommandException {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;

			if (!(player.hasPermission("meteorloot.start"))) {
				MeteorLoot.getInstance().sendMessage(player, Settings.getInstance().noPermMsg);
				return true;
			}
		}

		World world = null;
		if (args.length > 2)
			world = MeteorLoot.getInstance().getServer().getWorld(args[1]);
		else
			world = (player != null ? player.getWorld() : null);

		if (world == null || !Settings.getInstance().worlds.contains((world))) {
			if (player != null)
				MeteorLoot.getInstance().sendMessage(player, Settings.getInstance().notEnabledMsg.replaceAll("@WORLD", world.getName()));
			else
				sender.sendMessage(Settings.getInstance().notEnabledMsg.replaceAll("@WORLD", world.getName()));
			return true;
		}

		if (Meteor.getInstance().inProgress.containsKey(world) && Meteor.getInstance().inProgress.get(world)) {
			if (player != null)
				MeteorLoot.getInstance().sendMessage(player, Settings.getInstance().inProgMsg);
			else
				sender.sendMessage(Settings.getInstance().inProgMsg);
			return true;
		}

		Meteor.getInstance().startMeteorCrash(world);
		return true;
	}

}
