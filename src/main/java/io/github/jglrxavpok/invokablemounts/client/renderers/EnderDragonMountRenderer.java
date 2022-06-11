package io.github.jglrxavpok.invokablemounts.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.github.jglrxavpok.invokablemounts.Maths;
import io.github.jglrxavpok.invokablemounts.entities.EnderDragonMount;
import io.github.jglrxavpok.invokablemounts.entities.MountEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.jetbrains.annotations.Nullable;

/**
 * Mostly a reimplementaion of BaseMountRenderer. Due to DragonModel forcing a GhostModelWrapper&lt;EnderDragon&gt; field
 * (due to the generic argument of GhostModelWrapper), we have to redo most of it.
 */
public class EnderDragonMountRenderer extends EntityRenderer<EnderDragonMount> {

    private static final ResourceLocation DRAGON_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon.png");

    private final GhostModelWrapper<EnderDragon> model;

    public EnderDragonMountRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new GhostModelWrapper<>(new EnderDragonRenderer.DragonModel(context.bakeLayer(ModelLayers.ENDER_DRAGON)));
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
    public void render(EnderDragonMount entity, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource bufferSource, int p_115460_) {
        final RenderType renderType = RenderType.itemEntityTranslucentCull(getTextureLocation(entity));
        model.preRender(bufferSource, renderType);

        float alpha = 1.0f;
        int ticks = entity.getRemainingTicksBeforeDisappearing();
        if(ticks < MountEntity.TICK_COUNT_PER_DESPAWN) {
            float ratio = ticks / ((float) MountEntity.TICK_COUNT_PER_DESPAWN);
            alpha = 1.0f - Maths.easeInOutExpo(1.0f - ratio);
        }
        model.setColor(getBaseRed(), getBaseGreen(), getBaseBlue(), alpha * getBaseAlpha());

        boolean flag = entity.hurtTime > 0;
        this.model.prepareMobModel(entity, 0.0F, 0.0F, p_115457_);

        VertexConsumer buffer = bufferSource.getBuffer(renderType);

        // from EnderDragonRenderer
        {
            poseStack.pushPose();
            float f = (float)entity.getLatencyPos(7, p_115457_)[0];
            float f1 = (float)(entity.getLatencyPos(5, p_115457_)[1] - entity.getLatencyPos(10, p_115457_)[1]);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(f1 * 10.0F));
            poseStack.translate(0.0D, 0.0D, 1.0D);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(0.0D, (double)-1.501F, 0.0D);
        }
        this.model.renderToBuffer(poseStack, buffer, p_115460_, OverlayTexture.pack(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        super.render(entity, p_115456_, p_115457_, poseStack, bufferSource, p_115460_);
    }

    @Override
    public ResourceLocation getTextureLocation(EnderDragonMount p_114206_) {
        return DRAGON_LOCATION;
    }

}
