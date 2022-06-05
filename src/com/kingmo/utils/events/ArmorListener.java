package com.kingmo.utils.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmorListener implements Listener {

	private static JavaPlugin plugin;

	public ArmorListener(JavaPlugin plug) {
		plugin = plug;
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamage() == 0)
			e.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getDamage() == 0)
			e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ClickType ct = e.getClick();
		ItemStack ci = e.getCurrentItem();
		ItemStack cu = e.getCursor();

		try {
			if (ct.equals(ClickType.SHIFT_RIGHT) || ct.equals(ClickType.SHIFT_LEFT)) {
				if (e.getSlotType() == SlotType.ARMOR && ci.getType() != Material.AIR) {
					Bukkit.getServer().getPluginManager().callEvent(
							new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, getArmorType(ci.getType()), ci));
				} else if (isArmor(ci.getType()) && e.getInventory().getType() == InventoryType.CRAFTING) {
					try {
						if (getArmorType(ci.getType()) == ArmorType.HELMET
								&& p.getInventory().getHelmet().getType() == Material.AIR) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.HELMET, cu, ci));
						} else if (getArmorType(ci.getType()) == ArmorType.CHESTPLATE
								&& p.getInventory().getChestplate().getType() == Material.AIR) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.CHESTPLATE, cu, ci));
						} else if (getArmorType(ci.getType()) == ArmorType.LEGGINGS
								&& p.getInventory().getLeggings().getType() == Material.AIR) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.LEGGINGS, cu, ci));
						} else if (getArmorType(ci.getType()) == ArmorType.BOOTS
								&& p.getInventory().getBoots().getType() == Material.AIR) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.BOOTS, cu, ci));
						}
					} catch (NullPointerException e2) {
						if (getArmorType(ci.getType()) == ArmorType.HELMET) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.HELMET, cu, ci));
						} else if (getArmorType(ci.getType()) == ArmorType.CHESTPLATE) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.CHESTPLATE, cu, ci));
						} else if (getArmorType(ci.getType()) == ArmorType.LEGGINGS) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.LEGGINGS, cu, ci));
						} else if (getArmorType(ci.getType()) == ArmorType.BOOTS) {
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorEquipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.BOOTS, cu, ci));
						}
					}
				} else if (WeaponType.checkWeapon(ci)) {

					boolean isSlot = true;
					for (int x = 0; x < p.getInventory().getHeldItemSlot(); x++) {
						if (p.getInventory().getItem(x) == null) {
							isSlot = false;
						} else if (p.getInventory().getItem(x).equals(ci)) {
							isSlot = false;
						}
					}
					if (isSlot && !(p.getInventory().getHeldItemSlot() == e.getSlot())) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ItemSwapEvent(p, ci, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(ci.getType())));
					}

					if (p.getInventory().getHeldItemSlot() == e.getSlot()) {

						Bukkit.getServer().getPluginManager().callEvent(
								new ItemUnuseEvent(p, ci, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(ci.getType())));
					}
				}

			} else if (ct.equals(ClickType.NUMBER_KEY)) {
				ItemStack nci = p.getInventory().getItem(e.getHotbarButton());
				boolean applying = false;

				if (nci != null && isArmor(nci.getType()) && e.getSlotType() == SlotType.ARMOR) {

					if (getArmorType(nci.getType()) == ArmorType.HELMET && e.getSlot() == 39) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new ArmorEquipEvent(p, EquipMethod.HOTBAR_SWAP, ArmorType.HELMET, cu, nci));
						applying = true;

					} else if (getArmorType(nci.getType()) == ArmorType.CHESTPLATE && e.getSlot() == 38) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ArmorEquipEvent(p, EquipMethod.HOTBAR_SWAP, ArmorType.CHESTPLATE, cu, nci));
						applying = true;
					} else if (getArmorType(nci.getType()) == ArmorType.LEGGINGS && e.getSlot() == 37) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ArmorEquipEvent(p, EquipMethod.HOTBAR_SWAP, ArmorType.LEGGINGS, cu, nci));
						applying = true;
					} else if (getArmorType(nci.getType()) == ArmorType.BOOTS && e.getSlot() == 36) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new ArmorEquipEvent(p, EquipMethod.HOTBAR_SWAP, ArmorType.BOOTS, cu, nci));
						applying = true;
					}
					/*
					 * if(ArmorListener.isArmor(ci.getType())) {
					 * Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p,
					 * EquipMethod.PICK_DROP, (ArmorType) ArmorType.getType(ci.getType()), ci)); }
					 */

				}
				if (isArmor(ci.getType()) && e.getSlotType().equals(SlotType.ARMOR)) {
					try {
						if (!this.isFull(e.getClickedInventory())) {
							System.out.println(applying);
							Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p,
									EquipMethod.HOTBAR_SWAP, getArmorType(ci.getType()), ci, applying));
						}
					} catch (NullPointerException e2) {
						Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p,
								EquipMethod.HOTBAR_SWAP, getArmorType(ci.getType()), ci, applying));
					}
				}
				if (WeaponType.checkWeapon(ci)) {
					if (e.getSlot() != p.getInventory().getHeldItemSlot()
							&& e.getHotbarButton() == p.getInventory().getHeldItemSlot()) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ItemSwapEvent(p, ci, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(ci.getType())));
					}
					if (e.getSlot() == p.getInventory().getHeldItemSlot() && e.getSlot() != e.getHotbarButton()) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ItemUnuseEvent(p, ci, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(ci.getType())));
					}

				}
				if (WeaponType.checkWeapon(nci)) {

					if (e.getSlot() == p.getInventory().getHeldItemSlot() && e.getSlot() != e.getHotbarButton()) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ItemSwapEvent(p, nci, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(nci.getType())));
					}

					if (e.getSlot() != p.getInventory().getHeldItemSlot()
							&& e.getHotbarButton() == p.getInventory().getHeldItemSlot()) {
						Bukkit.getServer().getPluginManager().callEvent(new ItemUnuseEvent(p, nci,
								WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(nci.getType())));
					} else {
					}
				}
			} else if (ct.equals(ClickType.RIGHT) || ct.equals(ClickType.LEFT)) {
				if (isArmor(cu.getType()) && e.getSlotType() == SlotType.ARMOR) {
					if (getArmorType(cu.getType()) == ArmorType.HELMET && e.getSlot() == 39) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new ArmorEquipEvent(p, EquipMethod.PICK_DROP, ArmorType.HELMET, ci, cu));
					} else if (getArmorType(cu.getType()) == ArmorType.CHESTPLATE && e.getSlot() == 38) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new ArmorEquipEvent(p, EquipMethod.PICK_DROP, ArmorType.CHESTPLATE, ci, cu));
					} else if (getArmorType(cu.getType()) == ArmorType.LEGGINGS && e.getSlot() == 37) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new ArmorEquipEvent(p, EquipMethod.PICK_DROP, ArmorType.LEGGINGS, ci, cu));
					} else if (getArmorType(cu.getType()) == ArmorType.BOOTS && e.getSlot() == 36) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new ArmorEquipEvent(p, EquipMethod.PICK_DROP, ArmorType.BOOTS, ci, cu));
					}

					if (ArmorListener.isArmor(ci.getType())) {
						Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p, EquipMethod.PICK_DROP,
								(ArmorType) ArmorType.getType(ci.getType()), ci));
					}

				} else if (WeaponType.checkWeapon(cu)) {
					if (e.getSlot() == p.getInventory().getHeldItemSlot()) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ItemSwapEvent(p, cu, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(cu.getType())));
					}
				}
				if (WeaponType.checkWeapon(ci)) {
					if (e.getSlot() == p.getInventory().getHeldItemSlot()) {
						Bukkit.getServer().getPluginManager().callEvent(
								new ItemUnuseEvent(p, ci, WeaponEquipMethod.HOTBAR_SWAP, getWeaponType(ci.getType())));
					}
				}
				try {
					if (isArmor(ci.getType()) && e.getSlotType() == SlotType.ARMOR) {
						if (getArmorType(ci.getType()) == ArmorType.HELMET)
							Bukkit.getServer().getPluginManager()
									.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.HELMET, ci));
						else if (getArmorType(ci.getType()) == ArmorType.CHESTPLATE)
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.CHESTPLATE, ci));
						else if (getArmorType(ci.getType()) == ArmorType.LEGGINGS)
							Bukkit.getServer().getPluginManager().callEvent(
									new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.LEGGINGS, ci));
						else if (getArmorType(ci.getType()) == ArmorType.BOOTS)
							Bukkit.getServer().getPluginManager()
									.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.BOOTS, ci));
					}
				} catch (NullPointerException e1) {
				}
			}
		} catch (NullPointerException e2) {
		}
	}

	public static boolean isArmor(Material type) {
		switch (type) {

		case DIAMOND_HELMET:
			return true;
		case DIAMOND_CHESTPLATE:
			return true;
		case DIAMOND_LEGGINGS:
			return true;
		case DIAMOND_BOOTS:
			return true;
		case IRON_HELMET:
			return true;
		case IRON_CHESTPLATE:
			return true;
		case IRON_LEGGINGS:
			return true;
		case IRON_BOOTS:
			return true;
		case CHAINMAIL_HELMET:
			return true;
		case CHAINMAIL_CHESTPLATE:
			return true;
		case CHAINMAIL_LEGGINGS:
			return true;
		case CHAINMAIL_BOOTS:
			return true;
		case GOLD_HELMET:
			return true;
		case GOLD_CHESTPLATE:
			return true;
		case GOLD_LEGGINGS:
			return true;
		case GOLD_BOOTS:
			return true;
		case LEATHER_HELMET:
			return true;
		case LEATHER_CHESTPLATE:
			return true;
		case LEATHER_LEGGINGS:
			return true;
		case LEATHER_BOOTS:
			return true;
		default:
			return false;
		}

	}

	public static ArmorType getArmorType(Material m) {
		switch (m) {

		case DIAMOND_HELMET:
			return ArmorType.HELMET;
		case DIAMOND_CHESTPLATE:
			return ArmorType.CHESTPLATE;
		case DIAMOND_LEGGINGS:
			return ArmorType.LEGGINGS;
		case DIAMOND_BOOTS:
			return ArmorType.BOOTS;
		case IRON_HELMET:
			return ArmorType.HELMET;
		case IRON_CHESTPLATE:
			return ArmorType.CHESTPLATE;
		case IRON_LEGGINGS:
			return ArmorType.LEGGINGS;
		case IRON_BOOTS:
			return ArmorType.BOOTS;
		case CHAINMAIL_HELMET:
			return ArmorType.HELMET;
		case CHAINMAIL_CHESTPLATE:
			return ArmorType.CHESTPLATE;
		case CHAINMAIL_LEGGINGS:
			return ArmorType.LEGGINGS;
		case CHAINMAIL_BOOTS:
			return ArmorType.BOOTS;
		case GOLD_HELMET:
			return ArmorType.HELMET;
		case GOLD_CHESTPLATE:
			return ArmorType.CHESTPLATE;
		case GOLD_LEGGINGS:
			return ArmorType.LEGGINGS;
		case GOLD_BOOTS:
			return ArmorType.BOOTS;
		case LEATHER_HELMET:
			return ArmorType.HELMET;
		case LEATHER_CHESTPLATE:
			return ArmorType.CHESTPLATE;
		case LEATHER_LEGGINGS:
			return ArmorType.LEGGINGS;
		case LEATHER_BOOTS:
			return ArmorType.BOOTS;
		default:
			return ArmorType.CHESTPLATE;
		}

	}

	public boolean isFull(Inventory i) {
		return i.firstEmpty() == -1;
	}

	@EventHandler
	public void onItemBreak(PlayerItemBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack ci = e.getBrokenItem();

		if (isArmor(ci.getType())) {
			if (getArmorType(ci.getType()) == ArmorType.HELMET) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.HELMET, ci));
			} else if (getArmorType(ci.getType()) == ArmorType.CHESTPLATE) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.CHESTPLATE, ci));
			} else if (getArmorType(ci.getType()) == ArmorType.LEGGINGS) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.LEGGINGS, ci));
			} else if (getArmorType(ci.getType()) == ArmorType.BOOTS) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.BOOTS, ci));
			}
		} else if (WeaponType.checkWeapon(ci)) {
			Bukkit.getServer().getPluginManager()
					.callEvent(new ItemUnuseEvent(p, ci, WeaponEquipMethod.BREAK, getWeaponType(ci.getType())));
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		ItemStack[] iss = p.getInventory().getArmorContents();
		try {
			if (iss[0] != null || iss[0].getType() != Material.AIR) {
				Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.LOG_IN,
						ArmorType.BOOTS, new ItemStack(Material.AIR), iss[0]));
			}
		} catch (NullPointerException e2) {
		}
		try {
			if (iss[1] != null || iss[1].getType() != Material.AIR) {
				Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.LOG_IN,
						ArmorType.LEGGINGS, new ItemStack(Material.AIR), iss[1]));
			}
		} catch (NullPointerException e2) {
		}
		try {
			if (iss[2] != null || iss[2].getType() != Material.AIR) {
				Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.LOG_IN,
						ArmorType.CHESTPLATE, new ItemStack(Material.AIR), iss[2]));
			}
		} catch (NullPointerException e2) {
		}
		try {
			if (iss[3] != null || iss[3].getType() != Material.AIR) {
				Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.LOG_IN,
						ArmorType.HELMET, new ItemStack(Material.AIR), iss[3]));
			}
		} catch (NullPointerException e2) {
		}
	}

	@EventHandler
	public void onPick(PlayerPickupItemEvent e) {

		Item i = e.getItem();
		ItemStack is = i.getItemStack();
		if (!WeaponType.isWeapon(is.getType()))
			return;

		Player player = e.getPlayer();

		int firstEmpty = player.getInventory().firstEmpty();
		if (player.getInventory().getHeldItemSlot() == firstEmpty) {
			Bukkit.getPluginManager().callEvent(new ItemSwapEvent(player, is, WeaponEquipMethod.PICK_UP,
					(WeaponType) WeaponType.getType(is.getType())));

		}

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if (!(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK))
			return;
		ItemStack is = e.getItem();
		ItemStack cu = new ItemStack(Material.AIR); // AIR

		if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
			Block b = e.getClickedBlock();
			if (b.getState() instanceof InventoryHolder)
				return;

		}

		if (!(is == null || is.getType() == Material.AIR) && isArmor(is.getType())) {

			ArmorType type = (ArmorType) ArmorType.getType(is.getType());
			ItemStack[] armorContents = p.getInventory().getArmorContents();
			int slot = ArmorType.getSlotNumber(type);
			ItemStack armorContent = armorContents[slot];

			if (!(armorContent == null || armorContent.getType() == Material.AIR) && isArmor(armorContent.getType())) {
				cu = armorContent;
				int i = p.getInventory().getHeldItemSlot();
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
					p.getInventory().setItem(i, armorContent);
				}, 1);

				armorContents[slot] = null;
				p.getInventory().setArmorContents(armorContents);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0f, 2.0f);
				Bukkit.getServer().getPluginManager().callEvent(new ArmorUnequipEvent(p, EquipMethod.APPLY,
						(ArmorType) ArmorType.getType(armorContent.getType()), armorContent));

			}

			try {
				if (getArmorType(is.getType()) == ArmorType.HELMET) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.HELMET, cu, is));
				} else if (getArmorType(is.getType()) == ArmorType.CHESTPLATE) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.CHESTPLATE, cu, is));
				} else if (getArmorType(is.getType()) == ArmorType.LEGGINGS) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.LEGGINGS, cu, is));
				} else if (getArmorType(is.getType()) == ArmorType.BOOTS) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.BOOTS, cu, is));
				}
			} catch (NullPointerException e2) {
				if (getArmorType(is.getType()) == ArmorType.HELMET) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.HELMET, cu, is));
				} else if (getArmorType(is.getType()) == ArmorType.CHESTPLATE) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.CHESTPLATE, cu, is));
				} else if (getArmorType(is.getType()) == ArmorType.LEGGINGS) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.LEGGINGS, cu, is));
				} else if (getArmorType(is.getType()) == ArmorType.BOOTS) {
					Bukkit.getServer().getPluginManager()
							.callEvent(new ArmorEquipEvent(p, EquipMethod.APPLY, ArmorType.BOOTS, cu, is));
				}
			}
		}

	}

	@EventHandler
	public void onDispenserActivation(BlockDispenseEvent e) {
		Block b = e.getBlock();
		ItemStack is = e.getItem();
		Material m = is.getType();

		if (e.getBlock().getType() == Material.DISPENSER) {

			if (isArmor(m)) {
				try {
					Player p = getPlayerFromDirection(b);

					if (getArmorType(m) == ArmorType.BOOTS && p.getInventory().getArmorContents()[0] == null) {
						Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.DISPENSER,
								ArmorType.BOOTS, new ItemStack(Material.AIR), is));
					} else if (getArmorType(m) == ArmorType.LEGGINGS
							&& p.getInventory().getArmorContents()[1] == null) {
						Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.DISPENSER,
								ArmorType.LEGGINGS, new ItemStack(Material.AIR), is));
					} else if (getArmorType(m) == ArmorType.CHESTPLATE
							&& p.getInventory().getArmorContents()[2] == null) {
						Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.DISPENSER,
								ArmorType.CHESTPLATE, new ItemStack(Material.AIR), is));
					} else if (getArmorType(m) == ArmorType.HELMET && p.getInventory().getArmorContents()[3] == null) {
						Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.DISPENSER,
								ArmorType.HELMET, new ItemStack(Material.AIR), is));
					}
				} catch (NullPointerException e1) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	private Player getPlayerFromDirection(Block b) throws NullPointerException {
		BlockFace f = b.getFace(b);

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getWorld() == b.getWorld()) {

				Location pl = new Location(p.getWorld(), Math.floor(p.getLocation().getX()),
						Math.floor(p.getLocation().getY()), Math.floor(p.getLocation().getZ()));

				switch (f.toString().toUpperCase()) {

				case "SOUTH":
					Location l = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ() + 1);
					if (locationsAreEqual(pl, l)) {
						return p;
					}
				case "NORTH":
					Location l1 = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ() - 1);
					if (locationsAreEqual(pl, l1)) {
						return p;
					}
				case "EAST":
					Location l2 = new Location(b.getWorld(), b.getX() + 1, b.getY(), b.getZ() + 1);
					if (locationsAreEqual(pl, l2)) {
						return p;
					}
				case "WEST":
					Location l3 = new Location(b.getWorld(), b.getX() - 1, b.getY(), b.getZ());
					if (locationsAreEqual(pl, l3)) {
						return p;
					}
				case "UP":
					Location l4 = new Location(b.getWorld(), b.getX(), b.getY() + 1, b.getZ());
					if (locationsAreEqual(pl, l4)) {
						return p;
					}
				case "DOWN":
					Location l5 = new Location(b.getWorld(), b.getX(), b.getY() - 2, b.getZ());
					if (locationsAreEqual(pl, l5)) {
						return p;
					}
				default:
					Location l6 = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ());
					if (locationsAreEqual(pl, l6)) {
						return p;
					}

				}

			}
		}
		throw new NullPointerException();
	}

	private boolean locationsAreEqual(Location pl, Location l) {
		if (pl.getX() == l.getX() && pl.getY() == l.getY() && pl.getZ() == l.getZ())
			return true;
		return false;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();

		if (p.getInventory().getHelmet() != null && isArmor(p.getInventory().getArmorContents()[3].getType())) {
			ItemStack ci = p.getInventory().getArmorContents()[3];
			Bukkit.getServer().getPluginManager()
					.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.HELMET, ci));
		}
		if (p.getInventory().getChestplate() != null && isArmor(p.getInventory().getArmorContents()[2].getType())) {
			ItemStack ci = p.getInventory().getArmorContents()[2];
			Bukkit.getServer().getPluginManager()
					.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.CHESTPLATE, ci));
		}
		if (p.getInventory().getLeggings() != null && isArmor(p.getInventory().getArmorContents()[1].getType())) {
			ItemStack ci = p.getInventory().getArmorContents()[1];
			Bukkit.getServer().getPluginManager()
					.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.LEGGINGS, ci));
		}
		if (p.getInventory().getBoots() != null && isArmor(p.getInventory().getArmorContents()[0].getType())) {
			ItemStack ci = p.getInventory().getArmorContents()[0];
			Bukkit.getServer().getPluginManager()
					.callEvent(new ArmorUnequipEvent(p, EquipMethod.SHIFT_CLICK, ArmorType.BOOTS, ci));
		}
		if (p.getItemInHand() != null && WeaponType.checkWeapon(p.getItemInHand())) {
			ItemStack ci = p.getItemInHand();
			Bukkit.getServer().getPluginManager()
					.callEvent(new ItemUnuseEvent(p, ci, WeaponEquipMethod.DROP, getWeaponType(ci.getType())));
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		ItemStack is = e.getItemDrop().getItemStack();
		Player p = e.getPlayer();

		if (isArmor(is.getType())
				&& p.getInventory().getArmorContents()[getArmorNumber(is.getType())].getType() == Material.AIR) {
			Bukkit.getServer().getPluginManager()
					.callEvent(new ArmorUnequipEvent(p, EquipMethod.DROP, getArmorType(is.getType()), is));
		} else if (WeaponType.checkWeapon(is)) {
			Bukkit.getServer().getPluginManager()
					.callEvent(new ItemUnuseEvent(p, is, WeaponEquipMethod.DROP, getWeaponType(is.getType())));
		}

	}

	public static WeaponType getWeaponType(Material type) {
		switch (type) {
		case DIAMOND_SWORD:
			return WeaponType.SWORD;
		case IRON_SWORD:
			return WeaponType.SWORD;
		case GOLD_SWORD:
			return WeaponType.SWORD;
		case STONE_SWORD:
			return WeaponType.SWORD;
		case WOOD_SWORD:
			return WeaponType.SWORD;
		case DIAMOND_AXE:
			return WeaponType.AXE;
		case IRON_AXE:
			return WeaponType.AXE;
		case GOLD_AXE:
			return WeaponType.AXE;
		case STONE_AXE:
			return WeaponType.AXE;
		case WOOD_AXE:
			return WeaponType.AXE;
		case DIAMOND_SPADE:
			return WeaponType.SHOVEL;
		case IRON_SPADE:
			return WeaponType.SHOVEL;
		case GOLD_SPADE:
			return WeaponType.SHOVEL;
		case STONE_SPADE:
			return WeaponType.SHOVEL;
		case WOOD_SPADE:
			return WeaponType.SHOVEL;
		case DIAMOND_PICKAXE:
			return WeaponType.PICKAXE;
		case IRON_PICKAXE:
			return WeaponType.PICKAXE;
		case GOLD_PICKAXE:
			return WeaponType.PICKAXE;
		case STONE_PICKAXE:
			return WeaponType.PICKAXE;
		case WOOD_PICKAXE:
			return WeaponType.PICKAXE;
		default:
			break;
		}
		return null;

	}

	private int getArmorNumber(Material type) {
		switch (getArmorType(type)) {
		case HELMET:
			return 3;
		case CHESTPLATE:
			return 2;
		case LEGGINGS:
			return 1;
		case BOOTS:
			return 0;
		default:
			break;

		}
		return 0;
	}

}
