package com.robertx22.world_of_exile.main.entities;

public class MobRenderInfo {

    public float scale = 1;
    public String texture;

    public MobRenderInfo(float scale, String texture) {
        this.scale = scale;
        this.texture = texture;
    }

    public MobRenderInfo(String texture) {
        this.texture = texture;
    }
}
