package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties;

import com.google.gson.annotations.SerializedName;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

/**
 * Reprensentation of Json {@link net.minecraft.block.CropsBlock} properties
 *
 * @author ZeAmateis
 */
public class CropsProperties {

    private String seeds;
    private Age ageProperty;
    private int maxAge;

    //Bone meal manipulation
    private boolean canUseBoneMeal;
    @SerializedName("boneMealGrowChance")
    private BoneMealGrowObject boneMealGrowObject;

    public String getSeeds() {
        return seeds;
    }

    public IntegerProperty getAgeProperty() {
        return ageProperty.getAgeProperties();
    }

    public int getMaxAge() {
        return maxAge;
    }

    public boolean canUseBoneMeal() {
        return canUseBoneMeal;
    }

    public BoneMealGrowObject getBoneMealGrowObject() {
        return boneMealGrowObject;
    }

    public enum Age {
        AGE_0_1(BlockStateProperties.AGE_0_1),
        AGE_0_2(BlockStateProperties.AGE_0_2),
        AGE_0_3(BlockStateProperties.AGE_0_3),
        AGE_0_5(BlockStateProperties.AGE_0_5),
        AGE_0_7(BlockStateProperties.AGE_0_7),
        AGE_0_15(BlockStateProperties.AGE_0_15),
        AGE_0_25(BlockStateProperties.AGE_0_25);

        IntegerProperty ageProperties;

        Age(IntegerProperty age) {
            this.ageProperties = age;
        }

        public IntegerProperty getAgeProperties() {
            return ageProperties;
        }
    }

    public static class BoneMealGrowObject {
        private int min, max;

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }
}
