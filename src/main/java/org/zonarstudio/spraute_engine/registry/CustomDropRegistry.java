package org.zonarstudio.spraute_engine.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDropRegistry {
    public static class DropRule {
        public String itemId;
        public int min;
        public int max;
        public int chance;
        public boolean replace;
        public String nbt;
        
        public DropRule(String itemId, int min, int max, int chance, boolean replace, String nbt) {
            this.itemId = itemId;
            this.min = min;
            this.max = max;
            this.chance = chance;
            this.replace = replace;
            this.nbt = nbt;
        }
    }
    
    public static final Map<String, List<DropRule>> MOB_DROPS = new HashMap<>();
    public static final Map<String, List<DropRule>> BLOCK_DROPS = new HashMap<>();
    
    public static void addMobDrop(String mobId, String itemId, int min, int max, int chance, boolean replace, String nbt) {
        if (!mobId.contains(":")) mobId = "minecraft:" + mobId;
        MOB_DROPS.computeIfAbsent(mobId, k -> new ArrayList<>()).add(new DropRule(itemId, min, max, chance, replace, nbt));
    }
    
    public static void addBlockDrop(String blockId, String itemId, int min, int max, int chance, boolean replace, String nbt) {
        if (!blockId.contains(":")) blockId = "minecraft:" + blockId;
        BLOCK_DROPS.computeIfAbsent(blockId, k -> new ArrayList<>()).add(new DropRule(itemId, min, max, chance, replace, nbt));
    }
}