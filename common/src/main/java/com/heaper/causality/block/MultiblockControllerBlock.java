package com.heaper.causality.block;

import com.heaper.causality.block.entity.MultiblockControllerBE;
import com.heaper.causality.multiblock.MultiblockDefinition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class MultiblockControllerBlock extends BaseEntityBlock {
    public static final MapCodec<MultiblockControllerBlock> CODEC =
            simpleCodec(props -> new MultiblockControllerBlock(null, props));

    private final MultiblockDefinition definition;

    public MultiblockControllerBlock(MultiblockDefinition definition, Properties properties) {
        super(properties);
        this.definition = definition;
        registerDefaultState(getStateDefinition().any()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH));
    }

    public MultiblockDefinition definition() { return definition; }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(
                HorizontalDirectionalBlock.FACING,
                context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MultiblockControllerBE(pos, state, definition);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (level.getBlockEntity(pos) instanceof MultiblockControllerBE be)
            be.tryForm(player);

        return InteractionResult.SUCCESS;
    }
}
