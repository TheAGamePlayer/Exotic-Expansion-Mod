package dev.theagameplayer.blightedworlds.world.level;

import net.minecraft.util.StringRepresentable;

public enum PortalMode implements StringRepresentable {
	ACTIVE("active", true),
	INACTIVE("inactive", false),
	USED("used", true),
	HOSTILE("hostile", false);

	private final String name;
	private final boolean canTeleport;
	
	PortalMode(final String nameIn, final boolean canTeleportIn) {
		this.name = nameIn;
		this.canTeleport = canTeleportIn;
	}
	
	@Override
	public final String getSerializedName() {
		return this.name;
	}
	
	public final boolean canTeleport() {
		return this.canTeleport;
	}
}
