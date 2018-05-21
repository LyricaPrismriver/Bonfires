package uk.co.wehavecookies56.bonfires.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import uk.co.wehavecookies56.bonfires.Bonfire;
import uk.co.wehavecookies56.bonfires.Bonfires;
import uk.co.wehavecookies56.bonfires.EstusHandler;
import uk.co.wehavecookies56.bonfires.world.BonfireTeleporter;
import uk.co.wehavecookies56.bonfires.world.BonfireWorldSavedData;

import java.util.UUID;

/**
 * Created by Toby on 16/11/2016.
 */
public class ItemHomewardBone extends Item {

    public ItemHomewardBone(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Bonfires.tabBonfires);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (!worldIn.isRemote) {
            if (playerIn.getServer() != null) {
                UUID lastRested = playerIn.getCapability(EstusHandler.CAPABILITY_ESTUS, null).lastRested();
                if (lastRested != null) {
                    Bonfire bonfire = BonfireWorldSavedData.get(worldIn).bonfires.getBonfire(lastRested);
                    BonfireTeleporter tp = new BonfireTeleporter(bonfire.getPos());
                    if (playerIn.world.provider.getDimension() != bonfire.getDimension()) {
                        playerIn.changeDimension(bonfire.getDimension(), tp);
                    } else {
                        tp.placeEntity(worldIn, playerIn, playerIn.rotationYaw);
                    }
                    playerIn.getHeldItemMainhand().shrink(1);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, hand);
    }
}
