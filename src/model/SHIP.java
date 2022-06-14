package model;

public enum SHIP {
	
	BLUE("view/resources/shipchooser/blue_ship.png", "view/resources/shipchooser/blue_life.png"),
	GREEN("view/resources/shipchooser/green_ship.png", "view/resources/shipchooser/green_life.png"),
	ORANGE("view/resources/shipchooser/orange_ship.png", "view/resources/shipchooser/orange_life.png"),
	RED("view/resources/shipchooser/red_ship.png","view/resources/shipchooser/red_life.png");
	
	public String urlShip, urlLife;
	
	private SHIP(String urlShip, String urlLife) {
		this.urlShip = urlShip;
		this.urlLife = urlLife;
	}
	
	public String getUrl() {
		//System.out.println(urlShip);
		return this.urlShip;
	}
	
	public String getUrlLife() {
		//System.out.println(urlShip);
		return this.urlLife;
	}
}
