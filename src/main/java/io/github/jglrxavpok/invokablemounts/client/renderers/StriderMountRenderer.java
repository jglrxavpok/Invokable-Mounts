package io.github.jglrxavpok.invokablemounts.client.renderers;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import io.github.jglrxavpok.invokablemounts.entities.StriderMount;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StriderMountRenderer extends BaseMountRenderer<StriderMount> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(InvokableMountsMod.MODID, "textures/entity/strider_mount/strider_mount.png");
    private static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation(InvokableMountsMod.MODID, "textures/entity/strider_mount/strider_saddle.png");

    public StriderMountRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostModelWrapper<>(new StriderModel<>(context.bakeLayer(ModelLayers.STRIDER))));

        GhostModelWrapper<StriderMount> layerModel = new GhostModelWrapper<>(new StriderModel<>(context.bakeLayer(ModelLayers.STRIDER_SADDLE)));
        this.addLayer(new SaddleLayer<>(this, layerModel, SADDLE_TEXTURE));

        for (var layer : layers) {
            models.add(layer.getParentModel());
        }
        models.add(layerModel);
    }

    @Override
    public ResourceLocation getTextureLocation(StriderMount entity) {
        return TEXTURE_LOCATION;
    }

    protected boolean isShaking(StriderMount p_116070_) {
        return super.isShaking(p_116070_) || p_116070_.isSuffocating();
    }
}