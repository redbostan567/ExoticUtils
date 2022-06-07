package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.main.ExoticUtilityMain;
import com.kingmo.utils.main.Utils;

/**
 *
 * Handles all custom block placement and breaking. <br>
 * </br>
 * To use register {@link BlockType} using
 * {@link BlockListener#registerBlock(BlockType)}
 *
 * @author Kingmo100
 * @since V1.0
 */
public class BlockListener implements Listener {

	private static Map<String, BlockType> registeredBlocks = new HashMap<>();
	private static Map<Location, Block> activeBlocks = new HashMap<>();

	public BlockListener(List<BlockType> type) {

		for (BlockType t : type) {
			registeredBlocks.put(t.getItemName(), t);
		}

	}

	public BlockListener(Map<Location, Block> active) {
		BlockListener.activeBlocks = active;
	}

	/**
	 * Used to calculate when the custom blocks are placed
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		ItemStack stack = e.getItemInHand();

		Player player = e.getPlayer();

		if (!Utils.nonNull(stack))
			return;

		//System.out.println(stack.getItemMeta().getDisplayName() + "       " + registeredBlocks.toString());
		
		// Decides whether or not the block in the players hand is a custom block
		BlockType blockType = registeredBlocks.getOrDefault(stack.getItemMeta().getDisplayName(), null);
		if (blockType == null)
			return;
		

		Location loc = e.getBlock().getLocation();

		Block block = null;
		try {
			block = blockType.getBlockFromType(loc);
			// creates the custom block instance
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		block.run(loc, player);
		// runs the custom block and then decides if its a looped custom block
		LoopedRunnable loop = new LoopedRunnable(block, player, loc);
		if (blockType.getDelay() > 0)
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"), loop,
					block.getType().getDelay());

		// calls on place not much difference between run and place unless your using a
		// looped without redefining looped.
		block.onPlace(e.getPlayer());

		// adds loop for serialization
		ExoticUtilityMain.addLoop(loc, loop);

		// adds block to map for serialization
		activeBlocks.put(loc, block);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Location loc = e.getBlock().getLocation();
		// decides whether or not the block broken is a custom block
		if (!activeBlocks.containsKey(loc))
			return;

		// gets block instance
		Block block = activeBlocks.get(loc);

		// destroys block so looped runnable cancels
		block.setBroken(true);
		// Calls onbreak method
		block.onBreak(e.getPlayer());

		// breaks the item in the actual game and drops custom item.
		e.getBlock().setType(Material.AIR);
		if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			loc.getWorld().dropItemNaturally(loc, Block.createItemStack(block.getType()));

		// Kills armor stand if the name is shown
		if (block.getType().showName())
			block.getArmorStand().remove();
		block.setArmorStand(null); // only legal place to use this method.

		// removes the block from all registrys
		ExoticUtilityMain.removeLoop(loc);
		activeBlocks.remove(loc);
	}

	/**
	 * Clears all active armor stands for reboot.
	 */
	public static void clearStands() {
		for (Block b : activeBlocks.values()) {
			if (b.getType().showName()) {
				b.getArmorStand().remove();
			}
		}
	}

	/**
	 * <blockquote> Use this to register any blocks </blockquote>
	 * 
	 * @param type the block type being registered
	 * @return whether or not the block was registered properly
	 */
	public static boolean registerBlock(BlockType type) {
		String key = type.getItemName();
		if (registeredBlocks.containsKey(key))
			return false;
		registeredBlocks.put(key, type);
		//puts both names in the registry to make sure it is easily accessible. 
		registeredBlocks.put(type.getID().toUpperCase().replace(" ", "_"), type);
		
		System.out.println(registeredBlocks);
		
		//makes sure the block is serializable for future use.
		ConfigurationSerialization.registerClass(type.getBlockClass());
		return true;

	}

	/**
	 * Registers all block in a list.
	 * 
	 * @param types a list of all blocks being registered
	 */
	public static void registerBlocks(List<BlockType> types) {

		for (BlockType type : types)
			BlockListener.registerBlock(type);
	}
	/**
	 * Registers an array of blocks.
	 * 
	 * @param types : an array of blocks to be registered.
	 *
	 */
	public static void registerBlocks(BlockType[] types) {

		for (BlockType type : types)
			BlockListener.registerBlock(type);
	}
	/**
	 * @return all registered block types.
	 */
	public static Map<String, BlockType> getRegisteredBlocks() {
		return BlockListener.registeredBlocks;
	}

	/**
	 * @return all active blocks.
	 */
	public static Map<Location, Block> getActiveBlocks() {
		return BlockListener.activeBlocks;
	}

	/**
	 * @apiNote Do not use as it is automatically done in the API.
	 * @param block the Block in which the Name tag would be put on.
	 */
	@SuppressWarnings("deprecation")
	public static void addArmorStand(Block block) {
		//sets up armor stands
		Location loc = block.getBlock().getLocation().add(0.5, -1, 0.5);
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setCustomName(block.getName());
		as.setCustomNameVisible(true);
		as.setGravity(false);

		block.setArmorStand(as); // only legal place to use this method
	}

	/**
	 * Load Armor stands.
	 */
	public static void loadStands() {
		for (Block b : activeBlocks.values()) {
			if (b.getType().showName()) {
				BlockListener.addArmorStand(b);
			}
		}

	}

	public static BlockType getBlock(String string) {
		return registeredBlocks.get(string);
	}

	/**
	 * Calls {@link Block#onBlockClick(org.bukkit.event.block.Action, Player)} and
	 * determines whether or not the event should be cancelled.
	 * 
	 * @see Block#onBlockClick(org.bukkit.event.block.Action, Player)
	 */
	@EventHandler
	public void onBlockInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;

		Location loc = e.getClickedBlock().getLocation();
		//checks to see if this block is a custom block.
		if (!activeBlocks.containsKey(loc))
			return;

		Block block = activeBlocks.get(loc);

		e.setCancelled(block.onBlockClick(e.getAction(), e.getPlayer()));

	}

}
