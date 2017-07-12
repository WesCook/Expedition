package wtf.worldgen.trees.components;

import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import wtf.worldgen.trees.TreeInstance;

public class Branch {

	public final double length;
	public final double oriX;
	public final double oriY;
	public final double oriZ;
	
	public final double vecX;
	public final double vecZ;
	public final double vecY;
	
	protected double x;
	protected double y;
	protected double z;
	
	public int count = 0;
	
	public final BlockLog.EnumAxis axis;
	
	
	public Branch(TreeInstance tree, double oriX, double oriY, double oriZ, double x, double y, double z, double rootLength) {
		/*
		if (rootLength*(1-pitch) > 32){
			try {
				throw new Exception("Branch length > 32 ="+ rootLength + " "+ tree.type.getClass().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		this.oriX = oriX;
		this.oriY = oriY;
		this.oriZ = oriZ;
		
		vecX = x;
		vecY = y;
		vecZ = z;
		this.length=rootLength+1;
		
		this.x = oriX;
		this.y = oriY;
		this.z = oriZ;
		
		axis = Math.abs(vecY) < MathHelper.abs_max(vecX, vecZ) ? (Math.abs(vecX) > Math.abs(vecZ) ? BlockLog.EnumAxis.X : BlockLog.EnumAxis.Z) : BlockLog.EnumAxis.Y;
	}
	 
	public boolean hasNext(){
		double xlength = x - oriX;
		double ylength = y - oriY;
		double zlength = z - oriZ;
		if (length > 100){
			//System.out.println("Infinite caught");	
		}
		
		return length > 0 ? (xlength * xlength + ylength*ylength + zlength*zlength) < length*length : false;
		//return count<length;
	}
	
	//swap over to a calculated length, intead of simply number of blocks placed
	
	public BlockPos next(){
		
		if (length > 6 && MathHelper.abs_max(vecX,  vecY) > vecY){
			if ((int)(y+vecY) > (int)y){
				y += vecY;
				return new BlockPos(x, y, z);
			}
			else if ((int)(y+vecY) < (int)y){
				y += vecY;
				return new BlockPos(x, y, z);
			}
		}
		
		x += vecX;
		y += vecY;
		z += vecZ;
		count++;
		return new BlockPos(x, y, z);
	}
	
	public BlockPos pos(){
		return new BlockPos(x, y, z);
	}
	

	public BlockPos lateralNext(){
		//System.out.println("vec " + vecX + " " + vecZ);
		if (x==0 && z==0){
			y = y>0 ? y+1 : y-1;
			return new BlockPos(x, y, z); 
		}

		x += vecX;
		z += vecZ;
		return new BlockPos(x, y, z);
	}
	
	public int getCount(){
		return count;
	}
}
