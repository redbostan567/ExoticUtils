package com.kingmo.utils.xp;

import org.bukkit.entity.Player;

public class ExpManager
{
  private Player player;

  public ExpManager(Player player) { this.player = player; }

  /**
   * 
   * @return the players total experience.
   */
  public int getTotalExperience() {
    int experience = 0;
    int level = this.player.getLevel();
    if (level >= 0 && level <= 15) {
      experience = (int)Math.ceil(Math.pow(level, 2.0D) + (6 * level));
      int requiredExperience = 2 * level + 7;
      double currentExp = Double.parseDouble(Float.toString(this.player.getExp()));
      return (int)(experience + Math.ceil(currentExp * requiredExperience));
    }
    if (level > 15 && level <= 30) {
      experience = (int)Math.ceil(2.5D * Math.pow(level, 2.0D) - 40.5D * level + 360.0D);
      int requiredExperience = 5 * level - 38;
      double currentExp = Double.parseDouble(Float.toString(this.player.getExp()));
      return (int)(experience + Math.ceil(currentExp * requiredExperience));
    }

    experience = (int)Math.ceil(4.5D * Math.pow(level, 2.0D) - 162.5D * level + 2220.0D);
    int requiredExperience = 9 * level - 158;
    double currentExp = Double.parseDouble(Float.toString(this.player.getExp()));
    return (int)(experience + Math.ceil(currentExp * requiredExperience));
  }


  public int getTotalExperience(int level) {
      int xp = 0;

      if (level >= 0 && level <= 15) {
          xp = (int) Math.round(Math.pow(level, 2) + 6 * level);
      } else if (level > 15 && level <= 30) {
          xp = (int) Math.round((2.5 * Math.pow(level, 2) - 40.5 * level + 360));
      } else if (level > 30) {
          xp = (int) Math.round(((4.5 * Math.pow(level, 2) - 162.5 * level + 2220)));
      }
      return xp;
  }

  public void setTotalExperience(int amount) {
      int level = 0;
      int xp = 0;
      float a = 0;
      float b = 0;
      float c = -amount;

      if (amount > getTotalExperience(0) && amount <= getTotalExperience(15)) {
          a = 1;
          b = 6;
      } else if (amount > getTotalExperience(15) && amount <= getTotalExperience(30)) {
          a = 2.5f;
          b = -40.5f;
          c += 360;
      } else if (amount > getTotalExperience(30)) {
          a = 4.5f;
          b = -162.5f;
          c += 2220;
      }
      level = (int) Math.floor((-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a));
      xp = amount - getTotalExperience(level);
      player.setLevel(level);
      player.setExp(0);
      player.giveExp(xp);
  }

  public void addXp(int amount) {
	  this.setTotalExperience(this.getTotalExperience() + amount);
  }


}
