package com.heaper.causality.block;

import com.heaper.causality.block.entity.ItemPortBE;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.SlottedItemStorage;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class ItemPortBlock extends BaseEntityBlock {

    public static final MapCodec<ItemPortBlock> CODEC =
            simpleCodec(props -> new ItemPortBlock(PortDirection.INPUT, props));

    public final PortDirection direction;

    public ItemPortBlock(PortDirection direction, Properties properties) {
        super(properties);
        this.direction = direction;
    }

    public PortDirection direction() {
        return direction;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ItemPortBE(blockPos, blockState, direction);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (level.getBlockEntity(pos) instanceof ItemPortBE be)
            player.openMenu(be, buf -> buf.writeBlockPos(pos));

        return InteractionResult.SUCCESS;
    }

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

    private static void dropContents(Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof ItemPortBE be)) return;

        SlottedItemStorage storage = be.storage();
        for (int i = 0; i < storage.size(); i++) {
            ItemStack stack = storage.stackInSlot(i);
            if (!stack.isEmpty())
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }
}
