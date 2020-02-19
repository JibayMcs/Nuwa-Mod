package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties;

/**
 * Reprensentation of Json {@link net.minecraft.block.FallingBlock} properties
 *
 * @author ZeAmateis
 */
public class FallingProperties {

    private int dustColor;
    private double[] fallingVector;
    private boolean noGravity, hurtEntities, shouldDropItem, setNoBlockOnLand;
    private float fallDamageAmount = -1.0F;

    public int getDustColor() {
        return dustColor;
    }

    public double[] getFallingVector() {
        return fallingVector;
    }

    public boolean hasNoGravity() {
        return noGravity;
    }

    public boolean isHurtingEntities() {
        return hurtEntities;
    }

    public boolean shouldDropItem() {
        return shouldDropItem;
    }

    public boolean setNoBlockOnLand() {
        return setNoBlockOnLand;
    }

    public float getFallDamageAmount() {
        return fallDamageAmount;
    }
}
