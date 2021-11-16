/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.jca.work;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import javax.naming.NamingException;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;
import javax.resource.spi.work.WorkRejectedException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.core.task.TaskTimeoutException;
import org.springframework.jca.context.BootstrapContextAware;
import org.springframework.jndi.JndiLocatorSupport;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.util.Assert;

/**
 * {@link org.springframework.core.task.TaskExecutor} implementation
 * that delegates to a JCA 1.5 WorkManager, implementing the
 * {@link javax.resource.spi.work.WorkManager} interface.
 *
 * <p>This is mainly intended for use within a JCA ResourceAdapter implementation,
 * but may also be used in a standalone environment, delegating to a locally
 * embedded WorkManager implementation (such as Geronimo's).
 *
 * <p>Also implements the JCA 1.5 WorkManager interface itself, delegating all
 * calls to the target WorkManager. Hence, a caller can choose whether it wants
 * to talk to this executor through the Spring TaskExecutor interface or the
 * JCA 1.5 WorkManager interface.
 *
 * <p>This adapter is also capable of obtaining a JCA WorkManager from JNDI.
 * This is for example appropriate on the Geronimo application server, where
 * WorkManager GBeans (e.g. Geronimo's default "DefaultWorkManager" GBean)
 * can be linked into the J2EE environment through "gbean-ref" entries
 * in the {@code geronimo-web.xml} deployment descriptor.
 *
 * <p><b>On JBoss and GlassFish, obtaining the default JCA WorkManager
 * requires special lookup steps.</b> See the
 * {@link org.springframework.jca.work.jboss.JBossWorkManagerTaskExecutor}
 * {@link org.springframework.jca.work.glassfish.GlassFishWorkManagerTaskExecutor}
 * classes which are the direct equivalent of this generic JCA adapter class.
 *
 * @author Juergen Hoeller
 * @since 2.0.3
 * @see #setWorkManager
 * @see javax.resource.spi.work.WorkManager#scheduleWork
 */
public class WorkManagerTaskExecutor extends JndiLocatorSupport
		implements SchedulingTaskExecutor, WorkManager, BootstrapContextAware, InitializingBean {

	private WorkManager workManager;

	private String workManagerName;

	private boolean blockUntilStarted = false;

	private boolean blockUntilCompleted = false;

	private WorkListener workListener;


	/**
	 * Create a new WorkManagerTaskExecutor, expecting bean-style configuration.
	 * @see #setWorkManager
	 */
	public WorkManagerTaskExecutor() {
	}

	/**
	 * Create a new WorkManagerTaskExecutor for the given WorkManager.
	 * @param workManager the JCA WorkManager to delegate to
	 */
	public WorkManagerTaskExecutor(WorkManager workManager) {
		setWorkManager(workManager);
	}


	/**
	 * Specify the JCA WorkManager instance to delegate to.
	 */
	public void setWorkManager(WorkManager workManager) {
		Assert.notNull(workManager, "WorkManager must not be null");
		this.workManager = workManager;
	}

	/**
	 * Set the JNDI name of the JCA WorkManager.
	 * <p>This can either be a fully qualified JNDI name,
	 * or the JNDI name relative to the current environment
	 * naming context if "resourceRef" is set to "true".
	 * @see #setWorkManager
	 * @see #setResourceRef
	 */
	public void setWorkManagerName(String workManagerName) {
		this.workManagerName = workManagerName;
	}

	/**
	 * Specify the JCA BootstrapContext that contains the
	 * WorkManager to delegate to.
	 */
	public void setBootstrapContext(BootstrapContext bootstrapContext) {
		Assert.notNull(bootstrapContext, "BootstrapContext must not be null");
		this.workManager = bootstrapContext.getWorkManager();
	}

	/**
	 * Set whether to let {@link #execute} block until the work
	 * has been actually started.
	 * <p>Uses the JCA {@code startWork} operation underneath,
	 * instead of the default {@code scheduleWork}.
	 * @see javax.resource.spi.work.WorkManager#startWork
	 * @see javax.resource.spi.work.WorkManager#scheduleWork
	 */
	public void setBlockUntilStarted(boolean blockUntilStarted) {
		this.blockUntilStarted = blockUntilStarted;
	}

	/**
	 * Set whether to let {@link #execute} block until the work
	 * has been completed.
	 * <p>Uses the JCA {@code doWork} operation underneath,
	 * instead of the default {@code scheduleWork}.
	 * @see javax.resource.spi.work.WorkManager#doWork
	 * @see javax.resource.spi.work.WorkManager#scheduleWork
	 */
	public void setBlockUntilCompleted(boolean blockUntilCompleted) {
		this.blockUntilCompleted = blockUntilCompleted;
	}

	/**
	 * Specify a JCA 1.5 WorkListener to apply, if any.
	 * <p>This shared WorkListener instance will be passed on to the
	 * WorkManager by all {@link #execute} calls on this TaskExecutor.
	 */
	public void setWorkListener(WorkListener workListener) {
		this.workListener = workListener;
	}

	public void afterPropertiesSet() throws NamingException {
		if (this.workManager == null) {
			if (this.workManagerName != null) {
				this.workManager = lookup(this.workManagerName, WorkManager.class);
			}
			else {
				this.workManager = getDefaultWorkManager();
			}
		}
	}

	/**
	 * Obtain a default WorkManager to delegate to.
	 * Called if no explicit WorkManager or WorkManager JNDI name has been specified.
	 * <p>The default implementation returns a {@link SimpleTaskWorkManager}.
	 * Can be overridden in subclasses.
	 */
	protected WorkManager getDefaultWorkManager() {
		return new SimpleTaskWorkManager();
	}


	//-------------------------------------------------------------------------
	// Implementation of the Spring SchedulingTaskExecutor interface
	//-------------------------------------------------------------------------

	public void execute(Runnable task) {
		execute(task, TIMEOUT_INDEFINITE);
	}

	public void execute(Runnable task, long startTimeout) {
		Assert.state(this.workManager != null, "No WorkManager specified");
		Work work = new DelegatingWork(task);
		try {
			if (this.blockUntilCompleted) {
				if (startTimeout != TIMEOUT_INDEFINITE || this.workListener != null) {
					this.workManager.doWork(work, startTimeout, null, this.workListener);
				}
				else {
					this.workManager.doWork(work);
				}
			}
			else if (this.blockUntilStarted) {
				if (startTimeout != TIMEOUT_INDEFINITE || this.workListener != null) {
					this.workManager.startWork(work, startTimeout, null, this.workListener);
				}
				else {
					this.workManager.startWork(work);
				}
			}
			else {
				if (startTimeout != TIMEOUT_INDEFINITE || this.workListener != null) {
					this.workManager.scheduleWork(work, startTimeout, null, this.workListener);
				}
				else {
					this.workManager.scheduleWork(work);
				}
			}
		}
		catch (WorkRejectedException ex) {
			if (WorkException.START_TIMED_OUT.equals(ex.getErrorCode())) {
				throw new TaskTimeoutException("JCA WorkManager rejected task because of timeout: " + task, ex);
			}
			else {
				throw new TaskRejectedException("JCA WorkManager rejected task: " + task, ex);
			}
		}
		catch (WorkException ex) {
			throw new SchedulingException("Could not schedule task on JCA WorkManager", ex);
		}
	}

	public Future<?> submit(Runnable task) {
		FutureTask<Object> future = new FutureTask<Object>(task, null);
		execute(future, TIMEOUT_INDEFINITE);
		return future;
	}

	public <T> Future<T> submit(Callable<T> task) {
		FutureTask<T> future = new FutureTask<T>(task);
		execute(future, TIMEOUT_INDEFINITE);
		return future;
	}

	/**
	 * This task executor prefers short-lived work units.
	 */
	public boolean prefersShortLivedTasks() {
		return true;
	}


	//-------------------------------------------------------------------------
	// Implementation of the JCA WorkManager interface
	//-------------------------------------------------------------------------

	public void doWork(Work work) throws WorkException {
		this.workManager.doWork(work);
	}

	public void doWork(Work work, long delay, ExecutionContext executionContext, WorkListener workListener)
			throws WorkException {

		this.workManager.doWork(work, delay, executionContext, workListener);
	}

	public long startWork(Work work) throws WorkException {
		return this.workManager.startWork(work);
	}

	public long startWork(Work work, long delay, ExecutionContext executionContext, WorkListener workListener)
			throws WorkException {

		return this.workManager.startWork(work, delay, executionContext, workListener);
	}

	public void scheduleWork(Work work) throws WorkException {
		this.workManager.scheduleWork(work);
	}

	public void scheduleWork(Work work, long delay, ExecutionContext executionContext, WorkListener workListener)
			throws WorkException {

		this.workManager.scheduleWork(work, delay, executionContext, workListener);
	}

}
