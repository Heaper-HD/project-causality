package com.heaper.causality.block;

import com.heaper.causality.component.ComponentType;
import com.heaper.causality.core.material.Material;

public interface ComponentBlock {
    Material material();
    ComponentType componentType();
}
