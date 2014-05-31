package com.baobaotao.ioc;

import java.sql.Array;

// metaphor of bean
public class MoAttack implements ActorArrangable {
	private GeLi geli;

	public void injectGeli(GeLi geli) {
		this.geli = geli;		
	}
	
	public void cityGateAsk() {
		geli.responseAsk("墨者革离");
	}
}