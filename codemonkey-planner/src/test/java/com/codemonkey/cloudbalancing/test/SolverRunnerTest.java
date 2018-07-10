package com.codemonkey.cloudbalancing.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;

import com.codemonkey.cloudbalancing.domain.CloudBalance;
import com.codemonkey.cloudbalancing.domain.CloudProcess;
import com.codemonkey.planner.SolverRunner;

public class SolverRunnerTest {

	private XStreamSolutionFileIO<CloudBalance> xStreamProblemIO;
	 
	@Test
	public void test(){
		
		xStreamProblemIO = new XStreamSolutionFileIO<CloudBalance>(CloudBalance.class);
		String solverConfigPath = "rules/cloudbalancing/cloudBalancingSolverConfig.xml";
		Resource r = ResourceFactory.newClassPathResource("src/test/resources/data/2computers-6processes.xml");
		File file = new File(r.getSourcePath());
		CloudBalance cb = (CloudBalance) xStreamProblemIO.read(file);
		Object s = SolverRunner.run(solverConfigPath , cb);
		CloudBalance result = (CloudBalance)s;
		List<CloudProcess> list = result.getProcessList();
		assertEquals(new Long(0) , list.get(0).getComputer().getId());
		assertEquals(new Long(0) , list.get(1).getComputer().getId());
		assertEquals(new Long(0) , list.get(2).getComputer().getId());
		assertEquals(new Long(0) , list.get(3).getComputer().getId());
		assertEquals(new Long(1) , list.get(4).getComputer().getId());
		assertEquals(new Long(1) , list.get(5).getComputer().getId());
	}
}
