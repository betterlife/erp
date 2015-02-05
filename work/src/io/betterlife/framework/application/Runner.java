package io.betterlife.framework.application;

public interface Runner<P, R> {
	
	public R run(P obj) throws Exception;

}
