package com.kingmo.utils.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.kingmo.utils.main.UtilityUseException;

public class EconomyManager {

	private static Map<UUID, EconomyPlayer> ecoMap = new HashMap<>();

	/**
	 * 
	 * @param uuid : UUID of the player you wish to add to the system.
	 * @return a new EconomyPlayer object for the player of this UUID
	 * @throws NullPointerException thrown if UUID is invalid.
	 * @throws UtilityUseException  thrown if player is already registered. Use
	 *                              {@link #getPlayer(UUID)} in order to find this
	 *                              player.
	 */
	public EconomyPlayer createPlayer(UUID uuid) throws NullPointerException, UtilityUseException {
		OfflinePlayer newPlayer = Bukkit.getOfflinePlayer(uuid);
		if (newPlayer == null)
			throw new NullPointerException("This player either has never entered the server or does not exist.");
		else if (ecoMap.containsKey(uuid))
			throw new UtilityUseException("This player has already been registed. See Javadocs for work arounds.");
		@SuppressWarnings("deprecation")
		EconomyPlayer player = new EconomyPlayer(uuid); // this is the only acceptable use of it as it adds to the ecoMap.

		ecoMap.put(uuid, player);

		return player;
	}

	/**
	 * 
	 * @param uuid : UUID of the player you wish to get from the system.
	 * @return the player related to the param
	 * @throws UtilityUseException thrown if the player does not exist. Use
	 *                             {@link #createPlayer(UUID)} in order to create
	 *                             the player.
	 */
	public EconomyPlayer getPlayer(UUID uuid) throws UtilityUseException {

		if (!ecoMap.containsKey(uuid))
			throw new UtilityUseException("This player does not exist. See Javadocs for work arounds");

		return ecoMap.get(uuid);

	}

}
