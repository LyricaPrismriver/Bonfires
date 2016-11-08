package uk.co.wehavecookies56.bonfires.world;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import uk.co.wehavecookies56.bonfires.Bonfire;
import uk.co.wehavecookies56.bonfires.BonfireRegistry;
import uk.co.wehavecookies56.bonfires.Bonfires;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Toby on 07/11/2016.
 */
public class BonfireWorldSavedData extends WorldSavedData {

    private static final String DATA_NAME = Bonfires.modid + "_bonfiredata";

    public BonfireWorldSavedData() {
        super(DATA_NAME);
    }

    public BonfireWorldSavedData(String s) {
        super(s);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return BonfireRegistry.INSTANCE.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        BonfireRegistry.INSTANCE.readFromNBT(compound);
    }

    public boolean addBonfire(Bonfire bonfire) {
        boolean added = BonfireRegistry.INSTANCE.addBonfire(bonfire);
        markDirty();
        return added;
    }

    public boolean removeBonfire(UUID id) {
        boolean removed = BonfireRegistry.INSTANCE.removeBonfire(id);
        markDirty();
        return removed;
    }

    public static BonfireWorldSavedData get(World world) {
        MapStorage storage = world.getMapStorage();
        BonfireWorldSavedData instance = (BonfireWorldSavedData) storage.getOrLoadData(BonfireWorldSavedData.class, DATA_NAME);
        if (instance == null) {
            instance = new BonfireWorldSavedData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

}