package io.github.jglrxavpok.invokablemounts.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.PhantomMount;
import io.github.jglrxavpok.invokablemounts.entities.StriderMount;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.PhantomEyesLayer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomMountRenderer extends BaseMountRenderer<PhantomMount> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(InvokableMountsMod.MODID, "textures/entity/phantom_mount/phantom_mount.png");

    public PhantomMountRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostModelWrapper<>(new PhantomModel<>(context.bakeLayer(ModelLayers.PHANTOM))));

        // TODO: this.addLayer(new PhantomEyesLayer<>(this));
    }

    protected void scale(Phantom p_115681_, PoseStack p_115682_, float p_115683_) {
        int i = p_115681_.getPhantomSize();
        float f = 1.0F + 0.15F * (float)i;
        p_115682_.scale(f, f, f);
        p_115682_.translate(0.0D, 1.3125D, 0.1875D);
    }

    @Override
    public boolean shouldRender(PhantomMount mount, Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
        return mount.getVehicle() != Minecraft.getInstance().player || Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON;
    }

    @Override
    public void render(PhantomMount entity, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource bufferSource, int p_115460_) {
        PhantomModel<PhantomMount> phantomModel = (PhantomModel<PhantomMount>) getModel().getWrapped();
        phantomModel.root().getChild("body").getChild("head").visible = entity.getVehicle() == null;

        poseStack.pushPose();

        if(entity.getVehicle() instanceof Player p) {
            double dx = p.getX() - entity.getX();
            double dy = p.getY() - entity.getY();
            double dz = p.getZ() - entity.getZ();
            poseStack.translate(dx, dy, dz);
        }
        super.render(entity, p_115456_, p_115457_, poseStack, bufferSource, p_115460_);
        poseStack.popPose();
    }

    protected void setupRotations(PhantomMount mount, PoseStack poseStack, float p_115687_, float p_115688_, float partialTick) {
        super.setupRotations(mount, poseStack, p_115687_, p_115688_, partialTick);

        if(mount.getVehicle() instanceof Player player) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
            if(player.isFallFlying()) {
                float f1 = (float)player.getFallFlyingTicks() + partialTick;
                float f2 = Mth.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
                if (!player.isAutoSpinAttack()) {
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(f2 * (-90.0F - player.getXRot())));
                }

                Vec3 vec3 = player.getViewVector(partialTick);
                Vec3 vec31 = player.getDeltaMovement();
                double d0 = vec31.horizontalDistanceSqr();
                double d1 = vec3.horizontalDistanceSqr();
                if (d0 > 0.0D && d1 > 0.0D) {
                    double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
                    double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
                    poseStack.mulPose(Vector3f.ZP.rotation(-(float)(Math.signum(d3) * Math.acos(d2))));
                }
            }
            poseStack.translate(0.0, -1.4, -0.9);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(PhantomMount entity) {
        return TEXTURE_LOCATION;
    }
}