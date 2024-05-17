

public class Hitbox extends Rect{
	public Hitbox(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void track(Rect r) 
	
	{
		x = r.x;
		y = r.y;
	}
	
	public void player_track(Rect r) 
	
	{
		x = r.x + 40;
		y = r.y + 100;
	}
	
	
	public void wolf_track(Rect r) 
	
	{
		x = r.x;
		y = r.y + 100;
	}
	

	public void player_track_attack(Rect r, boolean attack) 
	
	{
		if(attack) {
			x = r.x + 50;
			y = r.y + 132;
		}else {
			x = r.x + 50;
			y = r.y + 500;
		}
		
	}

	
}
