package com.gopivotal.bookshop.buslogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryException;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;
import com.gopivotal.bookshop.domain.Post;


public class GetAllPostsFunction extends FunctionAdapter implements Declarable {

	@Override
	public void execute(FunctionContext context) {
		if (context instanceof RegionFunctionContext) {
			RegionFunctionContext rfc = (RegionFunctionContext) context;
			Cache cache= CacheFactory.getAnyInstance();
			
			String fieldString = (String) rfc.getArguments();
			Region<Object, PdxInstance> localRegion = PartitionRegionHelper.getLocalDataForContext(rfc);
			
			  
 			QueryService qs = cache.getQueryService();
 			String queryString = "select DISTINCT p from /Posts p WHERE p.userId=1";
 			
 			try {
 				Query q = qs.newQuery(queryString);
 				SelectResults<Post> results = (SelectResults <Post>)q.execute(rfc);
// 				rfc.getResultSender().sendResult((ArrayList) (results).asList());
// 				rfc.getResultSender().lastResult(null);
 				for(Post p: results){
 					System.out.println("Post: " + p.getText());
 					rfc.getResultSender().sendResult(p.getText());
 					
 				}
 				rfc.getResultSender().lastResult(null);
 			} catch (Exception e) {
 			  e.printStackTrace();	
 			}

		
		} else {
			throw new FunctionException("Function must be called as onRegion()");
		}

	}

	@Override
	public String getId() {
		return getClass().getName();
	}
	
	@Override
	public boolean optimizeForWrite() {
		return true;
	}
	
	@Override
	public void init(Properties props) {
		// Nothing to do
		
	}

}
