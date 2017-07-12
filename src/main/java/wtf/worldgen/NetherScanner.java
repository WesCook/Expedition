package wtf.worldgen;


import java.util.Arrays;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.init.BlockSets;
import wtf.worldgen.replacers.Replacer;

public class NetherScanner extends WorldScanner{
	

	
	@Override
	public boolean isSurfaceAndCheck(Chunk chunk, GeneratorMethods gen, IBlockState state, int x, int y, int z){
		//BlockPos pos = ;
		Block block = state.getBlock();

		if (BlockSets.isNonSolidAndCheckReplacement.containsKey(block)){
			//System.out.println("genReplace contained " + block.getLocalizedName());
			Replacer replacer = BlockSets.isNonSolidAndCheckReplacement.get(block);
			if (replacer!= null){
				replacer.isNonSolidAndReplacement(chunk, new BlockPos(x, y, z), gen, state);
				//WTFCore.log.info("Replaced");
			}
		}
		return netherSurfaceBlocks.contains(block);
	}

	private static Block[] listSurfaceBlocks = {Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL};
	public static HashSet<Block> netherSurfaceBlocks = new HashSet<Block>(Arrays.asList(listSurfaceBlocks));

}
