package uk.co.wehavecookies56.bonfires.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import uk.co.wehavecookies56.bonfires.tiles.TileEntityBonfire;

/**
 * Created by Toby on 07/11/2016.
 */
public class GuiHandler implements IGuiHandler {

    private static int modGuiIndex = 0;
    public static final int GUI_BONFIRECREATION = modGuiIndex++;
    public static final int GUI_BONFIRE = modGuiIndex++;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(xyz);
        if (ID == GUI_BONFIRECREATION)
            return new GuiCreateBonfire((TileEntityBonfire) te);
        if (ID == GUI_BONFIRE)
            return new GuiBonfire((TileEntityBonfire) te);
        return null;
    }
}
