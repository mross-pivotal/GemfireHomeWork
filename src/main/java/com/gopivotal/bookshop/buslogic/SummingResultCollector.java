package com.gopivotal.bookshop.buslogic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.distributed.DistributedMember;

public class SummingResultCollector implements
		ResultCollector<Serializable, Serializable> {

	// TODO-03: Determine what type will be used to contain results
	//    HINT: You only need to keep a final sum
	
	private BigDecimal num = BigDecimal.ZERO;
	ArrayList<Serializable> result = new ArrayList<Serializable>();
	
	@Override
	public void addResult(DistributedMember memberID,
			Serializable resultOfSingleExecution) {
		// TODO-04: Implement the addResult method 
		//    HINT: Keep in mind what was sent from the funciton in the prior lab.
		result.add(resultOfSingleExecution);
	}

	@Override
	public void clearResults() {
		// TODO-05: Implement clearResults method
		result.clear();
		
	}

	@Override
	public void endResults() {
		// No special processing required.
		
	}

	@Override
	public Serializable getResult() throws FunctionException {
		// TODO-06: Implement getResult method
		return result;
	}

	@Override
	public Serializable getResult(long timeout, TimeUnit unit)
			throws FunctionException, InterruptedException {
		// This method will do whatever the other getResult() method does.
		return this.getResult();
	}

}
