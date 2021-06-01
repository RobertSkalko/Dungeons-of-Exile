package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.util.AttributeBuilder;

public enum DefaultAttributes {

    GOLEM_BOSS {
        @Override
        public AttributeBuilder getDefault() {
            return new AttributeBuilder(125, 0.25F, 12).knockbackResist(1);
        }
    },
    SLIME {
        @Override
        public AttributeBuilder getDefault() {
            return new AttributeBuilder(30, 5, 5).knockbackResist(0.5F);
        }
    },
    SMALL_GOLEM {
        @Override
        public AttributeBuilder getDefault() {
            return new AttributeBuilder(40, 0.3F, 5).knockbackResist(0.8F);
        }
    };

    public abstract AttributeBuilder getDefault();

}
