package uk.co.wehavecookies56.bonfires;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.wehavecookies56.bonfires.blocks.BlockAshBonePile;
import uk.co.wehavecookies56.bonfires.blocks.BlockBonfire;
import uk.co.wehavecookies56.bonfires.gui.GuiHandler;
import uk.co.wehavecookies56.bonfires.items.ItemAshPile;
import uk.co.wehavecookies56.bonfires.items.ItemCoiledSword;
import uk.co.wehavecookies56.bonfires.items.ItemEstusFlask;

/**
 * Created by Toby on 05/11/2016.
 */
@Mod(modid = Bonfires.modid, name = Bonfires.name, version = Bonfires.version, dependencies = "required-after:llibrary@[1.7.1]")
public class Bonfires {

    @SidedProxy(clientSide = "uk.co.wehavecookies56.bonfires.ClientProxy", serverSide = "uk.co.wehavecookies56.bonfires.CommonProxy")
    public static CommonProxy proxy;

    public static final String modid = "bonfires", name = "Bonfires", version = "1.0";

    @Mod.Instance (modid)
    public static Bonfires instance;

    public static Block bonfire, ashBonePile;
    public static Block[] blocks;

    public static Item ashPile, coiledSword, estusFlask;
    public static Item[] items;

    public static CreativeTabs tabBonfires;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        PacketDispatcher.registerPackets();
        tabBonfires = new TabBonfires("tabBonfires");
        blocks = new Block[] {
                bonfire = new BlockBonfire(Material.IRON).setUnlocalizedName("bonfire"),
                ashBonePile = new BlockAshBonePile(Material.SNOW).setUnlocalizedName("ash_bone_pile")
        };
        items = new Item[] {
                ashPile = new ItemAshPile().setUnlocalizedName("ash_pile"),
                coiledSword = new ItemCoiledSword(EnumHelper.addToolMaterial("COILED_SWORD", 3, 105, 3, 10, 0)).setUnlocalizedName("coiled_sword"),
                estusFlask = new ItemEstusFlask(0, 0, false).setUnlocalizedName("estus_flask")
        };
        for (Block b : blocks) {
            b.setCreativeTab(tabBonfires);
            registerBlock(b);
        }
        for (Item i : items) {
            i.setCreativeTab(tabBonfires);
            registerItem(i);
        }
        EstusHandler.init();
        proxy.preInit();
    }

    public static void registerItem(Item item) {
        GameRegistry.register(item, new ResourceLocation(modid, item.getUnlocalizedName().substring(5)));
    }

    public static void registerBlock(Block block) {
        GameRegistry.register(block, new ResourceLocation(modid, block.getUnlocalizedName().substring(5)));
        GameRegistry.register(new ItemBlock(block), new ResourceLocation(modid, block.getUnlocalizedName().substring(5)));
    }

    public static void registerRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }

    public static void registerRender(Item item, int meta, String name) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(modid + ":" + name, "inventory"));
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(Bonfires.instance);
        GameRegistry.registerTileEntity(TileEntityBonfire.class, "bonfire");
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
