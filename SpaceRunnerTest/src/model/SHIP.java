package model;

public enum SHIP {
	
	BLUE("view/resources/shipchooser/blue_ship.png"),
	GREEN("view/resources/shipchooser/green_ship.png"),
	ORANGE("view/resources/shipchooser/orange_ship.png"),
	RED("view/resources/shipchooser/red_ship.png");
	
	public String urlShip;
	
	private SHIP(String urlShip) {
		this.urlShip = urlShip;
	}
	
	public String getURL() {
		//System.out.println(urlShip);
		return this.urlShip;
	}
}
