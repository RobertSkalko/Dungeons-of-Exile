package com.robertx22.world_of_exile.main.entities;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.entities.registration.MobManager;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class MobData<T extends LivingEntity> {

    public Identifier identifier;

    public Set<Consumer<ExileEvents.OnMobDeath>> onDeathEvents = new HashSet<>();
    public Set<Consumer<ExileEvents.OnEntityTick>> onTickEvents = new HashSet<>();
    public Set<Consumer<OnAttackSomeoneEvent>> onAttackSomeoneEvents = new HashSet<>();
    public Set<Consumer<OnHurtEvent>> onHurtEvents = new HashSet<>();

    public Set<MobTags> tags = new HashSet<>();

    public SpawnEvents spawnEvents = new SpawnEvents();

    public MobData(String id) {
        this.identifier = WOE.id(id);
    }

    public abstract MobType getMobType();

    public abstract MobRenderInfo getRenderInfo();

    public abstract MobRenderer getRenderer();

    public abstract AttributeBuilder getAttributes();

    public abstract Size getSizeDimensions();

    public abstract Goals getDefaultGoals();

    public LivingEntity spawn(World world, BlockPos pos) {

        try {
            EntityType type = MobManager.MAP.entrySet()
                .stream()
                .filter(x -> x.getValue()
                    .equals(this))
                .findFirst()
                .get()
                .getKey();

            LivingEntity en = (LivingEntity) type.create(world);

            en.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
            ServerWorld sw = (ServerWorld) world;

            if (en instanceof MobEntity) {
                MobEntity mob = (MobEntity) en;
                mob.initialize(sw, sw.getLocalDifficulty(en.getBlockPos()), SpawnReason.EVENT, null, null);
            }

            sw.spawnEntityAndPassengers(en);

            return en;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public abstract void addCustomGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector);

    public final void addGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector) {
        this.getDefaultGoals()
            .addDefaultGoals(en, goalSelector, targetSelector);
        this.addCustomGoals(en, goalSelector, targetSelector);
    }

    public enum Goals {
        MELEE {
            @Override
            public void addDefaultGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector) {
                goalSelector.add(0, new SwimGoal(en));
                goalSelector.add(1, new MeleeAttackGoal(en, 1.0D, true));
                targetSelector.add(2, new FollowTargetGoal<>(en, PlayerEntity.class, true));
                goalSelector.add(4, new WanderAroundGoal(en, 0.6D));
                goalSelector.add(7, new LookAtEntityGoal(en, PlayerEntity.class, 16));
                goalSelector.add(8, new LookAroundGoal(en));
                targetSelector.add(2, new RevengeGoal(en, new Class[0]));
                targetSelector.add(4, new UniversalAngerGoal(en, false));
            }
        };

        public abstract void addDefaultGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector);

    }

    public enum Size {

        BIG_GOLEM(new EntityDimensions(1.4F, 2.7F, true)),
        SMALL_GOLEM(new EntityDimensions(0.9F, 0.9F, true)),
        HUMAN(new EntityDimensions(0.5F, 1.9F, true));

        Size(EntityDimensions dimensions) {
            this.dimensions = dimensions;
        }

        public EntityDimensions dimensions;

    }

}
