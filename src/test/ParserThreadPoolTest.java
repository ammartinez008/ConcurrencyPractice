package test;

import main.Parser.ThreadPool.ParserThreadPool;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alx on 5/16/16.
 */
public class ParserThreadPoolTest {
	/*************************************************
	 * Created by alx on 5/16/16.
	 * Simple testing of ParserThreadPool construction
	 **************************************************/

		private static final int NTHREADS = Runtime.getRuntime().availableProcessors();
		private ParserThreadPool executorService;
		private BlockingQueue<Runnable> queue;


		@Before
		public void setup() {
			queue = new LinkedBlockingQueue<>();
			executorService = new ParserThreadPool(NTHREADS, NTHREADS + 1, 10, TimeUnit.SECONDS, queue);
		}

		@Test
		public void testEmpty() {
			assertEquals(executorService.getTime().longValue(), 0);
			assertEquals(executorService.getTasks().longValue(), 0);
			assertTrue(executorService.getQueue().isEmpty());
			assertEquals(executorService.getCorePoolSize(), NTHREADS);
			assertEquals(executorService.getMaximumPoolSize(), NTHREADS + 1);
		}





}
