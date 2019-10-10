package org.rezistenz.route.planing;

import org.jgrapht.Graph;
import org.jgrapht.io.ExportException;
import org.junit.Assert;
import org.junit.Test;
import org.rezistenz.route.planing.generate.ModelGenerator;
import org.rezistenz.route.planing.model.Distance;
import org.rezistenz.route.planing.model.Location;

import java.util.logging.Logger;

public class GeneratorTest {

	private static Logger log = Logger.getLogger(GeneratorTest.class.getName());

	@Test
	public void testGenerate() throws ExportException {
		final int locCount = 100;
		final Graph<Location, Distance> graph = new ModelGenerator()
				.setLocCount(locCount)
				.generate();

		Assert.assertEquals(locCount, graph.vertexSet().size());
		Assert.assertEquals((locCount * (locCount - 1) / 2), graph.edgeSet().size());
	}
		
}
