package io.github.jglrxavpok.invokablemounts.client.renderers;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseMountRenderer extends BaseMountRenderer<HorseMount> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(InvokableMountsMod.MODID, "textures/entity/horse_mount/horse_mount.png");

    public HorseMountRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostModelWrapper<>(new HorseModel<>(context.bakeLayer(ModelLayers.HORSE))));
    }

    @Override
    public ResourceLocation getTextureLocation(HorseMount entity) {
        return TEXTURE_LOCATION;
    }
}