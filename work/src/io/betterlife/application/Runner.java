package io.betterlife.application;

public interface Runner<P, R> {
	
	public R run(P obj) throws Exception;

}
