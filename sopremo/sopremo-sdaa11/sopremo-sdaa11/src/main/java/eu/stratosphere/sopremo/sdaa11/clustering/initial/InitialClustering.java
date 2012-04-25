package eu.stratosphere.sopremo.sdaa11.clustering.initial;

import eu.stratosphere.sopremo.CompositeOperator;
import eu.stratosphere.sopremo.Operator;
import eu.stratosphere.sopremo.SopremoModule;
import eu.stratosphere.sopremo.sdaa11.Annotator;

public class InitialClustering extends CompositeOperator<InitialClustering> {

	private static final long serialVersionUID = 9084919057903474256L;

	/** The maximum radius of a cluster. */
	private int maxRadius = 100;

	/** The maximum number of points of a cluster. */
	private int maxSize = 100;

	public int getMaxRadius() {
		return this.maxRadius;
	}

	public void setMaxRadius(final int maxRadius) {
		this.maxRadius = maxRadius;
	}

	public int getMaxSize() {
		return this.maxSize;
	}

	public void setMaxSize(final int maxSize) {
		this.maxSize = maxSize;
	}
	
	@Override
	public SopremoModule asElementaryOperators() {
		final SopremoModule module = new SopremoModule(this.getName(), 1, 1);

		final Operator<?> input = module.getInput(0);
		final Annotator annotator = new Annotator().withInputs(input);
		final SequentialClustering sequentialClustering = new SequentialClustering()
				.withInputs(annotator);
		sequentialClustering.setMaxRadius(maxRadius);
		sequentialClustering.setMaxSize(maxSize);

		module.getOutput(0).setInput(0, sequentialClustering);

		return module;
	}

}
