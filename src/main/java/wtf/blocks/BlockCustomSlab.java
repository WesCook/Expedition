package wtf.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.Core;

public class BlockCustomSlab extends BlockFalling{

	protected static final AxisAlignedBB height1 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	private IBlockState state;
	public BlockCustomSlab(IBlockState state) {
		super(Material.CARPET);
		this.state = state;
		this.setCreativeTab(Core.wtfTab);
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		switch (layer){
		case CUTOUT:
			return false;
		case CUTOUT_MIPPED:
			return false;
		case SOLID:
			return true;
		case TRANSLUCENT:
			return true;
		default:
			break;

		}
		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return height1;
	}
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}


	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return state.getBlockHardness(worldIn, pos);
    }
	
    @Override
	public float getExplosionResistance(Entity exploder)
    {
        return state.getBlock().getExplosionResistance(exploder)/2.5F;
    }
    
  

    
    @Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return state.getBlock().getBlockLayer();
    }
    
    @Override
	public SoundType getSoundType()
    {
        return state.getBlock().getSoundType();
    }
    
    
    @Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return state.getBlock().getFlammability(world, pos, face);
    }
    
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return this.state.getBlock().canDropFromExplosion(explosionIn);
    }

}
