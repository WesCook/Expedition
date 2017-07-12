package wtf.worldgen.trees;


import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import wtf.worldgen.generators.TreeGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldGenTreeCancel {

	@SubscribeEvent
	public void decorate(DecorateBiomeEvent.Decorate event){
		if (event.getType() == Decorate.EventType.TREE  && TreeGenerator.shouldTreePosGenerate(event.getWorld(), event.getRand(), event.getPos())){	
			event.setResult(Result.DENY);
		}
	}
	
	

	//tree testing
/*
	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event) throws Exception
	{
		System.out.println("event caught");
	
		event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
	
		TreeVars newTree = new Mangrove(event.getWorld());
				
		
		ChunkCoords coords = new ChunkCoords(event.getPos()); 
		ChunkScan chunkscan = CoreWorldGenListener.getChunkScan(event.getWorld(), coords);
		
		TreePos tree = new TreePos(event.getWorld(), event.getWorld().rand, chunkscan, chunkscan.surface[event.getPos().getX()&15][event.getPos().getZ()&15], newTree);
		
		GenTree.generate(tree);

	}
*/
}
