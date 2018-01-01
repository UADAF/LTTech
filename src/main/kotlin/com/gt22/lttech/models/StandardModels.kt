package com.gt22.lttech.models

import com.gt22.lttech.R
import net.minecraft.client.renderer.block.model.IBakedModel
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.model.TRSRTransformation
import org.apache.commons.lang3.tuple.Pair
import ru.pearx.libmc.client.models.OvModel
import ru.pearx.libmc.client.models.processors.FacingProcessor
import javax.vecmath.Matrix4f
import javax.vecmath.Quat4f
import javax.vecmath.Vector3f

class NQReactorModel : OvModel() {
    private val mat = TRSRTransformation(Vector3f(0f, 0.3f, 0f), null, null, Quat4f(0f, 1f, 0f, 1f)).matrix
    private val handMat = TRSRTransformation(Vector3f(0f, 0.3f, 0f), null, null, Quat4f(0.2f, 1f, 0.2f, 1f)).matrix
    init {
        baseModel = ResourceLocation(R.MODID, "obj/nqr.obj")
        vertexProcessors.add(FacingProcessor())
    }

    override fun handlePerspective(t: ItemCameraTransforms.TransformType): Pair<IBakedModel, Matrix4f> {
        return Pair.of(this, if(isHand(t)) handMat else mat)
    }
}


fun isHand(t: ItemCameraTransforms.TransformType): Boolean = t == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || t == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND