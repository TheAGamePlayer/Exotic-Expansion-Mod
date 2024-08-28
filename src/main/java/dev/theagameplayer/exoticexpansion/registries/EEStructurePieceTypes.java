package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.structures.InfernalNetherFortressPieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEStructurePieceTypes {
	public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE = DeferredRegister.create(Registries.STRUCTURE_PIECE, ExoticExpansionMod.MODID);
	
	//Infernal Fortress
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_END_FILLER1 = STRUCTURE_PIECE.register("inf_bridge_end_filler1", () -> InfernalNetherFortressPieces.BridgeEndFiller1::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_END_FILLER2 = STRUCTURE_PIECE.register("inf_bridge_end_filler2", () -> InfernalNetherFortressPieces.BridgeEndFiller2::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_STRAIGHT1 = STRUCTURE_PIECE.register("inf_bridge_straight1", () -> InfernalNetherFortressPieces.BridgeStraight1::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_STRAIGHT2 = STRUCTURE_PIECE.register("inf_bridge_straight2", () -> InfernalNetherFortressPieces.BridgeStraight2::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_STRAIGHT3 = STRUCTURE_PIECE.register("inf_bridge_straight3", () -> InfernalNetherFortressPieces.BridgeStraight3::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_CROSSING1 = STRUCTURE_PIECE.register("inf_bridge_crossing1", () -> InfernalNetherFortressPieces.BridgeCrossing1::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_CROSSING2 = STRUCTURE_PIECE.register("inf_bridge_crossing2", () -> InfernalNetherFortressPieces.BridgeCrossing2::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_BRIDGE_CROSSING3 = STRUCTURE_PIECE.register("inf_bridge_crossing3", () -> InfernalNetherFortressPieces.BridgeCrossing3::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_ROOM_CROSSING = STRUCTURE_PIECE.register("inf_room_crossing", () -> InfernalNetherFortressPieces.RoomCrossing::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_LAVA_ROOM_CROSSING = STRUCTURE_PIECE.register("inf_lava_room_crossing", () -> InfernalNetherFortressPieces.LavaRoomCrossing::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_ENTRANCE = STRUCTURE_PIECE.register("inf_castle_entrance", () -> InfernalNetherFortressPieces.CastleEntrance::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_STAIRS_ROOM = STRUCTURE_PIECE.register("inf_stairs_room", () -> InfernalNetherFortressPieces.StairsRoom::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_MONSTER_THRONE = STRUCTURE_PIECE.register("inf_monster_throne", () -> InfernalNetherFortressPieces.MonsterThrone::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_GRAND_MONSTER_THRONE = STRUCTURE_PIECE.register("inf_grand_monster_throne", () -> InfernalNetherFortressPieces.GrandMonsterThrone::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_SMALL_CORRIDOR_LEFT_TURN = STRUCTURE_PIECE.register("inf_castle_small_corridor_left_turn", () -> InfernalNetherFortressPieces.CastleSmallCorridorLeftTurnPiece::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_SMALL_CORRIDOR_RIGHT_TURN = STRUCTURE_PIECE.register("inf_castle_small_corridor_right_turn", () -> InfernalNetherFortressPieces.CastleSmallCorridorRightTurnPiece::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_CORRIDOR_STAIRS = STRUCTURE_PIECE.register("inf_castle_corridor_stairs", () -> InfernalNetherFortressPieces.CastleCorridorStairsPiece::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_CORRIDOR_T_BALCONY = STRUCTURE_PIECE.register("inf_castle_corridor_t_balcony", () -> InfernalNetherFortressPieces.CastleCorridorTBalconyPiece::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_SMALL_CORRIDOR = STRUCTURE_PIECE.register("inf_castle_small_corridor", () -> InfernalNetherFortressPieces.CastleSmallCorridorPiece::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_CASTLE_STALK_ROOM = STRUCTURE_PIECE.register("inf_castle_stalk_room", () -> InfernalNetherFortressPieces.CastleStalkRoom::new);
	public static final Supplier<StructurePieceType.ContextlessType> INF_START = STRUCTURE_PIECE.register("inf_start", () -> InfernalNetherFortressPieces.StartPiece::new);
}
