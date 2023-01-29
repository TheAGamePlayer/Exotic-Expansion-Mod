package dev.theagameplayer.blightedworlds.registries.other;

import dev.theagameplayer.blightedworlds.world.level.PortalMode;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public final class BWBlockStateProperties {
	public static final BooleanProperty ORB = BooleanProperty.create("orb");
	public static final EnumProperty<PortalMode> PORTAL_MODE = EnumProperty.create("portal_mode", PortalMode.class);
}
