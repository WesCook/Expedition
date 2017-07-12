package wtf.worldgen.trees;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.init.WTFBlocks;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.trees.types.AbstractTreeType;

public class TreeInstance {

	public final Random random;
	public final World world;
	public final BlockPos pos;
	public final double oriX;
	public final double y;
	public final double oriZ;
	
	public final AbstractTreeType type;

	public final double trunkDiameter;
	public final double trunkRadius;

	public final double trunkHeight;
	public final double rootLevel;
	

	public final double scale;
	public static SimplexHelper simplex = new SimplexHelper("treeSimplex");
	
	public final boolean snow;

	public ChunkScan chunkscan;
	
	private final HashMap<BlockPos, IBlockState> trunkBlocks;
	private final HashMap<BlockPos, IBlockState> leafBlocks;
	private final HashMap<BlockPos, IBlockState> rootBlocks;
	private final HashMap<BlockPos, IBlockState> decoBlocks;


	public TreeInstance(World world, Random random, ChunkScan chunkscan, BlockPos pos, AbstractTreeType tree){
		//tree.canGrowOn.add(CaveBlocks.MossyDirt);
		trunkBlocks = new HashMap<BlockPos, IBlockState>();
		leafBlocks = new HashMap<BlockPos, IBlockState>();
		rootBlocks = new HashMap<BlockPos, IBlockState>();
		decoBlocks = new HashMap<BlockPos, IBlockState>();

		this.world = world;
		this.random = random;
		this.pos = pos;
		this.oriX =pos.getX()+0.5;
		this.y = pos.getY();
		this.oriZ =pos.getZ()+0.5;
		this.snow = BiomeDictionary.isBiomeOfType(world.getBiome(pos), Type.SNOWY);
		
		this.type = tree;

		scale = simplex.get2DNoise(world, pos.getX()/100, pos.getZ()/100);
		
		trunkHeight = tree.getTrunkHeight(scale);
		trunkDiameter = tree.getTrunkDiameter(scale);
		trunkRadius = trunkDiameter/2;

		//branchLength = MathHelper.ceiling_double_int((tree.baseBranchLength +  tree.baseBranchLength*scale)/2);
		//rootLength = (int) (trunkHeight/tree.rootLengthDivisor);
		if (!tree.airGenerate){
			rootLevel = tree.rootLevel == 0 ? random.nextInt(2): tree.rootLevel;
		}
		else {
			rootLevel = tree.airGenHeight; // +1 because generation height is cut off at > airGenHeight
		}
		this.chunkscan = chunkscan;
	}

	Block[] groundArray = {Blocks.DIRT, Blocks.GRASS, Blocks.GRAVEL, Block.getBlockFromName("dirt0decoStatic")};
	public HashSet<Block> groundBlocks = new HashSet<Block>(Arrays.asList(groundArray));



	
	public void setTrunk(BlockPos pos){
		trunkBlocks.put(pos, type.wood);
		//this.chunkscan.setGenerated(pos);
	}

	public void setRoot(BlockPos pos){
		rootBlocks.put(pos, type.wood.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE));
		this.chunkscan.setGenerated(pos);

	}
	public void setBranch(BlockPos pos, BlockLog.EnumAxis axis){
		rootBlocks.put(pos, type.branch.withProperty(BlockLog.LOG_AXIS, axis));
		this.chunkscan.setGenerated(pos);
	}
	public int airHash = Blocks.AIR.hashCode();
	public void setLeaf(BlockPos pos){
		if (world.getBlockState(pos).getBlock().hashCode() == airHash){
			leafBlocks.put(pos, type.leaf.withProperty(BlockLeaves.CHECK_DECAY, false));
			if (snow){
				setDeco(pos.up(), Blocks.SNOW_LAYER.getDefaultState());
				if (random.nextInt(100) < 0){
					setDeco(pos.down(), WTFBlocks.icicle.getDefaultState());
				}
			}
		}
		
	}
	
	public void setDeco(BlockPos pos, IBlockState state){	
			decoBlocks.put(pos, state);
		
	}

	public void setBlocksForPlacement(GeneratorMethods gen){
		HashMap<BlockPos, IBlockState> masterMap = new HashMap<BlockPos, IBlockState>();
		masterMap.putAll(decoBlocks);
		masterMap.putAll(leafBlocks);
		masterMap.putAll(rootBlocks);
		masterMap.putAll(trunkBlocks);
		
		for (Entry<BlockPos, IBlockState> entry: masterMap.entrySet()){
			gen.setTreeBlock(entry.getKey(), entry.getValue());	
		}
		
		
	}

	public boolean inTrunk(BlockPos pos){
		//System.out.println(relPosZ(zpos) - z);
		return trunkBlocks.containsKey(pos);
	}

}
