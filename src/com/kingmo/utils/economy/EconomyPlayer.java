package com.kingmo.utils.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import com.kingmo.utils.logs.Logger;

public class EconomyPlayer implements ConfigurationSerializable {

	private long balance;
	private UUID uuid;

	/**
	 * @deprecated only to be used inside of the API. Will not update system if
	 *             used. See {@link EconomyManager#createPlayer(UUID)}
	 */
	public EconomyPlayer(UUID uuid) {
		this.uuid = uuid;
		balance = 0;
	}

	/**
	 * @deprecated only to be used inside of the API. Will not update system if
	 *             used. See {@link EconomyManager#createPlayer(UUID)}
	 */
	public EconomyPlayer(Player player) {
		this.uuid = player.getUniqueId();
		balance = 0;
	}

	/**
	 * @deprecated only to be used inside of the API. Will not update system if
	 *             used. See {@link EconomyManager#createPlayer(UUID)}
	 */
	public EconomyPlayer(UUID uuid, int startingBalance) {
		this.uuid = uuid;
		this.balance = startingBalance;
	}

	/**
	 * Used for serialization. Dont use.
	 * 
	 * @param map
	 */
	public EconomyPlayer(Map<String, Object> map) {

		this.uuid = (UUID) map.get("uuid");
		this.balance = (long) map.get("bal");
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> obj = new HashMap<>();

		obj.put("uuid", this.uuid);
		obj.put("bal", this.balance);

		return obj;
	}
	/**
	 * <p>
	 * Used to change the amount of money specified player has
	 * </p>
	 * <p>
	 * can be negative.
	 * </p>
	 * 
	 * @param amount the amount to be taken or added.
	 */
	public void changeSum(int amount) {

		String text = amount > 0 ? "added" : "took";
		Logger.log(text + " " + amount + " to " + Bukkit.getOfflinePlayer(uuid).getName() + "'s bank account");
		balance += amount;
	}

	
	/**
	 * Pays specified player a non-negative amount.
	 * <p>
	 * Also will take money from original player
	 * </p>
	 * @param player : player being sent the money.
	 * @param amount : the amount wished to send to player
	 * @see #recieve(int)
	 */
	public void pay(EconomyPlayer player, int amount) {
		player.recieve((amount = Math.abs(amount)));
		this.changeSum(-1 * amount);
	}
	/**
	 * Adds money to a players account.
	 * @param amount : the amount to be added to the player. This param should not be negative
	 */
	public void recieve(int amount) {
		this.changeSum(Math.abs(amount));
	}

}
