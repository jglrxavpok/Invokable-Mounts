package io.github.jglrxavpok.invokablemounts.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

/**
 * Forces a model to render in the entityTranslucent RenderType, and allow forcing its color
 */
public class GhostModelWrapper<T extends Entity> extends EntityModel<T> {

    private final EntityModel<T> model;
    private MultiBufferSource bufferSource = null;
    private RenderType overrideRenderType = null;

    private float red = 1.0f;
    private float green = 1.0f;
    private float blue = 1.0f;
    private float alpha = 1.0f;

    public GhostModelWrapper(EntityModel<T> toWrap) {
        super(RenderType::entityTranslucent);
        this.model = toWrap;
    }

    public void preRender(MultiBufferSource bufferSource, RenderType renderType) {
        this.bufferSource = bufferSource;
        this.overrideRenderType = renderType;
    }

    public void setColor(float r, float g, float b, float a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int p_103113_, int p_103114_, float red, float green, float blue, float alpha) {
        if(bufferSource == null) {
            throw new IllegalStateException("Call preRender before renderToBuffer");
        }
        if(overrideRenderType == null) {
            throw new IllegalStateException("Call preRender before renderToBuffer");
        }

        red = this.red;
        green = this.green;
        blue = this.blue;
        alpha = this.alpha;
        buffer = bufferSource.getBuffer(overrideRenderType);

        model.young = young;
        model.attackTime = attackTime;
        model.riding = riding;

        model.renderToBuffer(poseStack, buffer, p_103113_, p_103114_, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {
        model.setupAnim(p_102618_, p_102619_, p_102620_, p_102621_, p_102622_, p_102623_);
    }

    @Override
    public void prepareMobModel(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
        model.prepareMobModel(p_102614_, p_102615_, p_102616_, p_102617_);
    }
}
