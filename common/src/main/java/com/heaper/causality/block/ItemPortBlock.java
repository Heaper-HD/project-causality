package com.heaper.causality.block;

import com.heaper.causality.block.entity.ItemPortBE;
import com.heaper.causality.port.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
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

public class ItemPortBlock extends MachinePortBlock {

    public final PortSize size;

    public ItemPortBlock(PortDirection direction, PortSize size, Properties properties) {
        super(direction, PortType.ITEM, properties);
        this.size = size;
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    public PortSize size() {
        return size;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemPortBE(pos, state, direction(), size);
    }

    @Override
    protected void dropContents(Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof ItemPortBE be)) return;

        SlottedItemStorage storage = be.storage();
        for (int i = 0; i < storage.size(); i++) {
            ItemStack stack = storage.stackInSlot(i);
            if (!stack.isEmpty())
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }
}
