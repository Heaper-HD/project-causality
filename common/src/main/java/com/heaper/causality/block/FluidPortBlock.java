package com.heaper.causality.block;

import com.heaper.causality.block.entity.FluidPortBE;
import com.heaper.causality.port.FluidPort;
import com.heaper.causality.port.FluidPortSpec;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.PortType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class FluidPortBlock extends MachinePortBlock {

    private final FluidPortSpec spec;

    public FluidPortBlock(PortDirection direction, FluidPortSpec spec, Properties properties) {
        super(direction, PortType.FLUID, properties);
        this.spec = spec;
    }

    public FluidPortSpec spec() {
        return spec;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FluidPortBE(blockPos, blockState, direction(), spec);
    }
}
