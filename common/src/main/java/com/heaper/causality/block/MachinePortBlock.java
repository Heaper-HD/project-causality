package com.heaper.causality.block;

import com.heaper.causality.block.entity.MachinePortBE;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.PortType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public abstract class MachinePortBlock extends Block implements EntityBlock {

    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;

    private final PortDirection direction;
    private final PortType type;

    protected MachinePortBlock(PortDirection direction, PortType type, Properties properties) {
        super(properties);
        this.direction = direction;
        this.type = type;
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    public PortDirection direction() { return direction; }
    public PortType portType() { return type; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING,
                context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (level.getBlockEntity(pos) instanceof MachinePortBE be)
            player.openMenu(be, buf -> buf.writeBlockPos(pos));

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        if (level.isClientSide() || direction != PortDirection.OUTPUT) return null;

        return (tickLevel, tickPos, tickState, be) -> {
            if (be instanceof MachinePortBE port) port.pushTick();
        };
    }

    protected void dropContents(Level level, BlockPos pos) {}

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
        dropContents(level, pos);
        return super.onDestroyedByPlayer(state, level, pos, player, toolStack, willHarvest, fluid);
    }

    @Override
    public void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        dropContents(level, pos);
        super.onBlockExploded(state, level, pos, explosion);
    }
}
