package io.github.jglrxavpok.invokablemounts.client.renderers;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PigMountRenderer extends BaseMountRenderer<PigMount> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(InvokableMountsMod.MODID, "textures/entity/pig_mount/pig_mount.png");
    private static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");

    public PigMountRenderer(EntityRendererProvider.Context p_174340_) {
        super(p_174340_, new GhostModelWrapper<>(new PigModel<>(p_174340_.bakeLayer(ModelLayers.PIG))));

        GhostModelWrapper<PigMount> layerModel = new GhostModelWrapper<>(new PigModel<>(p_174340_.bakeLayer(ModelLayers.PIG_SADDLE)));
        this.addLayer(new SaddleLayer<>(this, layerModel, SADDLE_TEXTURE));

        for (var layer : layers) {
            models.add(layer.getParentModel());
        }
        models.add(layerModel);
    }

    @Override
    public ResourceLocation getTextureLocation(PigMount entity) {
        return TEXTURE_LOCATION;
    }
}