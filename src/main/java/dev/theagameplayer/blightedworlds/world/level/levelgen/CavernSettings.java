package dev.theagameplayer.blightedworlds.world.level.levelgen;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dev.theagameplayer.blightedworlds.registries.custom.BWCavernSettings;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

public final class CavernSettings {
	public static final Codec<CavernSettings> DIRECT_CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(
				Codec.list(CavernFloorSettings.CODEC).fieldOf("cavern_floors").forGetter(CavernSettings::getCavernFloors)
				).apply(builder, CavernSettings::new);
	});
	public static final Codec<Holder<CavernSettings>> CODEC = RegistryFileCodec.create(BWCavernSettings.CAVERN_SETTINGS, DIRECT_CODEC);
	private final List<CavernFloorSettings> cavernFloors;
	
	public CavernSettings(final List<CavernFloorSettings> cavernFloorsIn) {
		this.cavernFloors = cavernFloorsIn;
	}
	
	public final List<CavernFloorSettings> getCavernFloors() {
		return this.cavernFloors;
	}
 	
	public static final class CavernFloorSettings {
		public static final Codec<CavernFloorSettings> CODEC = RecordCodecBuilder.create(builder -> {
			return builder.group(
					Codec.INT.fieldOf("floor_height").forGetter(CavernFloorSettings::floorHeight), 
					Codec.INT.fieldOf("min_pillar_size").forGetter(CavernFloorSettings::minPillarSize),
					Codec.INT.fieldOf("max_pillar_size").forGetter(CavernFloorSettings::maxPillarSize),
					Codec.INT.fieldOf("pillar_layers").forGetter(CavernFloorSettings::pillarLayers),
					Codec.DOUBLE.fieldOf("pillar_rarity").forGetter(CavernFloorSettings::pillarRarity)
					).apply(builder, CavernFloorSettings::new);
		});
		private final int floorHeight;
		private final int minPillarSize;
		private final int maxPillarSize;
		private final int pillarLayers;
		private final double pillarRarity;
		
		public CavernFloorSettings(final int floorHeightIn, final int minPillarSizeIn, final int maxPillarSizeIn, final int pillarLayersIn, final double pillarRarityIn) {
			this.floorHeight = floorHeightIn;
			this.minPillarSize = minPillarSizeIn;
			this.maxPillarSize = maxPillarSizeIn;
			this.pillarLayers = pillarLayersIn;
			this.pillarRarity = pillarRarityIn;
		}
		
		public final int floorHeight() {
			return this.floorHeight;
		}
		
		public final int minPillarSize() {
			return this.minPillarSize;
		}
		
		public final int maxPillarSize() {
			return this.maxPillarSize;
		}
		
		public final int pillarLayers() {
			return this.pillarLayers;
		}
		
		public final double pillarRarity() {
			return this.pillarRarity;
		}
	}
}
