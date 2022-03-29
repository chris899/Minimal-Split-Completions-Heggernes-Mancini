

import java.util.Objects;

public class Pair<L, R>{
	private L source;
	private R target;
	public  Pair(L source, R target) {
		this.source = source;
		this.target = target;
	}
	    
	public L getSource(){ 
		return source; 
	}
	
	public R getTarget(){ 
		return target; 
	}
	
	public void setSource(L source){ 
		this.source = source; 
	}
	
	public void setTarget(R target){ 
		this.target = target; 
	}
	
	public void print() {
		System.out.print(source + "-" + target + ", ");
	}
	@Override
	public int hashCode() {
		return Objects.hash(source, target);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return Objects.equals(source, other.source) && Objects.equals(target, other.target);
	}

	
	
	
}
