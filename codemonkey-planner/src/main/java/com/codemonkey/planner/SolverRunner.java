package com.codemonkey.planner;

import java.io.IOException;

import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.impl.solver.XStreamXmlSolverFactory;


public class SolverRunner {

	public static Object run(String solverConfigPath , Object solution){
		
		try {
			XStreamXmlSolverFactory<?> solverFactory = new XStreamXmlSolverFactory<Object>();
			Resource r = ResourceFactory.newClassPathResource(solverConfigPath);
			solverFactory.configure(r.getInputStream());
			Solver solver = solverFactory.buildSolver();
		    solver.solve(solution);
		    return solver.getBestSolution();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
