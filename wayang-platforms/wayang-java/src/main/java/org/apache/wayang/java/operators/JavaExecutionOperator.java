package org.apache.wayang.java.operators;

import org.apache.wayang.core.api.exception.WayangException;
import org.apache.wayang.core.optimizer.OptimizationContext;
import org.apache.wayang.core.plan.wayangplan.ExecutionOperator;
import org.apache.wayang.core.platform.ChannelInstance;
import org.apache.wayang.core.platform.lineage.ExecutionLineageNode;
import org.apache.wayang.core.util.Tuple;
import org.apache.wayang.java.channels.CollectionChannel;
import org.apache.wayang.java.channels.JavaChannelInstance;
import org.apache.wayang.java.channels.StreamChannel;
import org.apache.wayang.java.execution.JavaExecutor;
import org.apache.wayang.java.platform.JavaPlatform;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Execution operator for the Java platform.
 */
public interface JavaExecutionOperator extends ExecutionOperator {

    @Override
    default JavaPlatform getPlatform() {
        return JavaPlatform.getInstance();
    }

    /**
     * Evaluates this operator. Takes a set of Java {@link Stream}s according to the operator inputs and produces
     * a set of {@link Stream}s according to the operator outputs -- unless the operator is a sink, then it triggers
     * execution.
     * <p>In addition, this method should give feedback of what this instance was doing by wiring the
     * {@link org.apache.wayang.core.platform.lineage.LazyExecutionLineageNode}s of input and ouput {@link ChannelInstance}s and
     * providing a {@link Collection} of executed {@link OptimizationContext.OperatorContext}s.</p>
     *
     * @param inputs          {@link ChannelInstance}s that satisfy the inputs of this operator
     * @param outputs         {@link ChannelInstance}s that collect the outputs of this operator
     * @param javaExecutor    that executes this instance
     * @param operatorContext optimization information for this instance
     * @return {@link Collection}s of what has been executed and produced
     */
    Tuple<Collection<ExecutionLineageNode>, Collection<ChannelInstance>> evaluate(
            ChannelInstance[] inputs,
            ChannelInstance[] outputs,
            JavaExecutor javaExecutor,
            OptimizationContext.OperatorContext operatorContext);

    /**
     * Utility method to forward a {@link JavaChannelInstance} to another.
     *
     * @param input  that should be forwarded
     * @param output to that should be forwarded
     */
    static void forward(ChannelInstance input, ChannelInstance output) {
        // Do the forward.
        if (output instanceof CollectionChannel.Instance) {
            ((CollectionChannel.Instance) output).accept(((CollectionChannel.Instance) input).provideCollection());
        } else if (output instanceof StreamChannel.Instance) {
            ((StreamChannel.Instance) output).accept(((JavaChannelInstance) input).provideStream());
        } else {
            throw new WayangException(String.format("Cannot forward %s to %s.", input, output));
        }

        // Manipulate the lineage.
        output.getLineage().addPredecessor(input.getLineage());
    }

}