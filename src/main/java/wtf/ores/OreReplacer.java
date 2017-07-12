package wtf.ores;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.blocks.BlockDenseOre;
import wtf.init.BlockSets;
import wtf.utilities.wrappers.StoneAndOre;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.replacers.Replacer;

public class OreReplacer extends Replacer{

	public OreReplacer(Block block) {
		super(block);
	}
	
	Random random = new Random();

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos,GeneratorMethods gen, IBlockState oldState){
		IBlockState state = BlockSets.stoneAndOre.get(new StoneAndOre(Blocks.STONE.getDefaultState(), oldState));
		if (state != null && state.getBlock() instanceof BlockDenseOre){
			gen.overrideBlock(pos, state.withProperty(BlockDenseOre.DENSITY, random.nextInt(3)));
		}
		return false;
	}
	
}
