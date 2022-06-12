package io.github.jglrxavpok.invokablemounts.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jglrxavpok.invokablemounts.Maths;
import io.github.jglrxavpok.invokablemounts.entities.MountEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMountRenderer<T extends Mob & MountEntity> extends MobRenderer<T, GhostModelWrapper<T>> {

    /**
     * We need to keep a separate list as the model inside SaddleLayer is not accessible (we could use an AccessTransformer,
     * but this solution also creates cleaner code)
     */
    protected final List<GhostModelWrapper<T>> models = new ArrayList<>();

    public BaseMountRenderer(EntityRendererProvider.Context context, GhostModelWrapper<T> model) {
        super(context, model, 0.0f /* no shadow */);

        models.add(model);
    }

    protected float getBaseAlpha() {
        return 0.6f;
    }

    protected float getBaseRed() {
        return 1.0f;
    }

    protected float getBaseGreen() {
        return 1.0f;
    }

    protected float getBaseBlue() {
        return 1.0f;
    }

    @Override
    public void render(T entity, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource bufferSource, int p_115460_) {
        final RenderType renderType = getRenderType(entity, true, false, false);
        float alpha = 1.0f;
        for(var model : models) {
            model.preRender(bufferSource, renderType);

            int ticks = entity.getRemainingTicksBeforeDisappearing();
            if(ticks < MountEntity.TICK_COUNT_PER_DESPAWN) {
                float ratio = ticks / ((float) MountEntity.TICK_COUNT_PER_DESPAWN);
                alpha = 1.0f - Maths.easeInOutExpo(1.0f - ratio);
            }

            model.setColor(getBaseRed(), getBaseGreen(), getBaseBlue(), alpha * getBaseAlpha());
        }

        super.render(entity, p_115456_, p_115457_, poseStack, bufferSource, p_115460_);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T entity, boolean bodyVisible, boolean invisibleToClient, boolean glowing) {
        return RenderType.itemEntityTranslucentCull(getTextureLocation(entity)); // itemEntityTranslucentCull actually makes blending work with water/glass blocks
    }
}
